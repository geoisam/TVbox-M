package com.pjs.tvbox.util

import java.time.LocalDateTime
import com.nlf.calendar.Solar
import com.nlf.calendar.Lunar
import java.util.*

object LunarUtil {
    private var now = LocalDateTime.now()
    private var year = now.year
    private var month = now.monthValue
    private var day = now.dayOfMonth
    private var hour = now.hour
    private var minute = now.minute
    private var second = now.second
    private var yearSolar = year
    private var monthSolar = month
    private var daySolar = day
    private var weekSolar = Solar.fromYmdHms(year,month,day,hour,minute,second).weekInChinese
    private var timeShiChen = Lunar.fromYmdHms(year,month,day,hour,minute,second).timeZhi
    private var lunar = Lunar.fromDate(Date())
    private var monthLunar = lunar.monthInChinese
    private var dayLunar = lunar.dayInChinese
    private var yearGanZhi = lunar.yearInGanZhi
    private var monthGanZhi = lunar.monthInGanZhi
    private var dayGanZhi = lunar.dayInGanZhi
    private var yearZodiac = lunar.yearShengXiao
    private var monthZodiac =lunar.monthShengXiao
    private var dayZodiac =lunar.dayShengXiao

    fun getYearMonth(): String = "${yearSolar} 年 ${monthSolar} 月"

    fun getDay(): String = daySolar.toString()

    fun getWeek(): String = "星期${weekSolar}"

    fun getMonthDay(): String = "${monthLunar}月${dayLunar}"

    fun getGanZhi(): String = "${yearGanZhi}${yearZodiac}年 ${monthGanZhi}${monthZodiac}月 ${dayGanZhi}${dayZodiac}日"

    fun getShiChen(): String = "${timeShiChen}时"
}