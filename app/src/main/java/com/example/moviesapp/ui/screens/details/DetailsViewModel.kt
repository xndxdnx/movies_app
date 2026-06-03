package com.example.moviesapp.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.repository.MovieListRepository
import com.example.moviesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val  repository: MovieListRepository,
    savedStateHandle: SavedStateHandle  // обработчик сохранения состояния нашей viewModel при изменении состояния\конфигурации
) : ViewModel() {
    private val movieId = savedStateHandle.get<Int>("id")?: -1

    private val _detailsState = MutableStateFlow(DetailsState())

    val detailsState = _detailsState.asStateFlow()

    init {
        getMovie(movieId)
    }

    fun getMovie(id: Int ) {
        viewModelScope.launch {

            _detailsState.update { detailsState ->
                detailsState.copy(isLoading = true)
            }

            repository.getMovie(id = id).collectLatest { result ->
                when(result) {
                    is Resource.Error -> _detailsState.update { detailsState ->
                        detailsState.copy(isLoading = false, movie = null)
                    }
                    is Resource.Loading -> _detailsState.update { detailsState ->
                        detailsState.copy(isLoading = result.loading)
                    }
                    is Resource.Success -> _detailsState.update { detailsState ->
                        detailsState.copy(isLoading = false, movie = result.data)
                    }
                }
            }

        }
    }

}