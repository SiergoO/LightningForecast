package com.sdamashchuk.app.navigation

import android.os.Build
import android.os.Parcelable
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.sdamashchuk.forecast.ui.ForecastScreen
import com.sdamashchuk.overview.ui.OverviewScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String,
    screenTitle: MutableState<String>,
    navigationIconVisibilityState: MutableState<Boolean>
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        builder = {
            onOverviewScreen(navController, screenTitle, navigationIconVisibilityState)
            onForecastScreen(navController, screenTitle, navigationIconVisibilityState)
        }
    )
}

private fun NavGraphBuilder.onOverviewScreen(
    navController: NavController,
    screenTitle: MutableState<String>,
    navigationIconVisibility: MutableState<Boolean>
) {
    composable(
        route = NavDestination.Overview.destination,
        enterTransition = { fadeIn() },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { fadeIn() },
        popExitTransition = { ExitTransition.None }
    ) {
        navigationIconVisibility.value = false
        OverviewScreen(
            onAreaDetected = { screenTitle.value = it },
            navigateToDailyForecast = { location ->
                navController.navigate(
                    NavDestination.Forecast.destination,
                    Pair("location", location)
                )
            }
        )
    }
}

private fun NavGraphBuilder.onForecastScreen(
    navController: NavController,
    screenTitle: MutableState<String>,
    navigationIconVisibility: MutableState<Boolean>
) {
    composable(
        route = NavDestination.Forecast.destination,
        enterTransition = { fadeIn() },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { fadeIn() },
        popExitTransition = { ExitTransition.None }
    ) {
        navigationIconVisibility.value = true
        ForecastScreen(
            onDaySelected = { screenTitle.value = it }
        )
    }
}

fun NavController.navigate(
    route: String,
    vararg args: Pair<String, Parcelable>
) {
    navigate(route)
    requireNotNull(currentBackStackEntry?.arguments).apply {
        args.forEach { (key: String, arg: Parcelable) ->
            putParcelable(key, arg)
        }
    }
}