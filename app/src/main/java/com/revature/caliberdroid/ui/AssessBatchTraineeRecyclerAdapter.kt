package com.revature.caliberdroid.ui

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.Trainee

class AssessBatchTraineeRecyclerAdapter() : SortedListAdapter<SortedListAdapter.ViewModel>() {
    private var items : List<Trainee> = ArrayList()

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<out SortedListAdapter.ViewModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}