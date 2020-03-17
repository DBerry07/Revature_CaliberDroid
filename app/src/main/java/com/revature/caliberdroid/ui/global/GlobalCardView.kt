package com.revature.caliberdroid.ui.global

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.revature.caliberdroid.R
import timber.log.Timber

class GlobalCardView : LinearLayout {

    private  var cardViewContainer:View
    private  var defaultVisibleRows: LinearLayout
    private var expandableView: LinearLayout
    private var imgExpandIcon: ImageView
    private var tvCardHeaderLabel:TextView
    private var tvCardHeaderText:TextView
    
    constructor(context: Context, _attrs: AttributeSet) : super(context, _attrs) {
    }

    init {
        orientation = LinearLayout.VERTICAL
        LayoutInflater.from(context).inflate(R.layout.item_template_global_card,this,true)
        defaultVisibleRows = findViewById(R.id.visibleRows)
        expandableView = findViewById(R.id.expandableRows)
        imgExpandIcon = findViewById(R.id.imgExpandIcon)

        tvCardHeaderLabel = findViewById(R.id.tvCardHeaderLabel)
        tvCardHeaderText = findViewById(R.id.tvCardHeaderText)

        cardViewContainer = findViewById(R.id.cardViewContainer)
        cardViewContainer.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                expandCard()
            }
        })
    }

    private fun expandCard() {
        if( expandableView.isVisible ){
            Timber.i("Is Visible")
            expandableView.visibility = View.GONE
            imgExpandIcon.setImageResource(com.revature.caliberdroid.R.drawable.ic_expand_arrow)
        } else {
            Timber.i("Not Visible")
            expandableView.visibility = View.VISIBLE
            imgExpandIcon.setImageResource(com.revature.caliberdroid.R.drawable.ic_collapse_arrow)
        }
    }
}