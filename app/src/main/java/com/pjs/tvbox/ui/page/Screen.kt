package com.pjs.tvbox.ui.page

import com.pjs.tvbox.R

sealed class Screen(val route: String, val titleResId: Int, val iconResId: Int) {
    data object Home : Screen("home", R.string.nav_home, R.drawable.ic_home)
    data object Live : Screen("live", R.string.nav_live, R.drawable.ic_live)
    data object Mine : Screen("mine", R.string.nav_mine, R.drawable.ic_mine)
}