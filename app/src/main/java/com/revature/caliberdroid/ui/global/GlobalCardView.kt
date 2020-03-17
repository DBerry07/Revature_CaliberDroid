package com.revature.caliberdroid.ui.global

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.revature.caliberdroid.R
import timber.log.Timber

class GlobalCardView : LinearLayout {
    companion object {
        private val DEFAULT_MAX_VISIBLE_LINE = 3
        private val DEFAULT_EXPAND_ICON = R.drawable.ic_expand_arrow
        private val DEFAULT_EXPANDABLE = false
    }

    private var maxVisibleLines = DEFAULT_MAX_VISIBLE_LINE
    private var imgResourceExpandIcon = DEFAULT_EXPAND_ICON
    private var expandable = DEFAULT_EXPANDABLE

    private var attrs: AttributeSet? = null

    //For creating this view programmatically
    constructor(context: Context) : super(context) {
    }

    //For creating this view in an XML file
    constructor(context: Context, _attrs: AttributeSet) : super(context, _attrs) {
        attrs = _attrs
        setupAttributes(attrs)
    }

    init {
        orientation = LinearLayout.VERTICAL
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.item_template_global_card,this,true)

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
            R.styleable.GlobalCardView_imgExpandableIcon,
            DEFAULT_EXPAND_ICON
        )
        val cardHeaderLabel:TextView = findViewById(R.id.cardHeaderLabel)
        cardHeaderLabel.text = typedArray.getText(R.styleable.GlobalCardView_cardHeaderLabel)

        val cardHeaderText:TextView = findViewById(R.id.cardHeaderText)
        cardHeaderText.text = typedArray.getText(R.styleable.GlobalCardView_cardHeaderText)

        typedArray.recycle()
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