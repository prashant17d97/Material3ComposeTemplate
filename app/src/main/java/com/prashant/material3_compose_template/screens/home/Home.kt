package com.prashant.material3_compose_template.screens.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prashant.material3_compose_template.activity.MainActivity
import com.prashant.material3_compose_template.navigation.Screens
import com.prashant.material3_compose_template.screens.home.HomeVM.Companion.TAG
import com.prashant.material3_compose_template.uimaterials.UIMaterials.Companion.ui

@Composable
fun Home(navHostController: NavHostController, homeVM: HomeVM = hiltViewModel()) {
    var id by remember {
        mutableStateOf("")
    }
    var dismiss by remember {
        mutableStateOf(false)
    }
    val lifeCycleOwner = LocalLifecycleOwner.current
    (MainActivity.weakReference.get() as MainActivity).currentScreenClick.observe(lifeCycleOwner) { currentCall ->
        when (currentCall) {
            Screens.Home.route -> {
                dismiss = true
            }
        }

    }
    if (dismiss) {
        Dialog(
            onDismissRequest = { }, properties = DialogProperties(
                dismissOnBackPress = true, dismissOnClickOutside = true,

                )
        ) {
            ui.MyPopUp(onDismiss = {
                dismiss = it
                Log.e(TAG, "Home: $it")
            }) {
                homeVM.saveUser(it)
                dismiss = false
                Log.e(TAG, "Home: add")
            }
        }
    }


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
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = id,
                onValueChange = {
                    id = it
                    homeVM.searchByStringList(id)
                },
                placeholder = {
                    Text(text = "Search", style = MaterialTheme.typography.labelSmall)
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search",
                        modifier = Modifier.clickable { homeVM.searchByStringList(id) })
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        homeVM.searchByStringList(id)
                    }
                )
            )
            /*OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = id,
                    onValueChange = {
                        if (it.length <= 10) {
                            id = it
                        }
                        idForCall = if (id.isNotEmpty() && id.matches(Regex("^\\d+\$"))) {
                            id.trim().toLong()
                        } else {
                            -1
                        }
                        homeVM.searchById(idForCall)
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (idForCall >= 0)
                                homeVM.searchById(idForCall)
                        }
                    )
                )*/
        }

        AnimatedVisibility(visible = homeVM.userByNameList.isNotEmpty()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Search Results",
                    style = MaterialTheme.typography.bodyLarge
                )
                homeVM.userByNameList.forEach { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.cardElevation(15.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "${user.id}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = user.name,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = user.email,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }


        }
        Text(
            text = "Added cards",
            style = MaterialTheme.typography.bodyLarge
        )

        homeVM.data.forEach { user ->
            var click by remember {
                mutableStateOf(false)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { click = !click },
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(15.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        Modifier
                            .padding(10.dp)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = "${user.id}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = user.email,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    AnimatedVisibility(visible = click) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red,
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                                .clickable { homeVM.deleteData(user) }
                                .padding(horizontal = 20.dp, vertical = 25.dp)
                        )
                    }
                }
            }
        }
    }

}