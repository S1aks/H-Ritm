package com.s1aks.h_ritm.data.db

import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.s1aks.h_ritm.App
import com.s1aks.h_ritm.data.entities.HeartData

@Database(
    entities = [HeartData::class],
    version = 1,
    exportSchema = false
)
abstract class HeartDataBase : RoomDatabase() {
    abstract fun historyDao(): HeartDataDao

    companion object {
        private var instance: HeartDataBase? = null
        fun getInstance(): HeartDataBase =
            instance ?: databaseBuilder(App.appContext, HeartDataBase::class.java, "HeartDB")
                .fallbackToDestructiveMigration()
                .build()
    }
}