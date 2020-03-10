package com.revature.caliberdroid.ui.assessbatch.assessweekview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.revature.caliberdroid.ui.assessbatch.assessweekview.overview.AssessWeekOverviewFragment
import com.revature.caliberdroid.ui.assessbatch.assessweekview.trainees.AssessBatchTraineesFragment

class AssessWeekViewPagerAdapter(val fm: FragmentManager, val behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> AssessWeekOverviewFragment()
            else -> AssessBatchTraineesFragment()
        }
    }

    override fun getCount(): Int = 2
}