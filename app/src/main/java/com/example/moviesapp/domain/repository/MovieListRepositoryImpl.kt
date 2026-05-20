package com.example.moviesapp.domain.repository

import coil.network.HttpException
import com.example.moviesapp.data.local.MovieDatabase
import com.example.moviesapp.data.mappers.toMovie
import com.example.moviesapp.data.mappers.toMovieEntity
import com.example.moviesapp.data.remote.MovieApi
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject


class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {
    override suspend fun getMovieList(
        category: String,
        page: Int,
        forceFetchFromRemote: Boolean
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            val localMovieList = movieDatabase.movieDao.getMovieByCategory(category)

            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovie) {
                emit(Resource.Success(data = localMovieList.map { movieEntity ->
                    movieEntity.toMovie(category)
                }))
                emit(Resource.Loading(false))

                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMovieList(category,page)
            }catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error( message = "Error loading movies"))
                return@flow
            }catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error( message = "Error loading movies"))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Resource.Error( message = "Error loading movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.result.let { listMovieDto ->
                listMovieDto.map { movieDto ->
                    movieDto.toMovieEntity(
                        category
                    )
                }
            }

            movieDatabase.movieDao.upsertMovieList(
                movieEntities
            )

            emit(Resource.Success(movieEntities.map { movieEntity ->
                movieEntity.toMovie(category)
            }))
            emit(Resource.Loading(false))
        }

    }

///////////////////////////////////////////////////////////////////////

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovieById(id)

            if (movieEntity != null){
                emit(Resource.Success(data = movieEntity.toMovie(movieEntity.category)))
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Error(message = "Error: no such movie"))

            emit(Resource.Loading(false))
        }
    }


}