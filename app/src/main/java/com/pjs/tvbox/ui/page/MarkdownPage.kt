package com.pjs.tvbox.ui.page

import android.graphics.drawable.ColorDrawable
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil3.compose.LocalPlatformContext
import coil3.ImageLoader
import coil3.request.ImageRequest
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.ins.InsExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import com.pjs.tvbox.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkdownPage(
    pageTitle: String,
    assetFile: String,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val platformContext = LocalPlatformContext.current
    val imageLoader = remember { ImageLoader(platformContext) }
    val placeholder = remember { ColorDrawable(Color.Transparent.toArgb()) }

    val markdownText = remember(assetFile) {
        runCatching { context.assets.open(assetFile).bufferedReader().use { it.readText() } }
            .getOrDefault("加载失败")
    }

    val parser = remember {
        Parser.builder()
            .extensions(
                listOf(
                    TablesExtension.create(),
                    StrikethroughExtension.create(),
                    InsExtension.create()
                )
            )
            .build()
    }

    val renderer = remember {
        HtmlRenderer.builder()
            .extensions(
                listOf(
                    TablesExtension.create(),
                    StrikethroughExtension.create(),
                    InsExtension.create()
                )
            )
            .build()
    }

    val html = remember(markdownText) {
        renderer.render(
            parser.parse(markdownText)
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = pageTitle,
                        style = MaterialTheme.typography.titleMedium,
                )
                        },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                },
                modifier = Modifier.statusBarsPadding(),
            )
        }
    ) { padding ->
        AndroidView(
            factory = {
                TextView(it).apply {
                    movementMethod = LinkMovementMethod.getInstance()
                    setTextIsSelectable(true)
                }
            },
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp, bottom = 32.dp)
                .verticalScroll(rememberScrollState()),
            update = { textView ->
                textView.text = android.text.Html.fromHtml(
                    html,
                    android.text.Html.FROM_HTML_MODE_COMPACT,
                    { source ->
                        if (source.isNullOrBlank()) return@fromHtml placeholder
                        imageLoader.enqueue(
                            ImageRequest.Builder(platformContext)
                                .data(source)
                                .target()
                                .build()
                        )
                        placeholder
                    },
                    null
                )
            }
        )
    }
}