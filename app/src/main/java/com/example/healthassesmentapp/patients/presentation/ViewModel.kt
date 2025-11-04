package com.example.healthassesmentapp.patients.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthassesmentapp.patients.data.AssessmentEntity
import com.example.healthassesmentapp.patients.data.PatientEntity
import com.example.healthassesmentapp.patients.data.PatientRepository
import com.example.healthassesmentapp.patients.data.VitalsEntity
import com.example.healthassesmentapp.patients.domain.ApiService
import com.example.healthassesmentapp.patients.domain.GeneralRequest
import com.example.healthassesmentapp.patients.domain.RegisterRequest
import com.example.healthassesmentapp.patients.domain.VitalsRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PatientViewModel @Inject constructor(
    val repo: PatientRepository,
    private val apiService: ApiService
) : ViewModel(), PatientActions  {

    private val _currentPatientId = MutableStateFlow<Long?>(null)
    val currentPatientId = _currentPatientId.asStateFlow()

    override fun setCurrentPatientId(id: Long) {
        _currentPatientId.value = id
    }

    override fun saveRegistration(patient: PatientEntity, onComplete: (Long) -> Unit) {
        viewModelScope.launch {
            val id = repo.insertPatient(patient)
            _currentPatientId.value = id
            onComplete(id)
        }
    }
    fun getPatientById(id: Long): Flow<PatientEntity> {
        return repo.getPatientById(id)
    }


    override fun saveVitals(vitals: VitalsEntity, onComplete: (Long) -> Unit) {
        viewModelScope.launch {
            val id = repo.insertVitals(vitals)
            onComplete(id)
        }
    }

   override fun saveAssessment(assessment: AssessmentEntity, onComplete: (Long) -> Unit ) {
        viewModelScope.launch {
            val id = repo.insertAssessment(assessment)
            onComplete(id)
        }
    }


    fun getPatientFullData(patientId: Long, onResult: (PatientEntity?, List<VitalsEntity>, List<AssessmentEntity>) -> Unit) {
        viewModelScope.launch {
            val patient = repo.getPatientByUnique("") // placeholder — better use repo.getPatientById
            // better:
            val p = repo.getLatestPatient()
            val vitals = repo.getVitalsForPatient(patientId)
            val assessments = repo.getAssessmentsForPatient(patientId)
            onResult(p, vitals, assessments)
        }
    }


    fun sendAllToApi(
        patient: PatientEntity,
        vitals: List<VitalsEntity>,
        assessments: List<AssessmentEntity>,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
                try {
                    val patientId = repo.insertPatient(patient)



                    val patientRequest = RegisterRequest(
                        firstname = patient.firstname,
                        lastname = patient.lastname,
                        unique = patient.unique,
                        dob = patient.dob,
                        gender = patient.gender,
                        reg_date = patient.reg_date
                    )
                    apiService.registerPatient(patientRequest)


                    val vitalsWithId = vitals.map { v ->
                        val vitalId = repo.insertVitals(v.copy(patientId = patientId))
                        v.copy(id = vitalId, patientId = patientId)

                    }


                    vitalsWithId.forEach { v ->
                         val vitalsRequest = VitalsRequest(
                            visit_date = v.visit_date,
                            height = v.height,
                            weight = v.weight,
                            bmi = v.bmi,

                        )
                        apiService.addVitals(vitalsRequest)
                    }

                    val assessmentsWithId = assessments.map { a ->
                        val assessmentId = repo.insertAssessment(a.copy(patientId = patientId))
                        a.copy(id = assessmentId, patientId = patientId)
                    }

                    assessmentsWithId.forEach { a ->
                        val assessmentRequest = GeneralRequest(
                            general_health = a.general_health,
                            on_diet = a.on_diet,
                            on_drugs = a.on_drugs,
                            comments = a.comments,
                            visit_date = a.visit_date,
                            patient_id = patientId
                        )
                        apiService.addGeneral(assessmentRequest)
                    }


                    onSuccess()
                } catch (e: Exception) {
                    onError(e.message ?: "Network error")
                }
            }}}
