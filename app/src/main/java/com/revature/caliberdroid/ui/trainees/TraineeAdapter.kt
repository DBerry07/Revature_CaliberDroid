package com.revature.revaturetraineemanagment

import android.os.Handler
import android.view.*
import android.view.LayoutInflater
import android.widget.*
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.ItemTraineeBinding
import com.revature.caliberdroid.ui.trainees.TraineeFragmentDirections


class TraineeAdapter(data : List<Trainee>, batchID : Long): RecyclerView.Adapter<TraineeAdapter.MyViewHolder>() {

    var trainees = data
    val batchId = batchID
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
        holder.name.text = ifNull(item.name)
        holder.email.text = ifNull(item.email)
        holder.phone.text = ifNull(item.phoneNumber)
        holder.skype.text = ifNull(item.skypeId)
        holder.profile.text = ifNull(item.profileUrl)
        holder.college.text = ifNull(item.college)
        holder.major.text = ifNull(item.major)
        holder.recruiter.text = ifNull(item.recruiterName)
        holder.project.text = ifNull(item.projectCompletion)
        holder.screener.text = ifNull(item.techScreenerName)
        holder.status.text = ifNull(item.trainingStatus)
        holder.degree.text = ifNull(item.degree)
        holder.score.text = ifNull(item.techScreenScore.toString())

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

        holder.btnEdit.setOnClickListener {
            val navControler = Navigation.findNavController(parent)
            navControler.navigate(TraineeFragmentDirections.actionTraineeFragmentToEditTraineeFragment(item, batchId = batchId))
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

    fun ifNull(string: String?) : String{
        if (string.equals("null") || string == null){
            return ""
        }
        else {
            return string
        }
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
        var degree: TextView
        var score: TextView

        var details : LinearLayout
        var buffer: LinearLayout
        var row1: LinearLayout
        var row2: LinearLayout
        var row3: LinearLayout
        var row4: LinearLayout
        var row5: LinearLayout

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
            this.degree = binding.TMDegree
            this.score = binding.TMScore
            this.arrow = binding.TMIvArrow
            this.details = binding.traineeDetails
            this.buffer = binding.traineeBuffer
            this.row1 = binding.traineeDetailsRow1
            this.row2 = binding.traineeDetailsRow2
            this.row3 = binding.traineeDetailsRow3
            this.row4 = binding.traineeDetailsRow4
            this.row5 = binding.traineeDetailsRow5
            this.options = binding.TMOptions
            this.btnSwitch = binding.TMBtnSwitch
            this.btnDelete = binding.TMBtnDelete
            this.btnEdit = binding.TMBtnEdit
        }

    }

    class MyGestureListener(myHolder: MyViewHolder, myPosition: Int, myAdapter: TraineeAdapter): GestureDetector.OnGestureListener {

        val SWIPE_THRESHOLD = 0.5
        val LOAD_DELAY : Long = 25

        val holder = myHolder
        val position = myPosition
        val adapter = myAdapter

        override fun onShowPress(e: MotionEvent?) {
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            if (!holder.isExpanded && holder.options.visibility == View.GONE) {
                holder.details.visibility = View.VISIBLE
                holder.buffer.visibility = View.VISIBLE
                Handler().postDelayed( {
                    holder.row1.visibility=View.VISIBLE
                }, LOAD_DELAY * 1)
                Handler().postDelayed( {
                    holder.row2.visibility=View.VISIBLE
                }, LOAD_DELAY * 2)
                Handler().postDelayed( {
                    holder.row3.visibility=View.VISIBLE
                }, LOAD_DELAY * 3)
                Handler().postDelayed( {
                    holder.row4.visibility=View.VISIBLE
                }, LOAD_DELAY * 4)
                Handler().postDelayed( {
                    holder.row5.visibility=View.VISIBLE
                }, LOAD_DELAY * 5)

                holder.isExpanded = !holder.isExpanded
                adapter.notifyItemChanged(position)
                holder.arrow.setImageResource(R.drawable.ic_collapse_arrow)
            }
            else if (holder.isExpanded) {
                Handler().postDelayed( {
                    holder.row5.visibility=View.GONE
                }, LOAD_DELAY * 1)
                Handler().postDelayed( {
                    holder.row4.visibility=View.GONE
                }, LOAD_DELAY * 2)
                Handler().postDelayed( {
                    holder.row3.visibility=View.GONE
                }, LOAD_DELAY * 3)
                Handler().postDelayed( {
                    holder.row2.visibility=View.GONE
                }, LOAD_DELAY * 4)
                Handler().postDelayed( {
                    holder.row1.visibility=View.GONE
                    holder.buffer.visibility=View.GONE
                    holder.details.visibility = View.GONE
                }, LOAD_DELAY * 5)

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