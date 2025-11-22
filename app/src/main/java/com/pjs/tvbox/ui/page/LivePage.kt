package com.pjs.tvbox.ui.page

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import android.view.SurfaceView
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pjs.tvbox.bean.LivePlayerBean

private const val HLS_URL = "https://gcalic.v.myalicdn.com/gc/wgw05_1/index.m3u8?contentid=2820180516001"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LivePage(
    liveBean: LivePlayerBean = LivePlayerBean(
        title = "直播测试",
        url = HLS_URL
    )
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val playerBean = remember(liveBean) {
        liveBean.apply {
            initPlayer(context)
            bindLifecycle(lifecycleOwner)
        }
    }

    DisposableEffect(playerBean) {
        onDispose {
            playerBean.releasePlayer()
        }
    }

        Scaffold(
            topBar = {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                            .focusable()
                            .padding(start = 0.dp, end = 0.dp, top = 0.dp),
                    ) {
                        AndroidView(
                            factory = { _ ->
                                SurfaceView(context).apply {
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                    playerBean.getExoPlayer()?.setVideoSurfaceView(this)
                                }
                            },
                            modifier = Modifier.fillMaxSize(),
                            update = { surfaceView ->
                                playerBean.getExoPlayer()?.setVideoSurfaceView(surfaceView)
                            }
                        )

                        playerBean.errorMessage?.let { errorMsg ->
                            Text(
                                text = errorMsg,
                                color = Color.Red,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 0.dp,
                    bottom = innerPadding.calculateBottomPadding() + 16.dp,
                )
            ) {
                item {
                    Text(
                        text = liveBean.title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
}
