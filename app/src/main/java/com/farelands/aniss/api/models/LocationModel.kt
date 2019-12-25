package com.farelands.aniss.api.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties
data class LocationModel(

    @Expose
    @SerializedName("latitude")
    val Latitude: String? = null,


    @Expose
    @SerializedName("longitude")
    val Longitude: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "Latitude" to Latitude,
            "Longitude" to Longitude
        )
    }
}