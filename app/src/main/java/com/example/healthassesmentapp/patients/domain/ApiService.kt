package com.example.healthassesmentapp.patients.domain

import androidx.room.PrimaryKey
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val unique: String,
    val dob: String,
    val gender: String,
    val reg_date: String
)
@Serializable
data class VitalsRequest(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val visit_date: String,
    val height: Double,
    val weight: Double,
    val bmi: Double,

    )
@Serializable
data class GeneralRequest(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val general_health: String?,
    val on_diet: String?,
    val on_drugs: String?,
    val comments: String,
    val visit_date: String,
    val patient_id: Long,

    )

interface ApiService {
    @Headers("Accept: application/json")
    @POST("patients/register")
    suspend fun registerPatient(@Body data: RegisterRequest): Response<Unit>

    @Headers("Accept: application/json")
    @POST("vital/add")
    suspend fun addVitals(@Body data: VitalsRequest): Response<Unit>

    @Headers("Accept: application/json")
    @POST("general/add")
    suspend fun addGeneral(@Body data: GeneralRequest): Response<Unit>
}
