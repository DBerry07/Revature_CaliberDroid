package com.revature.caliberdroid.adapter.locations.listeners

import android.widget.ImageView
import com.revature.caliberdroid.data.model.Location

interface EditLocationStatusInterface {
    fun editLocationStatus(location: Location, statusImageView: ImageView)
}