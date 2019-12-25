package com.farelands.aniss.api.requests

import com.farelands.aniss.api.models.LocationModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CallRequest(
    @Expose
    @SerializedName("adminRole")
    var adminRole: Int = 0,
    @Expose
    @SerializedName("location")
    var Location: LocationModel
)