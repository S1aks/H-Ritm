package com.s1aks.h_ritm.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HeartDataDao {

    @Query("SELECT * FROM HeartDataEntity ORDER BY dateTime DESC")
    suspend fun getAll(): List<HeartDataEntity>

    @Query("SELECT * FROM HeartDataEntity WHERE id LIKE :id")
    suspend fun getData(id: Int): HeartDataEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: HeartDataEntity)

    @Update
    suspend fun update(data: HeartDataEntity)

    @Delete
    suspend fun delete(data: HeartDataEntity)
}