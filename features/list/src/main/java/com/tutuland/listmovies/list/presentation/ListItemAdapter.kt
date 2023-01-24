package com.tutuland.listmovies.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tutuland.listmovies.list.R
import com.tutuland.listmovies.list.databinding.ListItemBinding
import com.tutuland.listmovies.list.domain.model.ListItem

class ListItemAdapter(
    private val favoriteAction: (ListItem) -> Unit,
    private val imdbAction: (ListItem) -> Unit,
) : ListAdapter<ListItem, ListItemAdapter.ListItemHolder>(ListItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) =
        holder bind getItem(position)

    inner class ListItemHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        infix fun bind(model: ListItem) {
            val favoriteDrawable =
                if (model.isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
            binding.listItemFavorite.setImageResource(favoriteDrawable)
            binding.listItemFavorite.setOnClickListener { favoriteAction(model) }
            binding.listItemImdb.setOnClickListener { imdbAction(model) }
            binding.listItemImage.load(model.movie.imageUrl)
            binding.listItemTitle.text = model.movie.title
            binding.listItemReleased.text = model.movie.releasedIn
            binding.listItemDuration.text = model.movie.duration
            binding.listItemResolution.text = model.movie.resolution
            binding.listItemAge.text = model.movie.ageRestriction
        }
    }

    private class ListItemDiff : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(old: ListItem, new: ListItem) = old.movie.id == new.movie.id
        override fun areContentsTheSame(old: ListItem, new: ListItem) = old == new
        override fun getChangePayload(old: ListItem, new: ListItem): Any {
            val diff = Bundle()
            if (old.movie.title != new.movie.title)
                diff.putString("title", new.movie.title)
            if (old.movie.imageUrl != new.movie.imageUrl)
                diff.putString("imageUrl", new.movie.imageUrl)
            if (old.movie.releasedIn != new.movie.releasedIn)
                diff.putString("releasedIn", new.movie.releasedIn)
            if (old.movie.duration != new.movie.duration)
                diff.putString("duration", new.movie.duration)
            if (old.movie.imdbLink != new.movie.imdbLink)
                diff.putString("imdbLink", new.movie.imdbLink)
            if (old.movie.resolution != new.movie.resolution)
                diff.putString("resolution", new.movie.resolution)
            if (old.movie.ageRestriction != new.movie.ageRestriction)
                diff.putString("ageRestriction", new.movie.ageRestriction)
            if (old.isFavorite != new.isFavorite)
                diff.putBoolean("isFavorite", new.isFavorite)
            return diff
        }
    }
}