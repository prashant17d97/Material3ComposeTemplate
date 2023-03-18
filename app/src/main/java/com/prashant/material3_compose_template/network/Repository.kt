package com.prashant.material3_compose_template.network

import android.util.Log
import com.prashant.material3_compose_template.MainActivity
import com.prashant.material3_compose_template.R
import com.prashant.material3_compose_template.commonfunctions.CommonFunctions.getStringResource
import com.prashant.material3_compose_template.commonfunctions.CommonFunctions.showToast
import com.prashant.material3_compose_template.network.NetworkExtensionFunction.hideProgress
import com.prashant.material3_compose_template.network.NetworkExtensionFunction.isNetworkAvailable
import com.prashant.material3_compose_template.network.NetworkExtensionFunction.jsonElementToData
import com.prashant.material3_compose_template.network.NetworkExtensionFunction.showProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import javax.inject.Inject

class Repository @Inject constructor(
    val retrofitApi: RetrofitApi
) {
    suspend inline fun <reified Generic> apiCall(
        retrofitCall: ApiProcessor,
        crossinline result: (result: Generic) -> Unit,
        crossinline responseMessage: (message: String, code: Int) -> Unit
    ) {
        try {
            if (!(MainActivity.weakReference.get() as MainActivity).isNetworkAvailable()) {
                getStringResource(R.string.your_device_offline).showToast()
                return
            } else {
                showProgress()
                val response = flow {
                    showProgress()
                    emit(retrofitCall.sendRequest(retrofitApi))
                }.flowOn(Dispatchers.IO)
                response.catch { exception ->
                    exception.printStackTrace()
                    hideProgress()
                    (exception.message ?: "").showToast()
                }.collect {
                    hideProgress()
                    when (it.code()) {
                        /**Some error occurred*/
                        in 100..199 -> {
                            responseMessage(it.message(), it.code())
                            Log.e(
                                TAG,
                                "login:---> Message:${it.message()} ResponseCode: ${it.code()}"
                            )
                        }

                        /**
                         * Successful
                         * */
                        200 -> {
                            Log.e(
                                TAG,
                                "login:---> Message:${it.message()} ResponseCode: ${it.code()}"
                            )
                            if (it.isSuccessful && it.body()?.isJsonNull == false) {
                                jsonElementToData<Generic>(it.body()) { resultData ->
                                    result(resultData)
                                }
                            }
                        }
                        in 300..399 -> {
                            responseMessage(it.message(), it.code())
                            getStringResource(R.string.someError).showToast()
                            Log.e(
                                TAG,
                                "login:---> Message:${it.message()} ResponseCode: ${it.code()}"
                            )
                        }

                        /**Unauthorized*/
                        401 -> {
                            responseMessage(it.message(), it.code())
                            Log.e(
                                TAG,
                                "login:---> Message:${it.message()} ResponseCode: ${it.code()}"
                            )
                        }

                        /**Page not found*/
                        404 -> {
                            responseMessage(it.message(), it.code())
                            Log.e(
                                TAG,
                                "login:---> Message:${it.message()} ResponseCode: ${it.code()}"
                            )
                        }

                        /**Server Error*/
                        in 501..509 -> {
                            responseMessage(it.message(), it.code())
                            Log.e(
                                TAG,
                                "login:---> Message:${it.message()} ResponseCode: ${it.code()}"
                            )
                        }

                        else -> {
                            /**ClientErrors*/
                            val res = it.errorBody()!!.string()
                            val jsonObject = JSONObject(res)
                            when {
                                jsonObject.has("message") -> {
                                    responseMessage(jsonObject.getString("message"), it.code())
                                    Log.e(
                                        TAG,
                                        "login:---> Message:${jsonObject.getString("message")} ResponseCode: ${it.code()}"
                                    )
                                }
                                jsonObject.has("error") -> {
                                    val message =
                                        jsonObject.getJSONObject("error").getString("text") ?: ""
                                    responseMessage(message, it.code())
                                    Log.e(
                                        TAG,
                                        "login:---> Message:${message} ResponseCode: ${it.code()}"
                                    )

                                    getStringResource(R.string.someError).showToast()
                                }
                                else -> {
                                    getStringResource(R.string.someError).showToast()
                                }
                            }
                        }
                    }
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, "call: ${exception.message}")
        }

    }

    companion object {
        const val TAG = "Repository"
    }
}