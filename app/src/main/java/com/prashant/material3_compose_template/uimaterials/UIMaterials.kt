package com.prashant.material3_compose_template.uimaterials

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prashant.material3_compose_template.commonfunctions.CommonFunctions.toastAt
import com.prashant.material3_compose_template.roomdb.User
import java.lang.ref.WeakReference

class UIMaterials {
    companion object {
        var uiMaterials = WeakReference(UIMaterials())
        val ui = uiMaterials.get() as UIMaterials
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun AddUserPopUp(onDismiss: (Boolean) -> Unit, dataValue: (User) -> Unit) {
        var name by remember {
            mutableStateOf("")
        }
        var mail by remember {
            mutableStateOf("")
        }
        val focusManager = LocalFocusManager.current
        val popUpContext = LocalContext.current
        val keyBoardManager = LocalSoftwareKeyboardController.current
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Add a new entry", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = name,
                onValueChange = {
                    name = it

                },
                placeholder = {
                    Text(text = "Name", style = MaterialTheme.typography.labelSmall)
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)

                })
            )

            //Email
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = mail,
                onValueChange = {
                    mail = it

                },
                placeholder = {
                    Text(text = "Email", style = MaterialTheme.typography.labelSmall)
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email,
                    autoCorrect = true
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    keyBoardManager?.hide()
                })
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { onDismiss(false) }) {
                    Text(text = "Cancel", style = MaterialTheme.typography.labelSmall)
                }
                Button(onClick = {
                    if (name.isNotEmpty() && mail.isNotEmpty()) {
                        dataValue(User(name = name.trim(), email = mail.trim()))
                        name = ""
                        mail = ""
                    } else {
                        when {
                            name.isEmpty() -> "Please enter the name!" toastAt popUpContext
                            mail.isEmpty() -> "Please enter the mail!" toastAt popUpContext
                        }
                    }

                }) {
                    Text(text = "Add", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }

    @Composable
    fun UserCard(
        user: User,
        showDeleteIcon: Boolean = true,
        click: () -> Unit = {},
        delete: () -> Unit
    ) {
        var showDelete by remember {
            mutableStateOf(false)
        }
        val localDensity = LocalDensity.current

        // Create element height in dp state
        var columnHeightDp by remember {
            mutableStateOf(0.dp)
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(20.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = MutableInteractionSource(), indication = null
                        ) {
                            if (showDeleteIcon) {
                                showDelete = !showDelete
                                click()
                            }
                        },
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        Modifier
                            .weight(3.5f)
                            .height(IntrinsicSize.Max)
                            .onGloballyPositioned { coordinates ->
                                // Set column height using the LayoutCoordinates
                                columnHeightDp =
                                    with(localDensity) { coordinates.size.height.toDp() }
                            }
                            .padding(10.dp)

                    ) {
                        Text(
                            text = "${user.id}", style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = user.name, style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = user.email, style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    AnimatedVisibility(visible = showDelete) {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .width(60.dp)
                                .height(columnHeightDp)
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                                .clickable { delete() }) {
                            Image(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete",
                                colorFilter = ColorFilter.tint(Color.Red),
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }
            SpacerHeight(
                5.dp
            )
        }
    }

    @Composable
    fun SpacerHeight(value: Dp) {
        Spacer(modifier = Modifier.height(value))
    }

    @Composable
    private fun SpacerWidth(value: Dp) {
        Spacer(modifier = Modifier.width(value))
    }

    @Preview
    @Composable
    fun Preview() {
        UserCard(user = User(
            id = 0, name = "Prashant", email = "pk503230@gmail.com"
        ), click = {}, delete = {})
    }

}