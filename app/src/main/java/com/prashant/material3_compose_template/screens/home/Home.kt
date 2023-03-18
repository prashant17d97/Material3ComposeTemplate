package com.prashant.material3_compose_template.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prashant.material3_compose_template.navigation.HOME

@Composable
fun Home(navHostController: NavHostController, homeVM: HomeVM = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Default.Home,
            contentDescription = HOME,
            modifier = Modifier.size(80.dp),
            colorFilter = ColorFilter.tint(Color.White.takeIf { isSystemInDarkTheme() }
                ?: Color.Black)
        )
        Text(
            text = "$HOME Screen",
            style = MaterialTheme.typography.displayLarge
        )
    }
}