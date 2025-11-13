package com.pjs.tvbox.ui

import android.os.Build
import android.util.Log
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pjs.tvbox.R
import com.pjs.tvbox.ui.theme.ContrastAwareReplyTheme
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition

fun setImmersiveStatusBar(window: Window) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = false
    }
}

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

    val context = LocalContext.current
    (context as? ComponentActivity)?.window?.let { window ->
        setImmersiveStatusBar(window)
    }

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
        val modifier = Modifier
            .padding(innerPadding)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    val currentRoute = navController.currentBackStackEntry?.destination?.route
                    val currentIndex = navItems.indexOfFirst { it.route == currentRoute }
                    Log.d("BottomNav", "Current route: $currentRoute, Index: $currentIndex, Drag: $dragAmount")

                    when {
                        dragAmount < -100 && currentIndex < navItems.size - 1 -> {
                            navController.navigate(navItems[currentIndex + 1].route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        dragAmount > 100 && currentIndex > 0 -> {
                            navController.navigate(navItems[currentIndex - 1].route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                }
            }

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = modifier,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            composable("home") {
                HomePage()
            }
            composable("live") {
                LivePage()
            }
            composable("mine") {
                MinePage()
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