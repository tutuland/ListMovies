package com.tutuland.listmovies.list.presentation

internal sealed interface ListViewAction {
    data class OpenLink(val link: String) : ListViewAction
}