package com.revature.caliberdroid.ui.batches

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.data.api.APIHandler.context
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.databinding.BatchRowBinding
import timber.log.Timber
import java.util.*

class BatchAdapter(
    context: Context,
    comparator: Comparator<Batch>,
    private val onItemListener: OnItemClickListener
) : SortedListAdapter<Batch>(context, Batch::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BatchViewHolder {
        return BatchViewHolder(BatchRowBinding.inflate(inflater, parent, false), onItemListener)
    }

    class BatchViewHolder(val binding: BatchRowBinding, private val onItemListener: OnItemClickListener)
        : SortedListAdapter.ViewHolder<Batch>(binding.root),
        View.OnClickListener {

        init {
            binding.constraintLayoutBatchRow.setOnClickListener(this)
            binding.btnDeleteBatch.setOnClickListener{ this.removeBatch() }
            binding.btnEditBatch.setOnClickListener{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.editBatch()
                }
            }
            binding.btnViewAssociates.setOnClickListener{ viewAssociates(this.itemView) }
        }

        private fun editBatch() {
            onItemListener.onBatchClick(currentItem, 1)
        }

        private fun removeBatch() {
                val builder = AlertDialog.Builder(context)
                builder.setCancelable(true)
                builder.setTitle("Are you sure you want to delete?")
                builder.setPositiveButton("NO") { _: DialogInterface?, _: Int -> }
                builder.setNegativeButton("Yes") { _: DialogInterface?, _: Int ->
                    Snackbar.make(this.itemView,"Batch Deleted Successfully!", Snackbar.LENGTH_SHORT).show()
                    BatchRepository.deleteBatch(this.currentItem)
                }

                val dialog = builder.create()
                dialog.show()
        }

        override fun performBind(item: Batch) {
            binding.batchModel = item
        }

        override fun onClick(v: View) {
            Timber.d("onClick: $v")
            expandCard()
        }

        private fun viewAssociates(v: View) {
            onItemListener.onBatchClick(currentItem, 2)
        }

        private fun expandCard() {

            if( binding.constraintlayoutBatchrowExpandable.isVisible ){
                binding.constraintlayoutBatchrowExpandable.visibility = View.GONE
                binding.batchrowArrow.setImageResource(com.revature.caliberdroid.R.drawable.ic_expand_arrow)
            } else {
                binding.constraintlayoutBatchrowExpandable.visibility = View.VISIBLE
                binding.batchrowArrow.setImageResource(com.revature.caliberdroid.R.drawable.ic_collapse_arrow)
            }
        }

    }

    interface OnItemClickListener {
        fun onBatchClick(batchClicked: Batch, path: Int)
    }

}