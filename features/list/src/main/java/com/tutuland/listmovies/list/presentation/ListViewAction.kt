package com.tutuland.listmovies.list.presentation

sealed interface ListViewAction {
    data class OpenLink(val link: String) : ListViewAction
}