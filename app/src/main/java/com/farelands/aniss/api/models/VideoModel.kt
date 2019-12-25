package com.farelands.aniss.api.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class VideoModel(
    @Expose
    @SerializedName("apiKey")
    val apiKey: String? = null,

    @Expose
    @SerializedName("sessionId")
    val sessionId: String? = null,

    @Expose
    @SerializedName("token")
    val token: String? = null
) : Parcelable

