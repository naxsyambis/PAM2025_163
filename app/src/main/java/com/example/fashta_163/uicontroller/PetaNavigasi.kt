package com.example.fashta_163.uicontroller

import com.example.fashta_163.viewmodel.provider.PenyediaViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fashta_163.repository.AplikasiFash
import com.example.fashta_163.uicontroller.route.*
import com.example.fashta_163.view.auth.LoginScreen
import com.example.fashta_163.view.auth.RegisterScreen
import com.example.fashta_163.viewmodel.LoginViewModel
import com.example.fashta_163.viewmodel.RegisterViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun FashApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val containerApp = remember {
        (context.applicationContext as AplikasiFash).containerApp
    }

    NavHost(
        navController = navController,
        startDestination = DestinasiLogin.route,
        modifier = modifier
    ) {

        // ===== LOGIN =====
        composable(DestinasiLogin.route) {
            val loginViewModel: LoginViewModel = viewModel(
                factory = PenyediaViewModel.provideLoginViewModelFactory(
                    containerApp.repositoryAuth
                )
            )

            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(DestinasiDashboard.route) {
                        popUpTo(DestinasiLogin.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(DestinasiRegister.route)
                }
            )
        }

        // ===== REGISTER =====
        composable(DestinasiRegister.route) {
            val registerViewModel: RegisterViewModel = viewModel(
                factory = PenyediaViewModel.provideRegisterViewModelFactory(
                    containerApp.repositoryAuth
                )
            )

            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = {
                    navController.popBackStack()
                },
                onNavigateBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // ===== DASHBOARD (placeholder) =====
        composable(DestinasiDashboard.route) {
            /* DashboardScreen() */
        }
    }
}
