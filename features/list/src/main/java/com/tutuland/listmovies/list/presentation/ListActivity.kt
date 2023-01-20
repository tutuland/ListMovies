package com.tutuland.listmovies.list.presentation

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.tutuland.listmovies.list.databinding.ListActivityBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity() {
    private val viewModel: ListViewModel by viewModel()
    private lateinit var binding: ListActivityBinding
    private lateinit var adapter: ListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect(::renderState)
        }
    }

    private fun setupViews() {
        binding = ListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ListItemAdapter(clickAction = viewModel::itemClicked, linkAction = ::linkClicked)
        binding.listRv.layoutManager = GridLayoutManager(this, getSpanCount())
        binding.listRv.adapter = adapter
        binding.listInputText.doAfterTextChanged { viewModel.filterTyped(it.toString()) }
        binding.listRetry.setOnClickListener { viewModel.viewStarted() }
    }

    private fun getSpanCount(): Int =
        if (resources.configuration.orientation == ORIENTATION_PORTRAIT) 1 else 2

    private fun renderState(state: ListViewState) {
        binding.listLoading.isVisible = state.showLoading
        binding.listRetry.isVisible = state.showRetry
        adapter.submitList(state.items)
    }

    private fun linkClicked(link: String) {
        val webpage: Uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) startActivity(intent)
    }
}