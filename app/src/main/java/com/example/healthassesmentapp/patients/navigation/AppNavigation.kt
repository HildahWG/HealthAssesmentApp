package com.example.healthassesmentapp.patients.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.healthassesmentapp.patients.presentation.GeneralAssesmentPage
import com.example.healthassesmentapp.patients.presentation.OverWeightAssesmentPage
import com.example.healthassesmentapp.patients.presentation.RegistrationPage
import com.example.healthassesmentapp.patients.presentation.VitalsPage


@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screens.RegistrationScreen.route,
    ) {

        composable(Screens.RegistrationScreen.route) {
            RegistrationPage(navController = navController)
        }
        composable(
            route = Screens.VitalsScreen.route,
            arguments = listOf(navArgument("patientId") { type = NavType.LongType })
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getLong("patientId") ?: 0L
            VitalsPage(navController = navController, patientId = patientId)
        }


        composable(
            route = Screens.GeneralScreen.route,
            arguments = listOf(navArgument("patientId") { type = NavType.LongType })
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getLong("patientId") ?: 0L
            GeneralAssesmentPage(navController = navController, patientId = patientId)
        }

        composable(
            route = Screens.OverweightScreen.route,
            arguments = listOf(navArgument("patientId") { type = NavType.LongType })
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getLong("patientId") ?: 0L
            OverWeightAssesmentPage(navController = navController, patientId = patientId)
        }
    }
}