package com.pjs.tvbox.ui.page

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pjs.tvbox.R
import com.pjs.tvbox.ui.theme.AppTheme

@Composable
fun HomePage() {
    val context = LocalContext.current


        Scaffold(
            topBar = {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    Toast.makeText(context, "数据源", Toast.LENGTH_SHORT).show()
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = stringResource(R.string.nav_default).take(4).let {
                                    if (stringResource(R.string.nav_default).length > 4) "$it…" else it
                                },
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_down),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row {
                            Box(
                                modifier = Modifier.padding(end = 8.dp)
                                    .clickable {
                                        Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show()
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_search),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                            Box(
                                modifier = Modifier.padding(start = 8.dp)
                                    .clickable {
                                        Toast.makeText(context, "更多", Toast.LENGTH_SHORT).show()
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        }
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 12.dp,
                    bottom = innerPadding.calculateBottomPadding() + 16.dp,
                )
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        HomeCard(
                            iconRes = R.drawable.ic_top,
                            text = stringResource(R.string.home_top),
                            onClick = {
                                Toast.makeText(context, "电影排名", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f)
                                .clip(RoundedCornerShape(8.dp)),
                        )
                        HomeCard(
                            iconRes = R.drawable.ic_paid,
                            text = stringResource(R.string.home_paid),
                            onClick = {
                                Toast.makeText(context, "实时票房", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f)
                                .clip(RoundedCornerShape(8.dp)),
                        )
                        HomeCard(
                            iconRes = R.drawable.ic_hot,
                            text = stringResource(R.string.home_hot),
                            onClick = {
                                Toast.makeText(context, "网播热度", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f)
                                .clip(RoundedCornerShape(8.dp)),
                        )
                        HomeCard(
                            iconRes = R.drawable.ic_tv,
                            text = stringResource(R.string.home_tv),
                            onClick = {
                                Toast.makeText(context, "电视收视", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f)
                                .clip(RoundedCornerShape(8.dp)),
                        )
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
        modifier = modifier.fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePagePreview() {
    AppTheme {
        HomePage()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomePageDarkPreview() {
    AppTheme {
        HomePage()
    }
}