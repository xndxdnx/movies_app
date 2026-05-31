package com.example.moviesapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.moviesapp.util.Screens
import com.example.moviesapp.viewmodel.MovieListUiEvent
import java.util.Vector

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    onEvent:(MovieListUiEvent) -> Unit
) {
    val items = listOf(
        BottomItem("Popular", icon = Icons.Rounded.Movie),
        BottomItem("Upcoming", icon = Icons.Rounded.Upcoming)
    )

    val selected = rememberSaveable { mutableIntStateOf(0) }

    NavigationBar {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == selected.intValue,
                    onClick = {
                        selected.intValue = index
                        when(selected.intValue){
                            0 -> { onEvent(MovieListUiEvent.Navigate)
                                 navController.popBackStack()
                                navController.navigate(route = Screens.PopularMovieList.route)
                                 }
                            1 -> {onEvent(MovieListUiEvent.Navigate)
                                navController.popBackStack()
                                navController.navigate(route = Screens.UpComingMovieList.route)
                            }
                        }
                    },
                    icon ={
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }

}

data class BottomItem(
    val title: String,
    val icon: ImageVector
)