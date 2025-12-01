package com.pjs.tvbox.model

data class Movie(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val cover: String,
    val coverLarge: String,
    val rating: String? = null,
)