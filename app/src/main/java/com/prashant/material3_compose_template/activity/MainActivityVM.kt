package com.prashant.material3_compose_template.activity

import androidx.lifecycle.ViewModel
import com.prashant.material3_compose_template.datastore.DataStoreUtil
import com.prashant.material3_compose_template.datastore.THEME_KEY
import com.prashant.material3_compose_template.uiconfiguration.UIConfiguration
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    val dataStoreUtil: DataStoreUtil
) : ViewModel() {

    fun getUIConfiguration(): UIConfiguration? {
        var uiConfiguration: UIConfiguration? = null
        dataStoreUtil.retrieveObject(THEME_KEY, UIConfiguration::class.java) {
            if (it != null) {
                uiConfiguration = it
            }
        }
        return uiConfiguration
    }
}