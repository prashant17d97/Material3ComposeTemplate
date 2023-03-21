package com.prashant.material3_compose_template.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashant.material3_compose_template.datastore.DataStoreUtil
import com.prashant.material3_compose_template.network.RetrofitApi
import com.prashant.material3_compose_template.preferencefile.PreferenceFile
import com.prashant.material3_compose_template.roomdb.DaoInterface
import com.prashant.material3_compose_template.roomdb.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val preferenceFile: PreferenceFile,
    private val dataStore: DataStoreUtil,
    private val retrofitApi: RetrofitApi,
    private val daoInterface: DaoInterface
) : ViewModel() {

    var data by mutableStateOf(listOf<User>())
    var user by mutableStateOf(User(id = 0, name = "", email = ""))
    var userByName by mutableStateOf(User(id = 0, name = "", email = ""))
    var userByNameList by mutableStateOf(listOf<User>())

    init {
        getAllUser()
    }

    fun saveUser(user: User) = viewModelScope.launch {
        daoInterface.insert(user)
        data = daoInterface.getAll()
    }

    fun deleteData(user: User) = viewModelScope.launch {
        daoInterface.delete(user)
        data = daoInterface.getAll()
    }

    private fun getAllUser() = viewModelScope.launch {
        data = daoInterface.getAll()
    }

    fun searchById(id: Long) = viewModelScope.launch {
        if (user.id != id) {
            val t = daoInterface.searchById(id)
            user = t ?: User(id = id, name = "", email = "")
            Log.e(TAG, "searchById: $t, $user")
        }
    }

    fun searchByString(name: String) = viewModelScope.launch {
        userByName = if (name.isNotEmpty()) {
            daoInterface.searchByCharacter(name.trim()) ?: User(name = "", email = "")
        } else {
            User(name = "", email = "")
        }
        Log.e(TAG, "searchByString: $userByName")

    }

    fun searchByStringList(name: String) = viewModelScope.launch {
        userByNameList = if (name.isNotEmpty()) {
            daoInterface.searchByCharacterList(name.trim())
        } else {
            listOf()
        }
        Log.e(TAG, "searchByStringList: $userByNameList")

    }

    companion object {
        const val TAG = "HomeVM"
    }
}