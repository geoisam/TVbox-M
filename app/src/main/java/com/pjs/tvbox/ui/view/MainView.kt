package com.pjs.tvbox.ui.view

import com.pjs.tvbox.R

sealed class MainView(val route: String, val titleResId: Int, val iconResId: Int) {
    data object Main : MainView("home", R.string.nav_home, R.drawable.ic_home)
    data object Live : MainView("live", R.string.nav_live, R.drawable.ic_live)
    data object Mine : MainView("mine", R.string.nav_mine, R.drawable.ic_mine)
}