package com.prashant.material3_compose_template.network

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.*
interface RetrofitApi {

    @GET(LOGIN_API)
    suspend fun login(
        @Query("EmailID") userId: String,
        @Query("Password") password: String
    ): Response<JsonElement>

}