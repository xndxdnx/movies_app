package com.example.moviesapp.data.remote.respond

import com.google.gson.annotations.SerializedName

data class MovieListDto(
    val page: Int,
    @SerializedName("results")
    val result: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)
