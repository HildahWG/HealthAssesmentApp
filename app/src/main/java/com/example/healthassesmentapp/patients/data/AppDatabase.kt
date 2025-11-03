package com.example.healthassesmentapp.patients.data


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PatientEntity::class, VitalsEntity::class, AssessmentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun vitalsDao(): VitalsDao
    abstract fun assessmentDao(): AssessmentDao
}