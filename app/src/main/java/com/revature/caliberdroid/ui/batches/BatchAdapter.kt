package com.revature.caliberdroid.ui.batches

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.opengl.Visibility
import android.os.Build
import android.text.AlteredCharSequence.make
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.contentValuesOf
import androidx.core.view.isVisible
import androidx.navigation.Navigation.findNavController
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.data.api.APIHandler.context
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.databinding.BatchRowBinding
import kotlinx.coroutines.withContext
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
            binding.btnEditBatch.setOnClickListener{ if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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

            if(binding.tvSubItemBatchRowLocationValue.isVisible){
                binding.tvSubItemBatchRowLocationValue.visibility = View.GONE
                binding.tvBatchRowLocationHeader.visibility = View.GONE
                binding.tvBatchrowGoodgrade.visibility = View.GONE
                binding.tvBatchrowGoodgradeValue.visibility = View.GONE
                binding.tvBatchrowPassinggrade.visibility = View.GONE
                binding.tvBatchrowPassinggradeValue.visibility = View.GONE
                binding.tvBatchrowCotrainer.visibility = View.GONE
                binding.tvBatchrowCotrainerValue.visibility = View.GONE
                binding.tvBatchRowTrainingtypeValue.visibility = View.GONE
                binding.tvBatchRowTrainingtypeHeader.visibility = View.GONE
            }

            else{
                binding.tvSubItemBatchRowLocationValue.visibility = View.VISIBLE
                binding.tvBatchRowLocationHeader.visibility = View.VISIBLE
                binding.tvBatchrowGoodgrade.visibility = View.VISIBLE
                binding.tvBatchrowGoodgradeValue.visibility = View.VISIBLE
                binding.tvBatchrowPassinggrade.visibility = View.VISIBLE
                binding.tvBatchrowPassinggradeValue.visibility = View.VISIBLE
                binding.tvBatchrowCotrainer.visibility = View.VISIBLE
                binding.tvBatchrowCotrainerValue.visibility = View.VISIBLE
                binding.tvBatchRowTrainingtypeValue.visibility = View.VISIBLE
                binding.tvBatchRowTrainingtypeHeader.visibility = View.VISIBLE

            }
        }

    }

    interface OnItemClickListener {
        fun onBatchClick(batchClicked: Batch, path: Int)
    }

}