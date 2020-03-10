package com.revature.revaturetraineemanagment

import android.view.*
import android.view.LayoutInflater
import android.widget.*
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.ItemTraineeBinding


class TraineeAdapter(data : List<Trainee>): RecyclerView.Adapter<TraineeAdapter.MyViewHolder>() {

    var trainees = data
    lateinit var parent: ViewGroup
    lateinit var pop : PopupWindow

    private var _binding: ItemTraineeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_trainee, parent, false)
        _binding = ItemTraineeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        pop = PopupWindow(parent.context)

        this.parent = parent
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = trainees.get(position)
        holder.name.text = item.name
        holder.email.text = item.email
        holder.phone.text = item.phoneNumber
        holder.skype.text = item.skypeId
        holder.profile.text = item.profileUrl
        holder.college.text = item.college
        holder.major.text = item.major
        holder.recruiter.text = item.recruiterName
        holder.project.text = item.projectCompletion
        holder.screener.text = item.techScreenerName
        holder.status.text = item.trainingStatus

        val mDetectorCompat =
            GestureDetectorCompat(parent.context, MyGestureListener(holder, position, this))
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
            val pop = PopupWindow()
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
        return trainees.size
    }

    class MyViewHolder(binding: ItemTraineeBinding) : RecyclerView.ViewHolder(binding.root) {

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
                holder.isExpanded = !holder.isExpanded
                adapter.notifyItemChanged(position)
                holder.arrow.setImageResource(R.drawable.ic_collapse_arrow)
            }
            else if (holder.isExpanded) {
                holder.details.visibility = View.GONE
                holder.isExpanded = !holder.isExpanded
                adapter.notifyItemChanged(position)
                holder.arrow.setImageResource(R.drawable.ic_expand_arrow)
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
            val differY = e2!!.y - e1!!.y
            val differX = e2.x - e1.x

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
            if (!holder.isExpanded && holder.options.visibility == View.GONE) {
                holder.options.visibility = View.VISIBLE
            }
            else if (!holder.isExpanded && holder.options.visibility == View.VISIBLE){
                holder.options.visibility = View.GONE
            }


        }

        fun swipeRight(){
            //Toast.makeText(holder.itemView.context, "Swipe Right", Toast.LENGTH_LONG).show()
            /*holder.options.visibility = View.GONE
            holder.arrow.visibility = View.VISIBLE
            holder.status.visibility = View.VISIBLE*/
        }

        fun swipeLeft(){
            //Toast.makeText(holder.itemView.context, "Swipe LEFT", Toast.LENGTH_LONG).show()
            /*if (!holder.isExpanded) {
                holder.options.visibility = View.VISIBLE
                holder.arrow.visibility = View.GONE
                holder.status.visibility = View.GONE
            }*/
        }

    }

}