package com.example.healthassesmentapp.patients.presentation


import android.view.PixelCopy.request
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.healthassesmentapp.patients.data.AssessmentEntity
import com.example.healthassesmentapp.patients.data.PatientEntity
import com.example.healthassesmentapp.patients.data.VitalsEntity
import com.example.healthassesmentapp.patients.navigation.Screens
import com.example.healthassesmentapp.patients.presentation.components.Comments
import com.example.healthassesmentapp.patients.presentation.components.CustomSmallButton
import com.example.healthassesmentapp.patients.presentation.components.DatePickerRow
import com.example.healthassesmentapp.patients.presentation.components.LabelInputRow
import com.example.healthassesmentapp.patients.presentation.components.MyTopAppBar
import com.example.healthassesmentapp.patients.presentation.components.QuestionWithCheckboxes
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun OverWeightAssesmentPage(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: PatientViewModel = hiltViewModel(),
    patientId: Long

    ) {
    Scaffold(
        modifier =
            modifier.fillMaxWidth(),
        topBar = {
            MyTopAppBar(
                title =  "Patient Visit Form B"
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
            var comments by remember { mutableStateOf("") }
            var regDate by remember { mutableStateOf("") }

            var selectedGeneralHealth by remember { mutableStateOf<String?>(null) }
            var selectedDrugs by remember { mutableStateOf<String?>(null) }


            LabelInputRow(
                label = "Patient Number:",
                onValueChange = { patientNumber = it },
                textValue = patientNumber,
            )
            DatePickerRow(
                text = "Registration Date",
                        onDateSelected = { selected ->
                    regDate = selected}
            )

            QuestionWithCheckboxes(
                question = "General Health?",
                option1 = "Good",
                option2 = "Bad"
            )
            QuestionWithCheckboxes(
                question = "Are you Currently taking any drugs?",
                option1 = "Yes",
                option2 = "No"

            )
            Comments(
                textValue = comments,
                onValueChange = { comments = it },
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
                    onNavigate = {
                        // Launch coroutine to save and send data
                        viewModel.viewModelScope.launch {
                            val patientId = viewModel.currentPatientId.value ?: 0L

                            if (patientId == 0L) {
                                println("Error: patientId not set")
                                return@launch
                            }

                            // Build assessment entity
                            val assessment = AssessmentEntity(
                                patientId = patientId,
                                vitalId = null,
                                visit_date = regDate,
                                general_health = selectedGeneralHealth,
                                on_diet = null,
                                on_drugs = selectedDrugs,
                                comments = comments
                            )

                            // Save locally
                            viewModel.saveAssessment(assessment) { insertedId ->
                                println("Assessment saved locally with ID: $insertedId")
                            }

                            // Fetch current patient and all relevant data
                            val patient: PatientEntity =
                                viewModel.getPatientById(patientId).first()
                            val vitals: List<VitalsEntity> =
                                listOf(viewModel.repo.getVitalsForPatient(patientId).first())
                            val assessments: List<AssessmentEntity> =
                                listOf(viewModel.repo.getAssessmentsForPatient(patientId).first())


                            viewModel.sendAllToApi(
                                patient = patient,
                                vitals = vitals,
                                assessments = assessments,
                                onSuccess = {
                                    println("Assessment sent to API successfully")
                                    navController.navigate(Screens.OverweightScreen)
                                },
                                onError = { error ->
                                    println("Error sending assessment: $error")
                                }
                            )
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
private fun OverWeightAssesmentPreview() {

   OverWeightAssesmentPage(
       patientId = 2,
   )
}
