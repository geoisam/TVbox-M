package com.pjs.tvbox.ui.page

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.text.format.Formatter
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.pjs.tvbox.R
import com.pjs.tvbox.OverlayPage
import com.pjs.tvbox.data.UpdateData
import com.pjs.tvbox.model.Update
import com.pjs.tvbox.ui.dialog.TipsDialog
import com.pjs.tvbox.ui.theme.LogoFont
import com.pjs.tvbox.util.LunarUtil
import com.pjs.tvbox.util.AppUtil
import com.pjs.tvbox.util.UpdateUtil
import kotlinx.coroutines.Dispatchers
import java.util.Calendar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinePage(
    onOpenPage: (OverlayPage) -> Unit
) {
    val context = LocalContext.current
    val showTipsDialog = remember { mutableStateOf(false) }
    val showUpdateDialog = remember { mutableStateOf(false) }
    val dateState = remember { mutableStateMapOf<String, String>() }
    var updateInfo by remember { mutableStateOf<Update?>(null) }
    val isChecking = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun updateDateState() {
        dateState["yearMonth"] = LunarUtil.getYearMonth()
        dateState["shiChen"] = LunarUtil.getShiChen()
        dateState["week"] = LunarUtil.getWeek()
        dateState["day"] = LunarUtil.getDay()
        dateState["monthDay"] = LunarUtil.getMonthDay()
        dateState["ganZhi"] = LunarUtil.getGanZhi()
        dateState["jieQi"] = LunarUtil.getJieQi()
        dateState["dayYi"] = LunarUtil.getDayYi()
        dateState["dayJi"] = LunarUtil.getDayJi()
        dateState["dayChong"] = LunarUtil.getDayChong()
    }

    LaunchedEffect(Unit) {
        UpdateUtil.currentUpdate()?.let { updateInfo = it }
        updateDateState()
        while (true) {
            delay(calculateTimeToNextHour())
            updateDateState()
        }
    }

    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(c: Context?, intent: Intent?) {
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
            try {
                context.unregisterReceiver(receiver)
            } catch (e: Exception) {
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            showTipsDialog.value = true
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
                            Toast.makeText(context, "主题", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_shirt),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    IconButton(
                        onClick = {
                            Toast.makeText(context, "设置", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_settings),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            )
            TipsDialog(
                isOpen = showTipsDialog.value,
                onClose = { showTipsDialog.value = false },
                title = "友情提示",
                message = "本软件一直为 开源免费 使用，如果你是从某些地方付费购买的话，那你就是被骗了！",
                confirmButtonText = "知道了",
                onConfirm = { },
                dismissButtonText = null,
                onDismiss = { },
                closeIcon = false,
                onCloseIconClick = { }
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .clickable { },
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
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
                                    text = dateState["yearMonth"] ?: "阳历年 阳历月",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                )
                                Box(
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.78f),
                                            MaterialTheme.shapes.medium
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = dateState["shiChen"] ?: "时辰",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                                if (dateState["jieQi"]?.isNotBlank() == true) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.78f),
                                                MaterialTheme.shapes.medium
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = dateState["jieQi"] ?: "节气",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                }
                            }
                            Text(
                                text = dateState["week"] ?: "星期",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(
                            text = dateState["day"] ?: "阳历日",
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = dateState["monthDay"] ?: "阴历月日",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = dateState["ganZhi"] ?: "年干支 月干支 日干支",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .clickable { },
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ){
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xff00b51d).copy(alpha = 0.78f),
                                        CircleShape,
                                    )
                                    .padding(horizontal = 4.dp, vertical = 2.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "宜",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Text(
                                text = dateState["dayYi"] ?: "无",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ){
                            Box(

                                modifier = Modifier
                                    .background(
                                        Color(0xffff2442).copy(alpha = 0.78f),
                                        CircleShape
                                    )
                                    .padding(horizontal = 4.dp, vertical = 2.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "忌",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Text(
                                text = dateState["dayJi"] ?: "无",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ){
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xffff6700).copy(alpha = 0.78f),
                                        CircleShape
                                    )
                                    .padding(horizontal = 4.dp, vertical = 2.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "冲",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Text(
                                text = dateState["dayChong"] ?: "冲煞",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
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
                        text = "收藏夹",
                        onClick = {
                            Toast.makeText(context, "收藏夹", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                    )
                    MineCard(
                        iconRes = R.drawable.ic_history,
                        text = "观看历史",
                        onClick = {
                            Toast.makeText(context, "观看历史", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                    )
                    MineCard(
                        iconRes = R.drawable.ic_cloud_download,
                        text = "下载缓存",
                        onClick = {
                            Toast.makeText(context, "下载缓存", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small),
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        MineItem(
                            iconRes = R.drawable.ic_database,
                            text = "订阅管理",
                            onClick = {
                                Toast.makeText(context, "订阅管理", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f),
                        )
                        MineItem(
                            iconRes = R.drawable.ic_media_link,
                            text = "播放链接",
                            onClick = {
                                Toast.makeText(context, "播放链接", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f),
                        )
                        MineItem(
                            iconRes = R.drawable.ic_videos,
                            text = "本地视频",
                            onClick = {
                                Toast.makeText(context, "本地视频", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f),
                        )
                        MineItem(
                            iconRes = R.drawable.ic_storage,
                            text = "备份与恢复",
                            onClick = {
                                Toast.makeText(context, "备份恢复", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small),
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                    ) {
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = "版本升级",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            },
                            leadingContent = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_rocket),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                            },
                            trailingContent = {
                                if (isChecking.value) {
                                    CircularProgressIndicator()
                                }else {
                                    if (updateInfo == null) {
                                        Text(
                                            text = "已是最新版本",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.78f),
                                                    MaterialTheme.shapes.large
                                                )
                                                .padding(horizontal = 7.dp, vertical = 3.dp),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                text = "发现新版本",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                            )
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .clickable {
                                    if (isChecking.value) return@clickable
                                    if(updateInfo != null){
                                        showUpdateDialog.value = true
                                    } else {
                                        isChecking.value = true
                                        scope.launch {
                                            try {
                                            UpdateUtil.clearUpdate()
                                            updateInfo = UpdateData.getUpdate(context)

                                                withContext(Dispatchers.Main) {
                                                    if (updateInfo != null) {
                                                        val remoteVersionName = updateInfo?.versionName?.replace(".", "")?.toLongOrNull() ?: 0L
                                                        val localVersionName = AppUtil.getVersionName(context).replace(".", "").toLongOrNull() ?: 0L
                                                        if (remoteVersionName > localVersionName) {
                                                            showUpdateDialog.value = true
                                                        } else {
                                                            Toast.makeText(
                                                                context,
                                                                "已是最新版本！",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    } else {
                                                        Toast.makeText(context, "检测更新失败！", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            } catch (e: Exception) {
                                                withContext(Dispatchers.Main) {
                                                    Toast.makeText(context, "检测更新失败！", Toast.LENGTH_SHORT).show()
                                                }
                                            } finally {
                                                isChecking.value = false
                                            }
                                        }
                                    }
                                },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        )
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = "关于",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            },
                            leadingContent = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_info),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                            },
                            trailingContent = {
                                Text(
                                    text = stringResource(R.string.app_name),
                                    fontFamily = LogoFont,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            },
                            modifier = Modifier
                                .clickable {
                                    onOpenPage(OverlayPage.About)
                                },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }
        }
        updateInfo?.let { update ->
            TipsDialog(
                isOpen = showUpdateDialog.value,
                onClose = { showUpdateDialog.value = false },
                title = "发现新版本 v${update.versionName}",
                message = "版本：${update.versionCode}\n" +
                        "大小：${Formatter.formatFileSize(LocalContext.current, update.appSize)}\n" +
                        "\n更新日志：\n${update.changeLog.ifBlank { "修复了一些已知问题" }}".trimIndent(),
                confirmButtonText = "更新",
                onConfirm = {
                    val url = when {
                        update.downloadUrl.startsWith("http") -> update.downloadUrl
                        else -> "https://github.com/geoisam/TVB-Mobile/releases"
                    }
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
                dismissButtonText = "取消",
                onDismiss = { showUpdateDialog.value = false },
                closeIcon = false,
                onCloseIconClick = { showUpdateDialog.value = false },
            )
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
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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