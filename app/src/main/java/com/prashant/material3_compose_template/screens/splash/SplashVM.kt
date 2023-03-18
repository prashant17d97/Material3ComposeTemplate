package com.prashant.material3_compose_template.screens.splash

import androidx.lifecycle.ViewModel
import com.prashant.material3_compose_template.activity.MainActivity
import com.prashant.material3_compose_template.datastore.DataStoreUtil
import com.prashant.material3_compose_template.network.RetrofitApi
import com.prashant.material3_compose_template.preferencefile.PreferenceFile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val preferenceFile: PreferenceFile,
    private val dataStore: DataStoreUtil,
    private val retrofitApi: RetrofitApi
) : ViewModel() {

}