package com.sdamashchuk.app.navigation

sealed class NavDestination(open val destination: String) {
    object Overview : NavDestination("destination_overview_screen")
    object Forecast : NavDestination("destination_forecast_screen")
}
