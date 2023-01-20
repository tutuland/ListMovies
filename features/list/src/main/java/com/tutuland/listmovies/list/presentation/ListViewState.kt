package com.tutuland.listmovies.list.presentation

import com.tutuland.listmovies.list.domain.model.ListItem

data class ListViewState(
    val showLoading: Boolean = false,
    val showRetry: Boolean = false,
    private val filter: String = "",
    private val _items: List<ListItem> = emptyList(),
) {
    val items: List<ListItem>
        get() = if (filter.isEmpty()) _items else _items.filter { item ->
            item.movie.title.contains(filter) || item.movie.releasedIn.contains(filter)
        }
}