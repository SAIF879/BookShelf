package com.example.bookshelf.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.screens.ReaderSplashScreen
import com.example.bookshelf.screens.details.ReaderBookDetailsScreen
import com.example.bookshelf.screens.home.ReaderHomeScreen
import com.example.bookshelf.screens.login.ReaderLoginScreen
import com.example.bookshelf.screens.search.SearchScreen

@Composable
fun ReaderNavigation() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name ){

    composable(ReaderScreens.SplashScreen.name){ ReaderSplashScreen(navController = navController) }
    composable(ReaderScreens.ReaderHomeScreen.name){ ReaderHomeScreen(navController = navController) }
    composable(ReaderScreens.SearchScreen.name){ SearchScreen(navController = navController) }
    composable(ReaderScreens.DetailsScreen.name){ ReaderBookDetailsScreen(navController = navController) }
    composable(ReaderScreens.ReaderLoginScreen.name){ ReaderLoginScreen(navController = navController) }


  }
}