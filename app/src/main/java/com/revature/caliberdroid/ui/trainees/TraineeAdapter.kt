package com.revature.revaturetraineemanagment

import android.view.*
import android.view.LayoutInflater
import android.widget.*
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.api.APIHandler.context
import com.revature.caliberdroid.databinding.TraineeItemBinding
import com.revature.caliberdroid.ui.batches.BatchesInfoDirections
import com.revature.caliberdroid.ui.batches.ManageBatchFragmentDirections
import com.revature.caliberdroid.ui.trainees.TraineeFragmentDirections


class TraineeAdapter(data : ArrayList<HashMap<String, String>>): RecyclerView.Adapter<TraineeAdapter.MyViewHolder>() {

    var info = data
    lateinit var parent: ViewGroup
    lateinit var pop : PopupWindow

    private var _binding: TraineeItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.trainee_item, parent, false)
        _binding = TraineeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        pop = PopupWindow(parent.context)

        this.parent = parent
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = info.get(position)
        holder.name.setText(item.get("name"))
        holder.email.setText(item.get("email"))
        holder.phone.setText(item.get("phone"))
        holder.skype.setText(item.get("skype"))
        holder.profile.setText(item.get("profile"))
        holder.college.setText(item.get("college"))
        holder.major.setText(item.get("major"))
        holder.recruiter.setText(item.get("recruiter"))
        holder.project.setText(item.get("project"))
        holder.screener.setText(item.get("screener"))
        holder.status.setText(item.get("status"))

        var mDetectorCompat = GestureDetectorCompat(parent.context, MyGestureListener(holder, position, this))
        holder.itemView.setOnTouchListener { v, event ->
            //textView?.setText(event.toString())
            mDetectorCompat.onTouchEvent(event)
            true
        }

        holder.btnSwitch.setOnClickListener {
            val navController = Navigation.findNavController(parent)
            navController.navigate(R.id.action_traineeFragment_to_switchTraineeFragment)
        }

        holder.btnDelete.setOnClickListener {
            var pop = PopupWindow()
            pop.showAtLocation(parent, Gravity.BOTTOM, 10, 10)
        }

        /*holder.button.setOnClickListener (View.OnClickListener {
            if (!holder.isExpanded) {
                holder.details.visibility = View.VISIBLE
                holder.isExpanded = !holder.isExpanded;
                notifyItemChanged(position)
            }
            else {
                holder.details.visibility = View.GONE
                holder.isExpanded = !holder.isExpanded;
                notifyItemChanged(position)
            }
        })*/
    }

    override fun getItemCount(): Int {
        return info.size
    }

    class MyViewHolder(binding: TraineeItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var isExpanded = false

        var name : TextView
        var email : TextView
        var status : TextView
        var phone : TextView
        var skype : TextView
        var profile : TextView
        var college : TextView
        var major : TextView
        var recruiter : TextView
        var project : TextView
        var screener : TextView
        var details : LinearLayout
        var options : LinearLayout
        var arrow : ImageView
        var btnSwitch : Button
        var btnEdit : Button
        var btnDelete : Button

        init {
            this.name = binding.TMName
            this.email = binding.TMEmail
            this.status = binding.TMTvStatus
            this.phone = binding.TMPhone
            this.skype = binding.TMSkype
            this.profile = binding.TMProfile
            this.college = binding.TMCollege
            this.major = binding.TMMajor
            this.recruiter = binding.TMRecruiter
            this.project = binding.TMProject
            this.screener = binding.TMScreener
            this.arrow = binding.TMIvArrow
            this.details = binding.traineeDetails
            this.options = binding.TMOptions
            this.btnSwitch = binding.TMBtnSwitch
            this.btnDelete = binding.TMBtnDelete
            this.btnEdit = binding.TMBtnEdit
        }

    }

    class MyGestureListener(myHolder: MyViewHolder, myPosition: Int, myAdapter: TraineeAdapter): GestureDetector.OnGestureListener {

        val SWIPE_THRESHOLD = 0.5

        val holder = myHolder
        val position = myPosition
        val adapter = myAdapter

        override fun onShowPress(e: MotionEvent?) {
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            if (!holder.isExpanded && holder.options.visibility == View.GONE) {
                holder.details.visibility = View.VISIBLE
                holder.isExpanded = !holder.isExpanded;
                adapter.notifyItemChanged(position)
            }
            else if (holder.isExpanded) {
                holder.details.visibility = View.GONE
                holder.isExpanded = !holder.isExpanded;
                adapter.notifyItemChanged(position)
            }
            return true
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return false
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var differY = e2!!.getY() - e1!!.getY()
            var differX = e2!!.getX() - e1!!.getX()

            if (Math.abs(differX) > differY) {
                if (Math.abs(differX) > SWIPE_THRESHOLD) {
                    if (differX > 0) {
                        swipeRight()

                    } else {
                        swipeLeft()
                    }
                }
            }

            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return false
        }

        override fun onLongPress(e: MotionEvent?) {
        }

        fun swipeRight(){
            Toast.makeText(holder.itemView.context, "Swipe Right", Toast.LENGTH_LONG).show()
            holder.options.visibility = View.GONE
            holder.arrow.visibility = View.VISIBLE
            holder.status.visibility = View.VISIBLE
        }

        fun swipeLeft(){
            Toast.makeText(holder.itemView.context, "Swipe LEFT", Toast.LENGTH_LONG).show()
            if (!holder.isExpanded) {
                holder.options.visibility = View.VISIBLE
                holder.arrow.visibility = View.INVISIBLE
                holder.status.visibility = View.INVISIBLE
            }
        }

    }

}