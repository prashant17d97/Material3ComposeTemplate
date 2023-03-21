package com.prashant.material3_compose_template.uimaterials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
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
    fun MyPopUp(onDismiss: (Boolean) -> Unit, dataValue: (User) -> Unit) {
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
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)

                    }
                )
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
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyBoardManager?.hide()
                    }
                )
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

    @Preview
    @Composable
    fun Preview() {
        MyPopUp(onDismiss = {}, dataValue = {})
    }

}