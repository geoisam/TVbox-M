package com.pjs.tvbox.bean

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class LivePlayerBean(
    val title: String,
    val url: String
) {
    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var exoPlayer: ExoPlayer? = null

    fun initPlayer(context: Context) {
        if (exoPlayer != null) return

        exoPlayer = ExoPlayer.Builder(context)
            .setHandleAudioBecomingNoisy(true)
            .build()
            .apply {
                repeatMode = Player.REPEAT_MODE_ONE
                playWhenReady = true
                setMediaItem(MediaItem.fromUri(url))

                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                        if (playbackState == Player.STATE_IDLE) {
                            errorMessage = "播放失败，请检查网络或直播地址"
                        }
                    }
                })

                prepare()
            }
    }

    fun bindLifecycle(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> exoPlayer?.pause()
                    Lifecycle.Event.ON_RESUME -> if (exoPlayer?.playWhenReady == true) exoPlayer?.play()
                    Lifecycle.Event.ON_DESTROY -> releasePlayer()
                    else -> Unit
                }
            }
        })
    }
    fun getExoPlayer(): ExoPlayer? = exoPlayer

    fun releasePlayer() {
        exoPlayer?.apply {
            release()
        }
        exoPlayer = null
    }
}