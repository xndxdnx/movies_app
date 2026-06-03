package com.example.moviesapp.util


sealed class Screens(
    val route : String,
) {
    object Home: Screens(route = "home")
    object Details: Screens(route = "details")
    object PopularMovieList: Screens(route = "popular")
    object UpComingMovieList: Screens(route = "upcoming")
}