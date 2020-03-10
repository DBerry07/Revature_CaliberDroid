package com.revature.caliberdroid.ui.assessbatch.assessweekview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentAssessWeekBinding
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel


class AssessWeekFragment : Fragment() {

    private lateinit var assessWeekViewPagerAdapter : AssessWeekViewPagerAdapter
    private val assessWeekViewModel: AssessWeekViewModel by activityViewModels()
    private var _assessWeekBinding: FragmentAssessWeekBinding? = null
    private val assessWeekBinding get() = _assessWeekBinding!!

    companion object {
        fun newInstance() = AssessWeekFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _assessWeekBinding = FragmentAssessWeekBinding.inflate(inflater)

        assessWeekViewModel.loadTrainees()

        val viewPager = assessWeekBinding.viewpagerWeekview
        assessWeekViewPagerAdapter = AssessWeekViewPagerAdapter(requireActivity().supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = assessWeekViewPagerAdapter

        val tabLayout = assessWeekBinding.tabsWeekview
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.text = requireContext().getString(R.string.title_assessments)
        tabLayout.getTabAt(1)!!.text = requireContext().getString(R.string.title_trainees)

        return assessWeekBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _assessWeekBinding = null
    }

}
