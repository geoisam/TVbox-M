package com.pjs.tvbox.data

import com.pjs.tvbox.model.Movie
import com.pjs.tvbox.network.PJS
import com.pjs.tvbox.network.PJSRequest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.net.URI

object DouBanHotData {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    suspend fun getHotMovies(): List<Movie> = runCatching {
        val response = PJS.request(
            PJSRequest(
                url = "https://m.douban.com/rexxar/api/v2/subject/recent_hot/movie?start=0&limit=12",
                headers = mapOf("Referer" to "https://movie.douban.com/")
            )
        )

        if (response.status != 200) return@runCatching emptyList()

        val root = when (val body = response.response) {
            is JsonElement -> body
            is String -> json.parseToJsonElement(body)
            else -> return@runCatching emptyList()
        }

        val items = root.jsonObject["items"]?.jsonArray ?: return@runCatching emptyList()

        items.mapNotNull { element ->
            element.jsonObject.toMovie()
        }
    }.getOrDefault(emptyList())

    private fun String.replaceHostOnly(newHost: String): String {
        if (isBlank()) return this
        return try {
            val uri = URI(this)
            val scheme = uri.scheme ?: "https"
            val port = if (uri.port == -1) "" else ":${uri.port}"
            val query = uri.rawQuery?.let { "?$it" }.orEmpty()
            val fragment = uri.rawFragment?.let { "#$it" }.orEmpty()
            "$scheme://$newHost$port${uri.rawPath}$query$fragment"
        } catch (e: Exception) {
            this
        }
    }

    private fun JsonObject.toMovie(): Movie? = runCatching {
        val rawCover = this["pic"]?.jsonObject?.get("normal")?.jsonPrimitive?.content.orEmpty()
        val rawCoverLarge = this["pic"]?.jsonObject?.get("large")?.jsonPrimitive?.content.orEmpty()

        Movie(
            id = this["id"]!!.jsonPrimitive.content,
            title = this["title"]!!.jsonPrimitive.content,
            subtitle = this["card_subtitle"]?.jsonPrimitive?.content.orEmpty(),
            cover = rawCover.takeIf { it.isNotBlank() }?.replaceHostOnly("img1.doubanio.com")
                .orEmpty(),
            coverLarge = rawCoverLarge.takeIf { it.isNotBlank() }
                ?.replaceHostOnly("img1.doubanio.com").orEmpty(),
            rating = this["rating"]?.jsonObject?.get("value")?.jsonPrimitive?.content.orEmpty(),
        )
    }.getOrNull()
}