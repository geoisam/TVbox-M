package com.pjs.tvbox.bean

import com.pjs.tvbox.R

sealed class MainBean(val route: String, val titleResId: Int, val iconResId: Int) {
    data object Home : MainBean("home", R.string.nav_home, R.drawable.ic_home)
    data object Discover : MainBean("discover", R.string.nav_discover, R.drawable.ic_explore)
    data object Mine : MainBean("mine", R.string.nav_mine, R.drawable.ic_person)
}