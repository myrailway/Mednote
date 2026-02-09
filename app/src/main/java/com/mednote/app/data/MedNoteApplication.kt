package com.mednote.app.data

import android.app.Application
import com.mednote.app.data.database.AppDatabase
import com.mednote.app.data.repository.SeizureRecordRepository

class MedNoteApplication : Application() {
    
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { SeizureRecordRepository(database.seizureRecordDao()) }
}
