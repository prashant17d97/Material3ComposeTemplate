package com.prashant.material3_compose_template.screens.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.prashant.material3_compose_template.activity.MainActivity.Companion.mainActivity
import com.prashant.material3_compose_template.navigation.Screens
import com.prashant.material3_compose_template.uimaterials.UIMaterials.Companion.ui

const val TAG = "HomeCompose"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Home(
    navHostController: NavHostController, homeVM: HomeVM = hiltViewModel()
) {
    var id by remember {
        mutableStateOf("")
    }
    var dismiss by remember {
        mutableStateOf(false)
    }

    val lifeCycleOwner = LocalLifecycleOwner.current

    mainActivity?.currentScreenClick?.observe(lifeCycleOwner) { currentCall ->
        when (currentCall) {
            Screens.Home.route -> {
                dismiss = true
            }
        }
    }

    lifeCycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            Log.e(TAG, "onDestroy: ")
            homeVM.clearData()
            super.onDestroy(owner)
        }

        override fun onResume(owner: LifecycleOwner) {
            homeVM.getAllUser()
            Log.e(TAG, "onResume: ")
            super.onResume(owner)
        }
    })

    if (dismiss) {
        Dialog(
            onDismissRequest = { dismiss = false }, properties = DialogProperties(
                dismissOnBackPress = true, dismissOnClickOutside = true,

                )
        ) {
            ui.AddUserPopUp(onDismiss = {
                dismiss = it
                Log.e(TAG, "Home: $it")
            }) {
                homeVM.saveUser(it)
                dismiss = false
                Log.e(TAG, "Home: add")
            }
        }
    }
    val focusManager = LocalFocusManager.current
    val keyBoardManager = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = id, onValueChange = {
                id = it
                homeVM.searchByStringList(id)
            }, placeholder = {
                Text(text = "Search", style = MaterialTheme.typography.labelSmall)
            }, trailingIcon = {
                Icon(imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    modifier = Modifier.clickable { homeVM.searchByStringList(id) })
            }, maxLines = 1, singleLine = true, keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search, keyboardType = KeyboardType.Text
            ), keyboardActions = KeyboardActions(onSearch = {
                homeVM.searchByStringList(id)
                focusManager.clearFocus()
                keyBoardManager?.hide()
            })
            )
        }

        AnimatedVisibility(visible = homeVM.userByNameList.isNotEmpty()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Search Results", style = MaterialTheme.typography.bodyLarge
                )
                homeVM.userByNameList.forEach { user ->
                    ui.UserCard(user = user,
                        showDeleteIcon = false,
                        delete = { homeVM.deleteData(user) })
                }
            }


        }
        Text(
            text = "Added cards", style = MaterialTheme.typography.bodyLarge
        )

        homeVM.data.forEach { user ->
            ui.UserCard(user = user, delete = { homeVM.deleteData(user) })
        }
    }
}