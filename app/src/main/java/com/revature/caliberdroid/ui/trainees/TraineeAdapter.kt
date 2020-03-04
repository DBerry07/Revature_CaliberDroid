package com.revature.revaturetraineemanagment

import android.view.*
import android.widget.*
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R

class TraineeAdapter(data : ArrayList<HashMap<String, String>>): RecyclerView.Adapter<TraineeAdapter.MyViewHolder>() {

    var info = data
    lateinit var parent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v : View = LayoutInflater.from(parent.context).inflate(R.layout.trainee_item, parent, false)
        //var viewHolder = MyViewHolder(v)
        this.parent = parent
        return MyViewHolder(v)
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

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {

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

        init {
            this.name = v.findViewById(R.id.TM_name)
            this.email = v.findViewById(R.id.TM_email)
            this.status = v.findViewById(R.id.TM_tv_status)
            this.phone = v.findViewById(R.id.TM_phone)
            this.skype = v.findViewById(R.id.TM_skype)
            this.profile = v.findViewById(R.id.TM_profile)
            this.college = v.findViewById(R.id.TM_college)
            this.major = v.findViewById(R.id.TM_major)
            this.recruiter = v.findViewById(R.id.TM_recruiter)
            this.project = v.findViewById(R.id.TM_project)
            this.screener = v.findViewById(R.id.TM_screener)
            this.arrow = v.findViewById(R.id.TM_iv_arrow)
            this.details = v.findViewById(R.id.expand)
            this.options = v.findViewById(R.id.TM_options)
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