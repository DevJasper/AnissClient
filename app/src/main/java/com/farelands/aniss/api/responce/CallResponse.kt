package com.farelands.aniss.api.responce

import android.os.Parcelable
import com.farelands.aniss.api.models.VideoModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CallResponse(
    @Expose
    @SerializedName("video")
    val video: VideoModel,

    @Expose
    @SerializedName("status")
    val status: Int? = null,

    @Expose
    @SerializedName("ref")
    val ref: String? = null
):Parcelable