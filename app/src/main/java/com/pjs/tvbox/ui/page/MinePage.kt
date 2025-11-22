package com.pjs.tvbox.ui.page

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pjs.tvbox.R
import com.pjs.tvbox.util.LunarUtil
import java.util.Calendar
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinePage() {
    val context = LocalContext.current
    val dateState = remember { mutableStateMapOf<String, String>() }
    var showChangePage by remember { mutableStateOf(false) }
    var showThanksPage by remember { mutableStateOf(false) }
    var showSponsorPage by remember { mutableStateOf(false) }
    var showAboutPage by remember { mutableStateOf(false) }

    fun updateDateState() {
        dateState["yearMonth"] = LunarUtil.getYearMonth()
        dateState["shiChen"] = LunarUtil.getShiChen()
        dateState["week"] = LunarUtil.getWeek()
        dateState["day"] = LunarUtil.getDay()
        dateState["monthDay"] = LunarUtil.getMonthDay()
        dateState["ganZhi"] = LunarUtil.getGanZhi()
    }

    LaunchedEffect(Unit) {
        updateDateState()
        while (true) {
            delay(calculateTimeToNextHour())
            updateDateState()
        }
    }

    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                updateDateState()
            }
        }
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
            addAction(Intent.ACTION_DATE_CHANGED)
        }
        context.registerReceiver(receiver, filter)
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

        Scaffold(
            topBar = {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier.padding(end = 8.dp)
                                .clickable {
                                    Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show()
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_help),
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
                                        Toast.makeText(context, "主题", Toast.LENGTH_SHORT).show()
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_theme),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                            Box(
                                modifier = Modifier.padding(start = 8.dp)
                                    .clickable {
                                        Toast.makeText(context, "设置", Toast.LENGTH_SHORT).show()
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_setting),
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
                    Card(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                updateDateState()
                                Toast.makeText(context, "日历时间已更新", Toast.LENGTH_SHORT).show()
                            },
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    Text(
                                        text = dateState["yearMonth"] ?: "2002 年 1 月",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                    Text(
                                        text = dateState["shiChen"] ?: "时辰",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier
                                            .background(
                                                MaterialTheme.colorScheme.primary,
                                                RoundedCornerShape(12.dp),
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                    )
                                }
                                Text(
                                    text = dateState["week"] ?: "星期二",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                            Text(
                                text = dateState["day"] ?: "29",
                                style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(vertical = 12.dp),
                            )
                            Text(
                                text = dateState["monthDay"] ?: "腊月十七",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Text(
                                text = dateState["ganZhi"] ?: "辛巳蛇年 辛丑牛月 丁酉鸡日",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        MineCard(
                            iconRes = R.drawable.ic_star,
                            text = stringResource(R.string.mine_star),
                            onClick = {
                                Toast.makeText(context, "收藏", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f)
                                .clip(RoundedCornerShape(8.dp)),
                        )
                        MineCard(
                            iconRes = R.drawable.ic_history,
                            text = stringResource(R.string.mine_history),
                            onClick = {
                                Toast.makeText(context, "观看历史", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f)
                                .clip(RoundedCornerShape(8.dp)),
                        )
                        MineCard(
                            iconRes = R.drawable.ic_download,
                            text = stringResource(R.string.mine_download),
                            onClick = {
                                Toast.makeText(context, "下载缓存", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f)
                                .clip(RoundedCornerShape(8.dp)),
                        )
                    }
                }
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            MineItem(
                                iconRes = R.drawable.ic_subscribe,
                                text = stringResource(R.string.mine_subscribe),
                                onClick = {
                                    Toast.makeText(context, "订阅管理", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                            )
                            MineItem(
                                iconRes = R.drawable.ic_media_link,
                                text = stringResource(R.string.mine_media_link),
                                onClick = {
                                    Toast.makeText(context, "播放链接", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                            )
                            MineItem(
                                iconRes = R.drawable.ic_video,
                                text = stringResource(R.string.mine_video),
                                onClick = {
                                    Toast.makeText(context, "本地视频", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                            )
                            MineItem(
                                iconRes = R.drawable.ic_transcode,
                                text = stringResource(R.string.mine_transcode),
                                onClick = {
                                    Toast.makeText(context, "视频转码", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 8.dp),
                        ) {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = stringResource(R.string.mine_update),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_update),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_arrow_right),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                },
                                modifier = Modifier
                                    .clickable {
                                        Toast.makeText(context, "检测更新", Toast.LENGTH_SHORT)
                                            .show()
                                    },
                                colors = ListItemDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                                )
                            )
                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = stringResource(R.string.mine_change),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_change),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_arrow_right),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                },
                                modifier = Modifier
                                    .clickable {
                                        showChangePage = true
                                    },
                                colors = ListItemDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                ),
                            )
                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = stringResource(R.string.mine_thanks),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_thanks),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_arrow_right),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                },
                                modifier = Modifier
                                    .clickable {
                                        showThanksPage = true
                                    },
                                colors = ListItemDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                ),
                            )
                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = stringResource(R.string.mine_sponsor),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_sponsor),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_arrow_right),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                },
                                modifier = Modifier
                                    .clickable {
                                        showSponsorPage = true
                                    },
                                colors = ListItemDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                ),
                            )
                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = stringResource(R.string.mine_about),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                leadingContent = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_about),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_arrow_right),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                },
                                modifier = Modifier
                                    .clickable {
                                        showAboutPage = true
                                    },
                                colors = ListItemDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                )
                            )
                            if (showChangePage) {
                                ChangePage(onDismiss = { showChangePage = false })
                            }
                            if (showThanksPage) {
                                ThanksPage(onDismiss = { showThanksPage = false })
                            }
                            if (showSponsorPage) {
                                SponsorPage(onDismiss = { showSponsorPage = false })
                            }
                            if (showAboutPage) {
                                AboutPage(onDismiss = { showAboutPage = false })
                            }
                        }
                    }
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = stringResource(R.string.app_version),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp),
                        )
                    }
                }
            }
        }

}

fun calculateTimeToNextHour(): Long {
    val cal = Calendar.getInstance()
    cal.add(Calendar.HOUR_OF_DAY, 1)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.timeInMillis - System.currentTimeMillis()
}

@Composable
fun MineCard(
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
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

@Composable
fun MineItem(
    iconRes: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
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