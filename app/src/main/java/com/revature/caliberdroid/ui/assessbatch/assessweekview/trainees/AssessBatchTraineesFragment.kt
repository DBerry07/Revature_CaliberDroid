package com.revature.caliberdroid.ui.assessbatch.assessweekview.trainees

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.FragmentAssessBatchTraineesBinding
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel
import com.revature.caliberdroid.ui.assessbatch.assessweekview.trainees.TraineeAssessmentsRecycleAdapter
import com.revature.caliberdroid.util.KeyboardUtil
import timber.log.Timber
import java.lang.Character.toLowerCase
import java.util.*

class AssessBatchTraineesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val assessWeekViewModel: AssessWeekViewModel by activityViewModels()

    private var _binding: FragmentAssessBatchTraineesBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAssessBatchTraineesBinding.inflate(layoutInflater,container,false)

        // set up search bar
        setHasOptionsMenu(true)

        binding.assessWeekModel=assessWeekViewModel

        binding.root.isFocusable = true
        binding.root.isFocusableInTouchMode = true
        binding.root.setOnClickListener {
            Timber.d("clicking on away")
            KeyboardUtil.hideSoftKeyboard(requireContext(),it)
            it.requestFocus()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflator: MenuInflater) {
        Timber.d("Menu Inflater")
        inflator.inflate(R.menu.search_bar, menu)

        val searchView: SearchView = menu.findItem(R.id.search_bar).actionView as SearchView
        searchView.setOnQueryTextListener(this)

    }

    fun initRecyclerView() {
        var mAdapter = AssessBatchTraineeRecyclerAdapter(this.context,ALPHABETICAL_COMPARATOR_TRAINEES,assessWeekViewModel)
        var linearLayoutManager:RecyclerView.LayoutManager = LinearLayoutManager(this.context)
        binding.recycleAssessBatchTrainees.layoutManager = linearLayoutManager
        binding.recycleAssessBatchTrainees.adapter = mAdapter

        //have the recycler view observe the trainees
        assessWeekViewModel.trainees.observe(viewLifecycleOwner, Observer {
            (binding.recycleAssessBatchTrainees.adapter as AssessBatchTraineeRecyclerAdapter).edit()
                .replaceAll(it)
                .commit()
        })

        //have the recycler view observe the trainee notes
        assessWeekViewModel.assessWeekNotes.traineeNotes.observe(viewLifecycleOwner, Observer {
            (binding.recycleAssessBatchTrainees.adapter as AssessBatchTraineeRecyclerAdapter).notifyDataSetChanged()
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // filter based on Batch Names and Skill Types
        assessWeekViewModel.trainees.observe(viewLifecycleOwner, Observer {
            (binding.recycleAssessBatchTrainees.adapter as AssessBatchTraineeRecyclerAdapter).edit()
                .removeAll()
                .commit()
            for(i in it){
                if(
                    i.name!!.toLowerCase(Locale.ROOT).contains(newText!!.toString(),true))
                {
                    (binding.recycleAssessBatchTrainees.adapter as AssessBatchTraineeRecyclerAdapter).edit().add(i).commit()
                }
          }
      })
      return false
  }


override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmField val ALPHABETICAL_COMPARATOR_TRAINEES: Comparator<Trainee> =
            Comparator<Trainee> { a: Trainee, b: Trainee -> a.name!!.compareTo(b.name!!)}
    }

}
