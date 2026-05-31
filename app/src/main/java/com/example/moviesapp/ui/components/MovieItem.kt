package com.example.moviesapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesapp.data.remote.MovieApi
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.util.RatingBar
import com.example.moviesapp.util.Screens

@Composable
fun MovieItem (
    movie: Movie,
    navHostController: NavHostController
) {
    val posterPath = movie.poster_path
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + posterPath.removePrefix("/"))
            .size ( Size.ORIGINAL)
            .build()
    ).state
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    val dominantColor by remember { mutableStateOf(defaultColor) }
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        dominantColor
                    )
                )
            ).clickable{
                navHostController.navigate(route = Screens.Details.route + "/${movie.id}")
            }
    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = null
                )
            }
        }
        if (imageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(22.dp)),
                painter = imageState.painter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = null
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = movie.title,
            color = Color.White,
            modifier = Modifier
                .padding(start = 26.dp, end = 8.dp),
            maxLines = 1
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 12.dp, top = 4.dp)
        ) {
            RatingBar(
                starModifier = Modifier.size(18.dp),
                rating = movie.vote_average / 2
            )
            Text(
                text = movie.vote_average.toString().take(3),
                modifier = Modifier
                    .padding(start = 4.dp),
                color = Color.LightGray,
                maxLines = 1,
                fontSize = 14.sp
            )
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun MovieItemPreview() {
//    val navController = rememberNavController()
//    MovieItem(
//        movie = Movie(
//            id = 1,
//            title = "Interstellar",
//            poster_path = "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
//            vote_average = 8.6,
//
//            adult = false,
//            backdrop_path = "/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg",
//            genre_ids = listOf(12, 18, 878),
//            original_language = "en",
//            original_title = "Interstellar",
//            overview = "A team of explorers travel through a wormhole in space.",
//            popularity = 1234.5,
//            release_date = "2014-11-07",
//            video = false,
//            vote_count = 32567,
//            category = "popular"
//        ),
//        navHostController = navController
//    )
//}