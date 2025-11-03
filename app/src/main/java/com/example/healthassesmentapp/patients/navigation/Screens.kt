package com.example.healthassesmentapp.patients.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screens(val route: String) {

    object RegistrationScreen : Screens("registration")

    object VitalsScreen : Screens("vitals/{patientId}") {
        fun withArgs(patientId: Long) = "vitals/$patientId"
    }

    object GeneralScreen : Screens("general/{patientId}") {
        fun withArgs(patientId: Long) = "general/$patientId"
    }

    object OverweightScreen : Screens("overweight/{patientId}") {
        fun withArgs(patientId: Long) = "overweight/$patientId"
    }
}
