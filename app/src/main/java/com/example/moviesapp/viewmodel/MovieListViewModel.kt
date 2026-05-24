package com.example.moviesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.repository.MovieListRepository
import com.example.moviesapp.util.Category
import com.example.moviesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel
@Inject constructor(
    private val repository: MovieListRepository
) : ViewModel() {
    private var _movieListState = MutableStateFlow(MovieListStates())

    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            MovieListUiEvent.Navigate -> {
                _movieListState.update { state ->
                    state.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }

            is MovieListUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovieList(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovieList(true)
                }
            }
        }
    }

    fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update { movieListState ->
                movieListState.copy(
                    isLoading = true
                )
            }
            repository.getMovieList(
                category = Category.POPULAR,
                page = movieListState.value.popularMovieListPage,
                forceFetchFromRemote = forceFetchFromRemote
            ).collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _movieListState.update { movieListStates ->
                            movieListStates.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        _movieListState.update { movieListStates ->
                            movieListStates.copy(isLoading = result.loading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { popularList ->
                            _movieListState.update { movieListStates ->
                                movieListStates.copy(
                                    popularMovieList = movieListState.value.popularMovieList
                                        +
                                    popularList.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage +1
                                )
                            }
                        }
                    }

                }
            }        // для Flow последние значения при частых запросах
        }
    }

    fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update { movieListState ->
                movieListState.copy(
                    isLoading = true
                )
            }
            repository.getMovieList(
                category = Category.UPCOMING,
                page = movieListState.value.upcomingMovieListPage,
                forceFetchFromRemote = forceFetchFromRemote
            ).collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _movieListState.update { movieListStates ->
                            movieListStates.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        _movieListState.update { movieListStates ->
                            movieListStates.copy(isLoading = result.loading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { upcomingList ->
                            _movieListState.update { movieListStates ->
                                movieListStates.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList
                                            +
                                            upcomingList.shuffled(),
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage +1
                                )
                            }
                        }
                    }

                }
            }        // для Flow последние значения при частых запросах
        }
    }
}