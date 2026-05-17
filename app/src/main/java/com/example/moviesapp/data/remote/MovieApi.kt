package com.example.moviesapp.data.remote

import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.remote.respond.MovieDto
import com.example.moviesapp.data.remote.respond.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {
    @GET ("movie/{category}") // для получения данных

    suspend fun getMovieList (
        @Path ("category") category: String,    // предоставляет значения в url в место категория

        @Query ("page") page: Int,   // запрос к библиотеке Retrofit

        @Query ("api_key") apiKey: String  = API_KEY// запрос к библиотеке Retrofit
    ) : MovieListDto

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"

        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"

        const val API_KEY = BuildConfig.MOVIE_API_KEY
    }
}