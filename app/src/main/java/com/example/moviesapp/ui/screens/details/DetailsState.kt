package com.example.moviesapp.ui.screens.details

import com.example.moviesapp.domain.model.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
