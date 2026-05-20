package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    suspend fun getMovieList(
        category: String,
        page: Int,
        forceFetchFromRemote: Boolean
    ) : Flow<Resource<List<Movie>>>

    suspend fun getMovie(
        id: Int
    ) : Flow<Resource<Movie>>


}