package com.chicken.sidehop.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chicken.sidehop.ui.screens.app.LoadingScreen
import com.chicken.sidehop.ui.screens.app.MenuScreen
import com.chicken.sidehop.ui.screens.game.ResultScreen
import com.chicken.sidehop.ui.screens.app.SettingsScreen
import com.chicken.sidehop.ui.screens.game.GameScreen
import com.chicken.sidehop.ui.screens.game.GameViewModel

sealed class AppRoute(val route: String) {
    data object Loading : AppRoute("loading")
    data object Menu : AppRoute("menu")
    data object Game : AppRoute("game")
    data object Settings : AppRoute("settings")
    data object Result : AppRoute("result/{score}") {
        fun build(score: Int) = "result/$score"
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Loading.route,
        modifier = modifier,
        enterTransition = { fadeIn(tween(300)) },
        exitTransition = { fadeOut(tween(250)) }
    ) {
        composable(AppRoute.Loading.route) {
            LoadingScreen(onFinished = {
                navController.navigate(AppRoute.Menu.route) {
                    popUpTo(AppRoute.Loading.route) { inclusive = true }
                }
            })
        }
        composable(AppRoute.Menu.route) {
            MenuScreen(
                onPlay = {
                    navController.navigate(AppRoute.Game.route) {
                        popUpTo(AppRoute.Menu.route)
                    }
                },
                onSettings = { navController.navigate(AppRoute.Settings.route) }
            )
        }
        composable(AppRoute.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable(AppRoute.Game.route) {
            val viewModel: GameViewModel = hiltViewModel()
            GameScreen(
                viewModel = viewModel,
                onExit = {
                    navController.navigate(AppRoute.Menu.route) {
                        popUpTo(AppRoute.Menu.route) { inclusive = true }
                    }
                },
                onGameOver = { score ->
                    navController.navigate(AppRoute.Result.build(score)) {
                        popUpTo(AppRoute.Menu.route) { inclusive = false }
                    }
                }
            )
        }
        composable(
            route = AppRoute.Result.route,
            arguments = listOf(navArgument("score") { type = NavType.IntType })
        ) { backStack ->
            val score = backStack.arguments?.getInt("score") ?: 0
            ResultScreen(
                score = score,
                onRetry = {
                    navController.navigate(AppRoute.Game.route) {
                        popUpTo(AppRoute.Menu.route)
                    }
                },
                onMenu = {
                    navController.navigate(AppRoute.Menu.route) {
                        popUpTo(AppRoute.Menu.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
