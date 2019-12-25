package com.farelands.aniss.api.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SecurityInfoRequest(
    @Expose
    @SerializedName("securityInfoCategory")
    var securityInfoCategory: Int,
    @Expose
    @SerializedName("fullName")
    var fullName: String = "",

    @Expose
    @SerializedName("email")
    var email: String = "",

    @Expose
    @SerializedName("phoneNumber")
    var phoneNumber: String = "",

    @Expose
    @SerializedName("area")
    var area: String = "",

    @Expose
    @SerializedName("town")
    var town: String = "",

    @Expose
    @SerializedName("address")
    var address: String = "",

    @Expose
    @SerializedName("thoseInvolved")
    var thoseInvolved: String = "",

    @Expose
    @SerializedName("description")
    var description: String = ""

)