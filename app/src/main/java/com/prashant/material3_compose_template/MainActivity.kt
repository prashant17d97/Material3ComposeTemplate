package com.prashant.material3_compose_template

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.prashant.material3_compose_template.interfaces.UiConfiguration
import com.prashant.material3_compose_template.navigation.NavGraph
import com.prashant.material3_compose_template.theme.ComposeTemplateTheme
import com.prashant.material3_compose_template.uiconfiguration.UIConfiguration
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MainActivity : ComponentActivity(), UiConfiguration {

    private var mutableState by mutableStateOf(UIConfiguration())

    companion object {
        lateinit var weakReference: WeakReference<Context>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakReference = WeakReference(this)
        setContent {
            val navHostController = rememberNavController()

            ComposeTemplateTheme(uiConfiguration = mutableState) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        NavGraph(navHostController = navHostController)
//                        CircularProgressAnimated()
                    }
                }
            }

        }
    }

    @Composable
    private fun CircularProgressAnimated() {
        val progressValue = 0.75f
        val infiniteTransition = rememberInfiniteTransition()

        val progressAnimationValue by infiniteTransition.animateFloat(
            initialValue = 0.0f,
            targetValue = progressValue, animationSpec = infiniteRepeatable(animation = tween(900))
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            CircularProgressIndicator(progress = progressAnimationValue)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        weakReference = WeakReference(null)
    }

    override fun onResume() {
        super.onResume()
        weakReference = WeakReference(this)

    }

    override fun darkTheme(uiConfiguration: UIConfiguration) {
        mutableState = uiConfiguration
        Log.e("Mainactivity", "darkTheme:$uiConfiguration")
    }
}
