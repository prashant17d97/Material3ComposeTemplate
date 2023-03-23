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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.prashant.material3_compose_template.datastore.THEME_KEY
import com.prashant.material3_compose_template.interfaces.UiConfiguration
import com.prashant.material3_compose_template.navigation.NavGraph
import com.prashant.material3_compose_template.navigation.Screens
import com.prashant.material3_compose_template.theme.ComposeTemplateTheme
import com.prashant.material3_compose_template.themeproperties.ThemeProperties
import com.prashant.material3_compose_template.uimaterials.UIMaterials.Companion.uiMaterials
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MainActivity : ComponentActivity(), UiConfiguration {

    private var mutableState by mutableStateOf(ThemeProperties())
    private var showLoader by mutableStateOf(false)
    var currentScreenClick = MutableLiveData("")
    private val mainActivityVM by viewModels<MainActivityVM>()

    companion object {
        lateinit var weakReference: WeakReference<Context>
        var mainActivity: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakReference = WeakReference(this)
        mainActivity = weakReference.get() as? MainActivity

        setContent {
            val navHostController = rememberNavController()

            ComposeTemplateTheme(themeProperties = mutableState) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(navHostController = navHostController)
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        mainActivityVM.dataStoreUtil.retrieveObject(THEME_KEY, ThemeProperties::class.java) {
            if (it != null) {
                mutableState = it
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(navHostController: NavHostController) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()

        val showAppBar: Boolean = when (navBackStackEntry?.destination?.route) {
            Screens.Splash.route, Screens.Login.route -> false
            else -> true
        }
        Scaffold(
            topBar = {
                if (showAppBar) {
                    TopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer),
                        title = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = navBackStackEntry?.destination?.route ?: "",
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Icon(
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = "Search",
                                    modifier = Modifier.clickable {
                                        currentScreenClick.value =
                                            navBackStackEntry?.destination?.route ?: ""

                                    })
                            }
                        },
                        scrollBehavior = null
                    )
                }
            }
        ) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                NavGraph(navHostController = navHostController)
                if (showLoader) {
                    CircularProgressAnimated()
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
        weakReference = WeakReference(null)
        mainActivity = null
        uiMaterials = WeakReference(null)
        mutableState
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        weakReference = WeakReference(this)
        mainActivityVM.dataStoreUtil.retrieveObject(THEME_KEY, ThemeProperties::class.java) {
            if (it != null) {
                mutableState = it
            }
        }
    }

    override fun uiConfiguration() {
        Log.e("TAG", "changeConfiguration: ")
        mainActivityVM.dataStoreUtil.retrieveObject(THEME_KEY, ThemeProperties::class.java) {
            if (it != null) {
                mutableState = it
            }
        }
    }

    override fun uiConfigurationTemporary(
        darkTheme: Boolean?, fontFamily: String, fontStyle: FontStyle
    ) {
        mutableState = mutableState.copy(
            isDarkTheme = darkTheme.takeIf { it != null } ?: mutableState.isDarkTheme,
            fontFamily = fontFamily.takeIf { it.isNotEmpty() } ?: mutableState.fontFamily,
            fontStyle = fontStyle)
    }

    override fun showLoader(boolean: Boolean) {
        showLoader = boolean
    }
}
