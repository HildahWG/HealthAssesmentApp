package com.example.healthassesmentapp.patients.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.hilt.navigation.compose.hiltViewModel
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
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.healthassesmentapp.patients.data.AssessmentEntity
import com.example.healthassesmentapp.patients.data.PatientEntity
import com.example.healthassesmentapp.patients.data.PatientRepository
import com.example.healthassesmentapp.patients.data.VitalsEntity
import com.example.healthassesmentapp.patients.domain.ApiService
import com.example.healthassesmentapp.patients.navigation.Screens
import com.example.healthassesmentapp.patients.presentation.components.CustomSmallButton
import com.example.healthassesmentapp.patients.presentation.components.DatePickerRow
import com.example.healthassesmentapp.patients.presentation.components.GenderInputField
import com.example.healthassesmentapp.patients.presentation.components.LabelInputRow
import com.example.healthassesmentapp.patients.presentation.components.MyTopAppBar

@Composable
fun RegistrationPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PatientViewModel = hiltViewModel()
    //viewModel: PatientActions = hiltViewModel()
) {

    Scaffold(
        modifier =
            modifier.fillMaxWidth(),
        topBar = {
            MyTopAppBar(
                title = "Patient Registration Form"
            )
        }
    ) { innerPadding ->
        Column(
            modifier =
                modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
        ) {
            var patientNumber by remember { mutableStateOf("") }
            var firstName by remember {mutableStateOf("")  }
            var lastName by remember {mutableStateOf("") }
            var regDate by remember { mutableStateOf("") }
            var dob by remember { mutableStateOf("") }
            var gender by remember { mutableStateOf("") }

            LabelInputRow(
                label = "Patient Number:",
                onValueChange = { patientNumber = it },
                textValue = patientNumber,
            )

            LabelInputRow(
                label = "First Name:",
                onValueChange = { firstName= it },
                textValue = firstName,
            )
            LabelInputRow(
                label = "Patient Number:",
                onValueChange = { lastName = it },
                textValue = lastName,
            )
            DatePickerRow(
                text = "Registration Date",
                dateValue = regDate,
                onDateSelected = { regDate = it }

            )
            DatePickerRow(
                text = "DOB",
                dateValue = dob,
                onDateSelected = { dob = it }
            )
            GenderInputField()
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
                    onNavigate = {if (firstName.isBlank() || lastName.isBlank() || patientNumber.isBlank()) {
                        println("No empty fields")
                        return@CustomSmallButton
                    }

                        val patient = PatientEntity(
                            firstname = firstName,
                            lastname = lastName,
                            unique = patientNumber,
                            dob = dob,
                            gender = gender,
                            reg_date = regDate
                        )


                        /*viewModel.saveRegistration(patient) { insertedId ->
                            viewModel.setCurrentPatientId(insertedId)
                            println(" Saved locally with ID: $insertedId")
                            navController.navigate(Screens.VitalsScreen.withArgs(insertedId))*/
                        viewModel.saveRegistration(patient) { insertedId ->
                            viewModel.setCurrentPatientId(insertedId)
                            println("Saved locally with ID: $insertedId")
                            navController.navigate(Screens.VitalsScreen.withArgs(insertedId))

                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))


        }
    }
}
/*
@Preview
@Composable
fun RegistrationPagePreview() {
    val navController = rememberNavController()

    val fakeViewModel = object : PatientActions {
        override fun saveRegistration(patient: PatientEntity, onSaved: (Long) -> Unit) {
            onSaved(0L) // do nothing
        }

        override fun setCurrentPatientId(id: Long) {
            // do nothing
        }

        override fun saveAssessment(assessment: AssessmentEntity, onComplete: (Long) -> Unit) {
            onComplete(0L) // do nothing
        }

        override fun saveVitals(vitals: VitalsEntity, onComplete: (Long) -> Unit) {
            onComplete(0L) // do nothing
        }
    }

    RegistrationPage(
        navController = navController,
        viewModel = fakeViewModel
    )
}*/