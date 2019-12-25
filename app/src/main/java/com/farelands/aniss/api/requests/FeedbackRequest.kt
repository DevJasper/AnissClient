package com.farelands.aniss.api.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FeedbackRequest(
    @Expose
    @SerializedName("email")
    var email: String = "",
    @Expose
    @SerializedName("message")
    var message: String = ""
)
