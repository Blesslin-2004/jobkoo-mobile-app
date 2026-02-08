package com.jgene.aijobfinder.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.jgene.aijobfinder.feature.auth.login.LoginScreen
import com.jgene.aijobfinder.feature.home.*
import com.jgene.aijobfinder.feature.splash.SplashScreen
import java.net.URLEncoder
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val gson = remember { Gson() }

    var showSearchSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        // SPLASH
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // LOGIN
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // HOME
        composable(Routes.HOME) {
            HomeScreen(
                onAddClick = { showSearchSheet = true },
                onJobClick = { job ->
                    val encodedJob = URLEncoder.encode(
                        gson.toJson(job),
                        StandardCharsets.UTF_8.toString()
                    )
                    navController.navigate("${Routes.JOB_DETAIL}/$encodedJob")
                }
            )

            // 🔽 SEARCH BOTTOM SHEET
            if (showSearchSheet) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { showSearchSheet = false }
                ) {
                    SearchBottomSheet(
                        onDismiss = { showSearchSheet = false }
                    )
                }
            }
        }

        // JOB DETAIL
        composable(
            route = "${Routes.JOB_DETAIL}/{job}",
            arguments = listOf(
                navArgument("job") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val jobJson = backStackEntry.arguments?.getString("job")
            val job = remember(jobJson) {
                gson.fromJson(
                    URLDecoder.decode(
                        jobJson,
                        StandardCharsets.UTF_8.toString()
                    ),
                    Job::class.java
                )
            }

            JobDetailScreen(job = job)
        }
    }
}
