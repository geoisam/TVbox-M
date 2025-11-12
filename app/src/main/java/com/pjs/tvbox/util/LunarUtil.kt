package com.pjs.tvbox.util

import com.nlf.calendar.Solar
import com.nlf.calendar.Lunar
import java.time.ZonedDateTime
import java.time.ZoneId
import java.util.*

object LunarUtil {
    fun getYearMonth(): String {
        val timeNow = ZonedDateTime.now(ZoneId.systemDefault())
        val yearSolar = timeNow.year
        val monthSolar = timeNow.monthValue
        return "${yearSolar} 年 ${monthSolar} 月"
    }

    fun getDay(): String {
        val timeNow = ZonedDateTime.now(ZoneId.systemDefault())
        return timeNow.dayOfMonth.toString()
    }

    fun getWeek(): String {
        val timeNow = ZonedDateTime.now(ZoneId.systemDefault())
        val solar = Solar.fromYmdHms(
            timeNow.year,
            timeNow.monthValue,
            timeNow.dayOfMonth,
            timeNow.hour,
            timeNow.minute,
            timeNow.second
        )
        return "星期${solar.weekInChinese}"
    }

    fun getMonthDay(): String {
        val timeNow = ZonedDateTime.now(ZoneId.systemDefault())
        val lunar = Lunar.fromDate(Date.from(timeNow.toInstant()))
        return "${lunar.monthInChinese}月${lunar.dayInChinese}"
    }

    fun getGanZhi(): String {
        val timeNow = ZonedDateTime.now(ZoneId.systemDefault())
        val lunar = Lunar.fromDate(Date.from(timeNow.toInstant()))
        return "${lunar.yearInGanZhi}${lunar.yearShengXiao}年 ${lunar.monthInGanZhi}${lunar.monthShengXiao}月 ${lunar.dayInGanZhi}${lunar.dayShengXiao}日"
    }

    fun getShiChen(): String {
        val timeNow = ZonedDateTime.now(ZoneId.systemDefault())
        val lunar = Lunar.fromYmdHms(
            timeNow.year,
            timeNow.monthValue,
            timeNow.dayOfMonth,
            timeNow.hour,
            timeNow.minute,
            timeNow.second
        )
        return "${lunar.timeZhi}时"
    }
}