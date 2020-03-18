package com.revature.caliberdroid.ui.batchselection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.ItemBatchSelectionBinding
import timber.log.Timber
import java.util.*

class BatchSelectionAdapter(
    context: Context,
    comparator: Comparator<Batch>,
    private val onItemListener: OnItemClickListener
) : SortedListAdapter<Batch>(context, Batch::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BatchViewHolder {
        return BatchViewHolder(ItemBatchSelectionBinding.inflate(inflater, parent, false), onItemListener)
    }

    class BatchViewHolder(val binding: ItemBatchSelectionBinding, val onItemListener: OnItemClickListener)
        : SortedListAdapter.ViewHolder<Batch>(binding.root),
        View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun performBind(item: Batch) {
            binding.batch = item
        }

        override fun onClick(v: View) {
            Timber.d("onClick: $v")
            onItemListener.onBatchClick(currentItem)
        }

    }

    interface OnItemClickListener {
        fun onBatchClick(batchClicked: Batch)
    }

}