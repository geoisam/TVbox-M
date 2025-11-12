package com.pjs.tvbox.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pjs.tvbox.R
import com.pjs.tvbox.ui.theme.ContrastAwareReplyTheme

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun BottomNav() {
    PermissionHandler()
    val navController = rememberNavController()
    val navItems = listOf(
        NavItem("home", R.string.nav_home, R.drawable.ic_home),
        NavItem("live", R.string.nav_live, R.drawable.ic_live),
        NavItem("mine", R.string.nav_mine, R.drawable.ic_mine)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                navItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(item.iconRes),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                            )
                        },
                        label = { Text(stringResource(item.labelRes)) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            try {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            } catch (e: Exception) {
                                Log.e("BottomNav", "Navigation error: ${item.route}", e)
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomePage()
            }
            composable("live") {
                LivePage()
            }
            composable("mine") {
                MinePage(
                    onDateCardClick = {},
                    onThemeClick = {},
                    onSettingsClick = {},
                    onStarClick = {},
                    onHistoryClick = {},
                    onDownloadClick = {},
                    onSubscribeClick = {},
                    onMediaLinkClick = {},
                    onVideoClick = {},
                    onTranscodeClick = {},
                    onUpdateClick = {},
                    onChangeClick = {},
                    onAboutClick = {}
                )
            }
        }
    }
}

data class NavItem(
    val route: String,
    val labelRes: Int,
    val iconRes: Int
)

@RequiresApi(Build.VERSION_CODES.R)
@Preview(showBackground = true)
@Composable
fun BottomNavPreview() {
    ContrastAwareReplyTheme {
        BottomNav()
    }
}