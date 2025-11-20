package com.pjs.tvbox.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.pjs.tvbox.ui.page.BottomNav
import com.pjs.tvbox.ui.page.HomePage
import com.pjs.tvbox.ui.page.LivePage
import com.pjs.tvbox.ui.page.MinePage
import com.pjs.tvbox.ui.page.Screen
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
    val tabs = listOf(Screen.Home, Screen.Live, Screen.Mine)
    val pagerState = rememberPagerState(initialPage = 0) { tabs.size }
    val coroutineScope = rememberCoroutineScope()
    val currentRoute by remember { derivedStateOf { tabs[pagerState.currentPage].route } }

    Scaffold(
        bottomBar = {
            BottomNav(
                currentRoute = currentRoute,
                onTabSelected = { screen ->
                    val targetIndex = tabs.indexOf(screen)
                    coroutineScope.launch {
                        pagerState.scrollToPage(targetIndex)
                    }
                }
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            when (page) {
                0 -> HomePage()
                1 -> LivePage()
                2 -> MinePage()
            }
        }
    }
}