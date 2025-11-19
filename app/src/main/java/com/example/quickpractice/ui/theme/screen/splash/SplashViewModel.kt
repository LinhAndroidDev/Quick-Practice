package com.example.quickpractice.ui.theme.screen.splash

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.quickpractice.ui.theme.navigation.Route
import com.example.quickpractice.util.SharePreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var shared: SharePreferenceRepository

    fun checkNavigation(navController: NavController) {
        if (shared.getToken().isNullOrEmpty()) {
            goToLogin(navController)
        } else {
            goToHome(navController)
        }
    }

    private fun goToLogin(navController: NavController) {
        navController.navigate(Route.Login.route) {
            popUpTo(Route.Splash.route) {
                inclusive = true
            }
        }
    }

    private fun goToHome(navController: NavController) {
        navController.navigate(Route.HOME.route) {
            popUpTo(Route.Splash.route) {
                inclusive = true
            }
        }
    }
}