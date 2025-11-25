package com.pjs.tvbox.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pjs.tvbox.ui.page.AboutPage
import com.pjs.tvbox.ui.page.BottomNav
import com.pjs.tvbox.ui.page.HomePage
import com.pjs.tvbox.ui.page.DiscoverPage
import com.pjs.tvbox.ui.page.MinePage
import com.pjs.tvbox.bean.MainBean
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    var overlayPage by remember { mutableStateOf<OverlayPage?>(null) }
    val tabs = listOf(MainBean.Home, MainBean.Discover, MainBean.Mine)
    val pagerState = rememberPagerState(initialPage = 0) { tabs.size }
    val coroutineScope = rememberCoroutineScope()
    val currentPage by remember { derivedStateOf { pagerState.currentPage } }
    LaunchedEffect(currentPage) { }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0.dp),
            bottomBar = {
                BottomNav(
                    currentRoute = tabs[pagerState.currentPage].route,
                    onTabSelected = { screen ->
                        val targetIndex = tabs.indexOf(screen)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(targetIndex)
                        }
                    }
                )
            }
        ) { padding ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                beyondViewportPageCount = 2,
            ) { page ->
                when (tabs[page]) {
                    MainBean.Home -> HomePage()
                    MainBean.Discover -> DiscoverPage()
                    MainBean.Mine -> MinePage(onOpenPage = { overlayPage = it })
                }
            }
        }
        overlayPage?.let { page ->
            when (page) {
                is OverlayPage.About -> AboutPage { overlayPage = null }
            }
        }
    }
}

sealed class OverlayPage {
    object About : OverlayPage()
}