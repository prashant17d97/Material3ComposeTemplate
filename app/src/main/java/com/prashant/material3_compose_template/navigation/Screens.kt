package com.prashant.material3_compose_template.navigation


const val SPLASH = "Splash"
const val LOGIN = "Login"
const val HOME = "Home"

sealed class Screens(val route: String) {
    object Splash : Screens(route = SPLASH)
    object Login : Screens(route = LOGIN)
    object Home : Screens(route = HOME)
}
