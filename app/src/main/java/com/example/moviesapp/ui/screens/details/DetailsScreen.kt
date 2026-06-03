package com.example.moviesapp.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.moviesapp.data.remote.MovieApi
import com.example.moviesapp.util.RatingBar

@Composable
fun DetailsScreen() {

    val viewModel = hiltViewModel<DetailsViewModel>()

    val detailsState = viewModel.detailsState.collectAsState().value

    val backDropPath = detailsState.movie?.backdrop_path.orEmpty()

    val posterImagePath = detailsState.movie?.poster_path.orEmpty()

    val backDropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)      // билдер/фабрика для формирования запроса на загрузку изображения
            .data(MovieApi.IMAGE_BASE_URL + backDropPath.removePrefix("/"))
            .size(Size.ORIGINAL)
            .build()
    ).state // получим её состояние подкапотное

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + posterImagePath.removePrefix("/"))
            .size(Size.ORIGINAL)
            .build()
    ).state // получим её состояние подкапотное



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            when {
                backDropPath.isEmpty() || backDropImageState
                        is AsyncImagePainter.State.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = null,
                            modifier = Modifier
                                .size(70.dp)
                        )
                    }
                }

                backDropImageState is AsyncImagePainter.State.Success -> {
                    Image(
                        painter = backDropImageState.painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                else -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp)
            ) {
                when {
                    posterImagePath.isEmpty() || posterImageState
                            is AsyncImagePainter.State.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                                .clip(RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ImageNotSupported,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(70.dp)
                            )
                        }
                    }
                    posterImageState is AsyncImagePainter.State.Success -> {
                        Image(
                            painter = posterImageState.painter,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    else -> Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.primaryContainer)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
            detailsState.movie?.let { movie ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = movie.title,
                        modifier = Modifier
                            .padding(start = 16.dp),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp)
                    ) {
                        RatingBar(
                            starModifier = Modifier.size(18.dp),
                            rating = movie.vote_average / 2
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            text = movie.vote_average.toString().take(3),
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )

                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp),
                        text = "Language: ${movie.original_language}",
                        fontSize = 14.sp,
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp),
                        text = "Release Date: ${movie.release_date}",
                        fontSize = 14.sp,
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp),
                        text = "${movie.vote_count} Votes ",
                        fontSize = 14.sp,
                    )
                }

            }

        }
        Spacer(Modifier.height(32.dp))
        detailsState.movie?.let { movie ->
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = "Overview",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = movie.overview,
                fontSize = 16.sp,
            )

        }


    }


}