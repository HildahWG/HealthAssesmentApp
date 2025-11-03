package com.example.healthassesmentapp.patients.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.healthassesmentapp.patients.data.VitalsEntity
import com.example.healthassesmentapp.patients.navigation.Screens
import com.example.healthassesmentapp.patients.presentation.components.CustomSmallButton
import com.example.healthassesmentapp.patients.presentation.components.DatePickerRow
import com.example.healthassesmentapp.patients.presentation.components.LabelInputRow
import com.example.healthassesmentapp.patients.presentation.components.MyTopAppBar

@Composable
fun VitalsPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PatientViewModel = hiltViewModel(),
    patientId: Long

    ) {
    Scaffold(
        modifier =
            modifier.fillMaxWidth(),
        topBar = {
            MyTopAppBar(
                title = "Patient Vitals Form"
            )
        }
    ) { innerPadding ->
        Column(
            modifier =
                modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
        ) {
            var patientName by remember { mutableStateOf("") }
            var height by remember {mutableStateOf("")  }
            var weight by remember {mutableStateOf("") }
            var BMI by remember {mutableStateOf("") }
            var dov by remember { mutableStateOf("") }

            LabelInputRow(
                label = "Patient Name:",
                onValueChange = { patientName = it },
                textValue = patientName,
            )

            LabelInputRow(
                label = "Height:",
                onValueChange = { height= it },
                textValue = height,
            )
            LabelInputRow(
                label = "Weight:",
                onValueChange = { weight = it },
                textValue = weight,
            )
            LabelInputRow(
                label = "Weight:",
                onValueChange = { BMI = it },
                textValue = BMI,
            )

            DatePickerRow(
                text = "Visit Date",
                onDateSelected = { selected ->
                    dov = selected }

            )

            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),

                ) {
                CustomSmallButton(
                    value = "Close",
                    onNavigate = {},
                    modifier = Modifier.weight(1f)
                )

                CustomSmallButton(
                    value = "Save",
                    onNavigate = { if (patientName.isBlank() || height.isBlank() || weight.isBlank() || BMI.isBlank()) {
                        println("Fields cannot be empty")
                        return@CustomSmallButton
                    }


                        val vitals = VitalsEntity(
                            patientId =  patientId,
                            visit_date =dov,
                            height = height.toDoubleOrNull() ?: 0.0,
                            weight = weight.toDoubleOrNull() ?: 0.0,
                            bmi = BMI.toDoubleOrNull() ?: 0.0
                        )

                        viewModel.saveVitals(vitals) { vitalsId ->
                            println("Vitals saved locally with ID: $vitalsId")
                            navController.navigate(Screens.GeneralScreen.withArgs(patientId))
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))


        }
    }
}
@Preview
@Composable
private fun VitalsPagePreview() {
    //VitalsPage(navController = rememberNavController())
}