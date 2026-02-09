package com.mednote.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mednote.app.data.MedNoteApplication
import com.mednote.app.data.model.SeizureRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SeizureViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = (application as MedNoteApplication).repository
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    private val _todayRecords = MutableStateFlow<List<SeizureRecord>>(emptyList())
    val todayRecords: StateFlow<List<SeizureRecord>> = _todayRecords.asStateFlow()
    
    private val _allRecords = MutableStateFlow<List<SeizureRecord>>(emptyList())
    val allRecords: StateFlow<List<SeizureRecord>> = _allRecords.asStateFlow()
    
    private val _allDates = MutableStateFlow<List<String>>(emptyList())
    val allDates: StateFlow<List<String>> = _allDates.asStateFlow()
    
    private val _selectedDate = MutableStateFlow(dateFormat.format(Date()))
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()
    
    init {
        loadTodayRecords()
        loadAllRecords()
        loadAllDates()
    }
    
    fun loadTodayRecords() {
        viewModelScope.launch {
            repository.getRecordsByDate(_selectedDate.value).collect { records ->
                _todayRecords.value = records
            }
        }
    }
    
    fun loadAllRecords() {
        viewModelScope.launch {
            repository.getAllRecords().collect { records ->
                _allRecords.value = records
            }
        }
    }
    
    fun loadAllDates() {
        viewModelScope.launch {
            repository.getAllDates().collect { dates ->
                _allDates.value = dates
            }
        }
    }
    
    fun selectDate(date: String) {
        _selectedDate.value = date
        loadTodayRecords()
    }
    
    fun saveRecord(
        bigChain: String,
        smallChain: String,
        tonic: String,
        clonic: String
    ) {
        viewModelScope.launch {
            val record = SeizureRecord(
                date = _selectedDate.value,
                bigChain = bigChain,
                smallChain = smallChain,
                tonic = tonic,
                clonic = clonic
            )
            repository.insert(record)
        }
    }
    
    fun deleteRecord(record: SeizureRecord) {
        viewModelScope.launch {
            repository.delete(record)
        }
    }
    
    fun getCurrentDate(): String = dateFormat.format(Date())
}
