package com.pjs.tvbox.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pjs.tvbox.theme.ContrastAwareReplyTheme
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

@Composable
fun LivePage() {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        PlayerFactory.setPlayManager(IjkPlayerManager::class.java)
        onDispose {
            GSYVideoManager.releaseAllVideos()
        }
    }

    Scaffold(
        topBar = {
            AndroidView(
                factory = { ctx ->
                    StandardGSYVideoPlayer(ctx).apply {
                        setUp(
                            "http://live.zohi.tv/video/s10001-fztv-4/index.m3u8",
                            true,
                            "电视直播"
                        )
                        isNeedShowWifiTip = true
                        isAutoFullWithSize = true
                        isLooping = false
                        startPlayLogic()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(MaterialTheme.colorScheme.surface),
                update = { player ->
                    val manager = player.getGSYVideoManager()
                    if (manager.isPlaying) {
                        player.onVideoResume()
                    } else {
                        player.onVideoPause()
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {}
}

@Preview(showBackground = true)
@Composable
fun LivePagePreview() {
    ContrastAwareReplyTheme {
        LivePage()
    }
}