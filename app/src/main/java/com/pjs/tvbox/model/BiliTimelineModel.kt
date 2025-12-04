package com.pjs.tvbox.model

data class TimelineDate(
    val date: String,
    val dayOfWeek: Int,
    val isToday: Int,
    val episodes: List<TimelineAnime>?,
)

data class TimelineAnime(
    val seasonId: Long,
    val episodeId: Long,
    val cover: String,
    val epCover: String,
    val squareCover: String,
    val pubIndex: String,
    val pubTime: String,
    val published: Int,
    val title: String,
)
