package com.prashant.material3_compose_template.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.navigation.compose.rememberNavController
import com.prashant.material3_compose_template.datastore.THEME_KEY
import com.prashant.material3_compose_template.interfaces.UiConfiguration
import com.prashant.material3_compose_template.navigation.NavGraph
import com.prashant.material3_compose_template.theme.ComposeTemplateTheme
import com.prashant.material3_compose_template.uiconfiguration.UIConfiguration
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MainActivity : ComponentActivity(), UiConfiguration {

    private var mutableState by mutableStateOf(UIConfiguration())
    private var showLoader by mutableStateOf(false)
    private val mainActivityVM by viewModels<MainActivityVM>()

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
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        NavGraph(navHostController = navHostController)
                        if (showLoader) {
                            CircularProgressAnimated()
                        }
                    }
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        mainActivityVM.dataStoreUtil.retrieveObject(THEME_KEY, UIConfiguration::class.java) {
            if (it != null) {
                mutableState = it
            }
        }
    }

    @Composable
    private fun CircularProgressAnimated() {
        val progressValue = 0.75f
        val infiniteTransition = rememberInfiniteTransition()

        val progressAnimationValue by infiniteTransition.animateFloat(
            initialValue = 0.0f,
            targetValue = progressValue,
            animationSpec = infiniteRepeatable(animation = tween(900))
        )
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
        ) {
            Dialog(
                onDismissRequest = { }, properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                    securePolicy = SecureFlagPolicy.SecureOn
                )
            ) {
                CircularProgressIndicator(progress = progressAnimationValue)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        weakReference = WeakReference(null)
    }

    override fun onResume() {
        super.onResume()
        weakReference = WeakReference(this)
        mainActivityVM.dataStoreUtil.retrieveObject(THEME_KEY, UIConfiguration::class.java) {
            if (it != null) {
                mutableState = it
            }
        }
    }

    override fun changeConfiguration() {
        Log.e("TAG", "changeConfiguration: ")
        mainActivityVM.dataStoreUtil.retrieveObject(THEME_KEY, UIConfiguration::class.java) {
            if (it != null) {
                mutableState = it
            }
        }
    }

    override fun changeConfigurationTemporary(
        darkTheme: Boolean?, fontFamily: String, fontStyle: FontStyle
    ) {
        mutableState = mutableState.copy(isDarkTheme = darkTheme.takeIf { it != null }
            ?: mutableState.isDarkTheme,
            fontFamily = fontFamily.takeIf { it.isNotEmpty() } ?: mutableState.fontFamily,
            fontStyle = fontStyle)
    }

    override fun showLoader(boolean: Boolean) {
        showLoader = boolean
    }
}
