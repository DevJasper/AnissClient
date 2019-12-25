package com.farelands.aniss.api.services


import com.farelands.aniss.api.requests.SecurityInfoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SecurityInfoService {
    @POST("api/SecurityInfo/GiveSecurityInfo")
    fun submitSecurityInfo(@Body securityInfoRequest: SecurityInfoRequest): Call<Void>
}