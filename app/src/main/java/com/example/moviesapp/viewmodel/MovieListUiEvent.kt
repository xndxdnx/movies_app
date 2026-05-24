package com.example.moviesapp.viewmodel

sealed interface MovieListUiEvent {

    data class Paginate (
        val category: String
    ) : MovieListUiEvent

    object Navigate : MovieListUiEvent

}