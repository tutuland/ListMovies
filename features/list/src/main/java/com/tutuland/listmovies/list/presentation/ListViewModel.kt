package com.tutuland.listmovies.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutuland.listmovies.list.domain.GetListUseCase
import com.tutuland.listmovies.list.domain.ToggleFavoriteUseCase
import com.tutuland.listmovies.list.domain.model.ListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val getList: GetListUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<ListViewState> = MutableStateFlow(ListViewState())
    val state: StateFlow<ListViewState> = _state

    init {
        viewStarted()
    }

    fun viewStarted() {
        viewModelScope.launch {
            fetchContent()
        }
    }

    fun itemClicked(item: ListItem) {
        viewModelScope.launch {
            toggleFavorite(item)
            fetchContent()
        }
    }

    fun filterTyped(filter: String) {
        _state.value = _state.value.copy(filter = filter)
    }

    private fun showLoading() {
        _state.value = _state.value.copy(showLoading = true)
    }

    private fun showRetry() {
        _state.value = _state.value.copy(showLoading = false, showRetry = true, _items = listOf())
    }

    private fun showContent(content: List<ListItem>) {
        _state.value = _state.value.copy(showLoading = false, showRetry = false, _items = content)
    }

    private suspend fun fetchContent() = runCatching {
        showLoading()
        val content = getList()
        showContent(content)
    }.onFailure { showRetry() }
}

