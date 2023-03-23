package com.prashant.material3_compose_template.activity

import androidx.lifecycle.ViewModel
import com.prashant.material3_compose_template.datastore.DataStoreUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    val dataStoreUtil: DataStoreUtil
) : ViewModel() {

}