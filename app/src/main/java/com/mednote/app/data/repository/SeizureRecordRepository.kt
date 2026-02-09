package com.mednote.app.data.repository

import com.mednote.app.data.dao.SeizureRecordDao
import com.mednote.app.data.model.SeizureRecord
import kotlinx.coroutines.flow.Flow

class SeizureRecordRepository(private val dao: SeizureRecordDao) {
    
    fun getAllRecords(): Flow<List<SeizureRecord>> = dao.getAllRecords()
    
    fun getRecordsByDate(date: String): Flow<List<SeizureRecord>> = dao.getRecordsByDate(date)
    
    fun getAllDates(): Flow<List<String>> = dao.getAllDates()
    
    suspend fun insert(record: SeizureRecord): Long = dao.insert(record)
    
    suspend fun update(record: SeizureRecord) = dao.update(record)
    
    suspend fun delete(record: SeizureRecord) = dao.delete(record)
    
    suspend fun deleteAll() = dao.deleteAll()
}
