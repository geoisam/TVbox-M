package com.pjs.tvbox.ui.page

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pjs.tvbox.R
import com.pjs.tvbox.model.ToolItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverPage() {
    val context = LocalContext.current

    val liveTitle = stringResource(R.string.tools_live)
    val m3u8Title = stringResource(R.string.tools_m3u8)
    val topTitle = stringResource(R.string.tools_top)
    val hotTitle = stringResource(R.string.tools_hot)
    val ticketTitle = stringResource(R.string.tools_ticket)
    val ratingTitle = stringResource(R.string.tools_rating)
    val newsTitle = stringResource(R.string.tools_news)
    val watermarkTitle = stringResource(R.string.tools_watermark)

    val toolsList = listOf(
        ToolItem(
            icon = R.drawable.ic_tv,
            title = liveTitle,
            subtitle = stringResource(R.string.tools_live_desc),
            onClick = {
                Toast.makeText(context, liveTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_change_circle,
            title = m3u8Title,
            subtitle = stringResource(R.string.tools_m3u8_desc),
            onClick = {
                Toast.makeText(context, liveTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_movie,
            title = topTitle,
            subtitle = stringResource(R.string.tools_top_desc),
            onClick = {
                Toast.makeText(context, topTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_filter_vintage,
            title = hotTitle,
            subtitle = stringResource(R.string.tools_hot_desc),
            onClick = {
                Toast.makeText(context, hotTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_local_atm,
            title = ticketTitle,
            subtitle = stringResource(R.string.tools_ticket_desc),
            onClick = {
                Toast.makeText(context, ticketTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_whatshot,
            title = ratingTitle,
            subtitle = stringResource(R.string.tools_rating_desc),
            onClick = {
                Toast.makeText(context, ratingTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_news,
            title = newsTitle,
            subtitle = stringResource(R.string.tools_news_desc),
            onClick = {
                Toast.makeText(context, newsTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_psychiatry,
            title = watermarkTitle,
            subtitle = stringResource(R.string.tools_watermark_desc),
            onClick = {
                Toast.makeText(context, watermarkTitle, Toast.LENGTH_SHORT).show()
            }
        ),
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        modifier = Modifier.padding(horizontal = 4.dp),
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.extraLarge)
                                .clickable {
                                    Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show()
                                },
                            shape = MaterialTheme.shapes.extraLarge,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = stringResource(R.string.nav_search),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                                Icon(
                                    painter = painterResource(R.drawable.ic_search),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            Toast.makeText(context, "帮助", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_help),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            Toast.makeText(context, "更多", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add_circle),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(toolsList.size) { index ->
                    val tool = toolsList[index]
                    ToolsCard(
                        logo = tool.icon,
                        title = tool.title,
                        subtitle = tool.subtitle,
                        onClick = tool.onClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun ToolsCard(
    logo: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Image(
                painter = painterResource(logo),
                contentDescription = null,
                modifier = Modifier.size(41.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}