package com.prashant.material3_compose_template.network

import com.google.gson.JsonElement
import retrofit2.Response

interface ApiProcessor {
    suspend fun sendRequest(retrofitApi: RetrofitApi):Response<JsonElement>
}