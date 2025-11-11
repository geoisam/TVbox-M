package com.pjs.tvbox.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*
import com.pjs.tvbox.R
import com.pjs.tvbox.util.LunarUtil

class MineFragment : Fragment() {
    private lateinit var mineDateMonth: TextView
    private lateinit var mineDateHour: TextView
    private lateinit var mineDateWeek: TextView
    private lateinit var mineDateDay: TextView
    private lateinit var mineDateLunar: TextView
    private lateinit var mineDateYear: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.mine_page, container, false)

        mineDateMonth = view.findViewById(R.id.mine_date_month)
        mineDateHour = view.findViewById(R.id.mine_date_hour)
        mineDateWeek = view.findViewById(R.id.mine_date_week)
        mineDateDay = view.findViewById(R.id.mine_date_day)
        mineDateLunar = view.findViewById(R.id.mine_date_lunar)
        mineDateYear = view.findViewById(R.id.mine_date_year)

        updateUI()
        return view
    }

    private fun updateUI() {
        mineDateMonth.text = LunarUtil.getYearMonth()
        mineDateHour.text = LunarUtil.getShiChen()
        mineDateWeek.text = LunarUtil.getWeek()
        mineDateDay.text = LunarUtil.getDay()
        mineDateLunar.text = LunarUtil.getMonthDay()
        mineDateYear.text = LunarUtil.getGanZhi()
    }

}