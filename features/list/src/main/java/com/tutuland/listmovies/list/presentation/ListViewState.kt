package com.tutuland.listmovies.list.presentation

import com.tutuland.listmovies.list.domain.model.ListItem

data class ListViewState(
    val isLoading: Boolean = false,
    val showRetry: Boolean = false,
    private val filter: String = "",
    private val _items: List<ListItem> = emptyList(),
) {
    val items: List<ListItem> get() = _items.filter { it.movie.title.contains(filter) }
}