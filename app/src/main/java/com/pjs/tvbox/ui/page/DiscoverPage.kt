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
    val liveSubTitle = stringResource(R.string.tools_live_desc)
    val topTitle = stringResource(R.string.tools_top)
    val topSubTitle = stringResource(R.string.tools_top_desc)
    val hotTitle = stringResource(R.string.tools_hot)
    val hotSubTitle = stringResource(R.string.tools_hot_desc)
    val rateTitle = stringResource(R.string.tools_rate)
    val rateSubTitle = stringResource(R.string.tools_rate_desc)
    val paidTitle = stringResource(R.string.tools_paid)
    val paidSubTitle = stringResource(R.string.tools_paid_desc)
    val newsTitle = stringResource(R.string.tools_news)
    val newsSubTitle = stringResource(R.string.tools_news_desc)

    val toolsList = listOf(
        ToolItem(
            icon = R.drawable.ic_tv,
            liveTitle, liveSubTitle,
            onClick = {
                Toast.makeText(context, liveTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_movie,
            topTitle, topSubTitle,
            onClick = {
                Toast.makeText(context, topTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_hot,
            hotTitle, hotSubTitle,
            onClick = {
                Toast.makeText(context, hotTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_rate,
            rateTitle, rateSubTitle,
            onClick = {
                Toast.makeText(context, rateTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_paid,
            paidTitle, paidSubTitle,
            onClick = {
                Toast.makeText(context, paidTitle, Toast.LENGTH_SHORT).show()
            }
        ),
        ToolItem(
            icon = R.drawable.ic_news,
            newsTitle, newsSubTitle,
            onClick = {
                Toast.makeText(context, newsTitle, Toast.LENGTH_SHORT).show()
            }
        ),
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp),
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
                actions = {
                    IconButton(
                        onClick = {
                            Toast.makeText(context, "更多", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
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
                contentPadding = PaddingValues(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(toolsList.size) { index ->
                    val tool = toolsList[index]
                    ToolsCard(
                        iconRes = tool.icon,
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
    iconRes: Int,
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
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(44.dp),
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