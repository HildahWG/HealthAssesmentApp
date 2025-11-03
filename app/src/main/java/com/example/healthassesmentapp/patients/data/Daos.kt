package com.example.healthassesmentapp.patients.data


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: PatientEntity): Long // returns generated id

   // @Query("SELECT * FROM patients WHERE id = :id LIMIT 1")
    //suspend fun getPatientById(id: Long): PatientEntity?

    @Query("SELECT * FROM patients WHERE `unique` = :unique LIMIT 1")
    suspend fun getPatientByUnique(unique: String): PatientEntity?

    @Query("SELECT * FROM patients ORDER BY id DESC LIMIT 1")
    suspend fun getLatestPatient(): PatientEntity?
    @Query("SELECT * FROM patients WHERE id = :id")
    fun getPatientById(id: Long): Flow<PatientEntity>

}

@Dao
interface VitalsDao {
    @Insert
    suspend fun insertVitals(vitals: VitalsEntity): Long

    @Query("SELECT * FROM vitals WHERE patientId = :patientId")
    suspend fun getVitalsForPatient(patientId: Long): List<VitalsEntity>

    @Query("SELECT * FROM vitals WHERE id = :id LIMIT 1")
    suspend fun getVitalsById(id: Long): VitalsEntity?
}
@Dao
interface AssessmentDao {
    @Insert
    suspend fun insertAssessment(assessment: AssessmentEntity): Long

    @Query("SELECT * FROM assessments WHERE patientId = :patientId")
    suspend fun getAssessmentsForPatient(patientId: Long): List<AssessmentEntity>
}