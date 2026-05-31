package com.example.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesapp.ui.screens.HomeScreen
import com.example.moviesapp.util.Screens

@Composable
fun NavGraph (

) {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = Screens.Home.route
    ){
        composable (Screens.Home.route) {
            HomeScreen(
                navController
            )
        }
        composable (
            Screens.Details.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {

        }
    }
}