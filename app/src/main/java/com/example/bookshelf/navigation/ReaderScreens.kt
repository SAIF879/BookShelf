package com.example.bookshelf.navigation

enum class ReaderScreens {
    SplashScreen,
    ReaderLoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    SearchScreen,
    DetailsScreen,
    UpdateScreen,
    ReaderStatsScreen;

    companion object {
        fun fromRoute(route: String): ReaderScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            ReaderLoginScreen.name -> ReaderLoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            SearchScreen.name -> SearchScreen
            DetailsScreen.name -> DetailsScreen
            UpdateScreen.name -> UpdateScreen
            ReaderStatsScreen.name -> ReaderStatsScreen
            null-> ReaderHomeScreen
            else -> throw java.lang.IllegalArgumentException("Route $route is not recognised")
        }
    }
}