package com.example.moviesapp.viewmodel

import com.example.moviesapp.domain.model.Movie

data class MovieListStates(
    val isLoading: Boolean = false,
    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,
    val isCurrentPopularScreen: Boolean = true,
    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList()
)
