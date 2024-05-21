package com.s1aks.h_ritm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.s1aks.h_ritm.data.entities.HeartData

@Dao
interface HeartDataDao {

    @Query("SELECT * FROM HeartData ORDER BY dateTime DESC")
    suspend fun getAll(): List<HeartData>

    @Query("SELECT * FROM HeartData WHERE id LIKE :id")
    suspend fun getData(id: Int): HeartData

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: HeartData)

    @Update
    suspend fun update(data: HeartData)

    @Query("DELETE FROM HeartData WHERE id = :id")
    suspend fun delete(id: Int)
}