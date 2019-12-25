package com.farelands.aniss.api.services


import com.farelands.aniss.api.requests.FeedbackRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackService {

    @POST("api/Feedback/GiveFeedback")
    fun submitFeedback(@Body feedbackRequest: FeedbackRequest): Call<Void>
}