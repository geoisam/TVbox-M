package com.pjs.tvbox.model

data class ToolItem(
    val icon: Int,
    val title: String,
    val subtitle: String,
    val onClick: () -> Unit,
)