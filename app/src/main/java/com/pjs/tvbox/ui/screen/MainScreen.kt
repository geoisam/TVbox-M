package com.pjs.tvbox.ui.screen

import com.pjs.tvbox.R

sealed class MainScreen(val route: String, val titleResId: Int, val iconResId: Int) {
    data object Home : MainScreen("home", R.string.nav_home, R.drawable.ic_home)
    data object Discover : MainScreen("discover", R.string.nav_discover, R.drawable.ic_explore)
    data object Mine : MainScreen("mine", R.string.nav_mine, R.drawable.ic_person)
}