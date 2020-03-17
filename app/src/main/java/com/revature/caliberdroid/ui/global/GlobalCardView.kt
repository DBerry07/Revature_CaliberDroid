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
    companion object {
        private val DEFAULT_EXPAND_ICON = R.drawable.ic_expand_arrow
        private val DEFAULT_EXPANDABLE = false
    }

    private var imgResourceExpandIcon = DEFAULT_EXPAND_ICON
    private var expandable = DEFAULT_EXPANDABLE

    private var attrs: AttributeSet? = null

    private lateinit var cardViewContainer:View
    private lateinit var defaultVisibleRows: LinearLayout
    private lateinit var expandableView: LinearLayout
    private lateinit var imgExpandIcon: ImageView
    private lateinit var tvCardHeaderLabel:TextView
    private lateinit var tvCardHeaderText:TextView

    private var contentLabelAndTextMap: Map<String,String> = HashMap()
    private var clickableIconsAndListenersMap: Map<Int, View.OnClickListener> = HashMap()

    //For creating this view programatically
    constructor(context: Context): super(context){

    }

    //For creating this view in an XML file
    constructor(context: Context, _attrs: AttributeSet) : super(context, _attrs) {
        attrs = _attrs
        setupAttributes(attrs)
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

    private fun setupAttributes(attrs: AttributeSet?) {

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.GlobalCardView,
            0, 0
        )
        expandable = typedArray.getBoolean(
            R.styleable.GlobalCardView_expandable,
            DEFAULT_EXPANDABLE
        )
        imgResourceExpandIcon = typedArray.getResourceId(
            R.styleable.GlobalCardView_imgResourceExpandIcon,
            DEFAULT_EXPAND_ICON
        )

        val cardHeaderLabel:TextView = findViewById(R.id.tvCardHeaderLabel)
        cardHeaderLabel.text = typedArray.getText(R.styleable.GlobalCardView_cardHeaderLabel)

        val cardHeaderText:TextView = findViewById(R.id.tvCardHeaderText)
        cardHeaderText.text = typedArray.getText(R.styleable.GlobalCardView_cardHeaderText)

        typedArray.recycle()
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

    private fun setRightAlignedIcons(_clickableIconsAndListenersMap: Map<Int, View.OnClickListener>){
        for((key,value) in _clickableIconsAndListenersMap){

        }
    }

    private fun setRows(_contentLabelAndTextMap: Map<String,String>){
        for((key,value) in _contentLabelAndTextMap){

        }
    }


}