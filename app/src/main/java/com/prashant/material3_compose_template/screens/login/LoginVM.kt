package com.prashant.material3_compose_template.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.gson.JsonElement
import com.prashant.material3_compose_template.R
import com.prashant.material3_compose_template.commonfunctions.CommonFunctions.getStringResource
import com.prashant.material3_compose_template.commonfunctions.CommonFunctions.showToast
import com.prashant.material3_compose_template.datastore.DataStoreUtil
import com.prashant.material3_compose_template.navigation.Screens
import com.prashant.material3_compose_template.network.ApiProcessor
import com.prashant.material3_compose_template.network.Repository
import com.prashant.material3_compose_template.network.RetrofitApi
import com.prashant.material3_compose_template.preferencefile.PreferenceFile
import com.prashant.material3_compose_template.screens.login.data.LoginData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val preferenceFile: PreferenceFile,
    private val dataStore: DataStoreUtil,
    private val repository: Repository,
) : ViewModel() {

    private val TAG: String = this@LoginVM.javaClass.simpleName
    fun login(navHostController: NavHostController) = viewModelScope.launch {
        repository.apiCall<LoginData>(retrofitCall = object : ApiProcessor {
            override suspend fun sendRequest(retrofitApi: RetrofitApi): Response<JsonElement> {
                return retrofitApi.login("userId", "password")
            }
        }, result = { result ->
            Log.e(TAG, "login: $result")
            navHostController.navigate(Screens.Home.route) {
                popUpTo(Screens.Login.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }, responseMessage = { message, code ->
            Log.e(this@LoginVM.javaClass.simpleName, "login: $message $code")
            message.takeIf { it.isNotEmpty() }
                ?: "$code ${getStringResource(R.string.someError)}".showToast()
        })
    }

}


