package com.mednote.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seizure_records")
data class SeizureRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val bigChain: String,
    val smallChain: String,
    val tonic: String,
    val clonic: String,
    val timestamp: Long = System.currentTimeMillis()
)
