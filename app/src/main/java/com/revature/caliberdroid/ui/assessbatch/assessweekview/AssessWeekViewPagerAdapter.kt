package com.revature.caliberdroid.ui.assessbatch.assessweekview

import android.net.wifi.rtt.CivicLocationKeys.BUILDING
import android.net.wifi.rtt.CivicLocationKeys.STATE
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class AssessWeekViewPagerAdapter(val fm: FragmentManager, val behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> AssessWeekOverviewFragment()
            1 -> AssessWeekOverviewFragment()
            else -> AssessWeekOverviewFragment()
        }
    }

    override fun getCount(): Int = 2
}