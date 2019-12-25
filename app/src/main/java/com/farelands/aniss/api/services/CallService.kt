package com.farelands.aniss.api.services

import com.farelands.aniss.api.requests.CallRequest
import com.farelands.aniss.api.responce.CallResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CallService {

    @POST("api/calls/VideoCall")
    fun call(@Body newCallRequest: CallRequest):Call<CallResponse>
}