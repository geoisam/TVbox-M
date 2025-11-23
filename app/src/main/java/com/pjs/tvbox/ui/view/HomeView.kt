package com.pjs.tvbox.ui.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.pjs.tvbox.R
import com.pjs.tvbox.data.DouBanHotData
import com.pjs.tvbox.model.Movie

@Composable
fun DBHotView(modifier: Modifier = Modifier) {
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        try {
            movies = DouBanHotData.getHotMovies()
        } catch (e: Exception) {
            error = e.message ?: "未知错误"
        } finally {
            isLoading = false
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(strokeWidth = 4.dp)
            }

            error != null -> {
                Text(
                    text = "加载失败\n\n$error",
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            }

            movies.isEmpty() -> {
                Text(
                    text = "暂无数据",
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(top = 4.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    item(span = { GridItemSpan(3) }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            HomeCard(
                                iconRes = R.drawable.ic_top,
                                text = stringResource(R.string.home_top),
                                onClick = {
                                    Toast.makeText(context, "电影排名", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f)
                            )
                            HomeCard(
                                iconRes = R.drawable.ic_paid,
                                text = stringResource(R.string.home_paid),
                                onClick = {
                                    Toast.makeText(context, "近期票房", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f)
                            )
                            HomeCard(
                                iconRes = R.drawable.ic_hot,
                                text = stringResource(R.string.home_hot),
                                onClick = {
                                    Toast.makeText(context, "网播热度", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f)
                            )
                            HomeCard(
                                iconRes = R.drawable.ic_tv,
                                text = stringResource(R.string.home_tv),
                                onClick = {
                                    Toast.makeText(context, "电视收视", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                    items(movies, key = { it.id }) { movie ->
                        MovieCard(movie = movie)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeCard(
    iconRes: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.69f)
            .clickable {
                val url = movie.cover
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Box {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data("https://images.weserv.nl/?url=" + movie.cover)
                    .crossfade(true)
                    .build(),
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(strokeWidth = 4.dp)
                    }
                },
                error = {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("图片加载失败",
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelSmall,
                            )
                    }
                },
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
            )

            movie.rating?.let { rating ->
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.78f),
                            RoundedCornerShape(bottomStart = 8.dp, topEnd = 8.dp)
                        )
                        .padding(horizontal = 7.dp, vertical = 3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = rating,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        Color.Black.copy(alpha = 0.78f),
                        RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}