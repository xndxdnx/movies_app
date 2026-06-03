package com.example.moviesapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.ui.components.BottomNavigationBar
import com.example.moviesapp.util.Screens
import com.example.moviesapp.viewmodel.MovieListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    navHostController: NavHostController
) {
    val movieListViewModel = hiltViewModel<MovieListViewModel>()

    val movieListState = movieListViewModel.movieListState.collectAsState().value

    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = bottomNavController,
                onEvent = movieListViewModel::onEvent   // Передача ссылки на объект а не результат
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (movieListState.isCurrentPopularScreen){
                            "Popular Movies"
                        }else{
                            "Upcoming Movies"
                        },
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier
                    .shadow(2.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                bottomNavController,
                startDestination = Screens.PopularMovieList.route
            ) {
                composable(route = Screens.PopularMovieList.route) {
                    PopularMovieScreen(
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent,
                        navHostController = navHostController
                    )
                }
                composable(route = Screens.UpComingMovieList.route) {
                    UpcomingMovieScreen(
                        movieListState = movieListState,
                        onEvent = movieListViewModel::onEvent,
                        navHostController = navHostController
                    )
                }

            }
        }
    }

}