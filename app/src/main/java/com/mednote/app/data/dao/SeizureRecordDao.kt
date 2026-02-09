package com.mednote.app.data.dao

import androidx.room.*
import com.mednote.app.data.model.SeizureRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface SeizureRecordDao {
    @Query("SELECT * FROM seizure_records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<SeizureRecord>>

    @Query("SELECT * FROM seizure_records WHERE date = :date ORDER BY timestamp DESC")
    fun getRecordsByDate(date: String): Flow<List<SeizureRecord>>

    @Insert
    suspend fun insert(record: SeizureRecord): Long

    @Update
    suspend fun update(record: SeizureRecord)

    @Delete
    suspend fun delete(record: SeizureRecord)

    @Query("DELETE FROM seizure_records")
    suspend fun deleteAll()

    @Query("SELECT DISTINCT date FROM seizure_records ORDER BY date DESC")
    fun getAllDates(): Flow<List<String>>
}
