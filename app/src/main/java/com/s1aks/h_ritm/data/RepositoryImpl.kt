package com.s1aks.h_ritm.data

import com.s1aks.h_ritm.data.db.HeartDataBase
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.domain.Repository

class RepositoryImpl : Repository {
    override suspend fun getData(id: Int): HeartData =
        HeartDataBase.getInstance().historyDao().getData(id).toHeartData()

    override suspend fun getAllData(): List<HeartData> =
        HeartDataBase.getInstance().historyDao().getAll().toListHeartData()

    override suspend fun insertData(data: HeartData) =
        HeartDataBase.getInstance().historyDao().insert(data.toHeartDataEntity())

    override suspend fun updateData(data: HeartData) =
        HeartDataBase.getInstance().historyDao().update(data.toHeartDataEntity())

    override suspend fun deleteData(data: HeartData) =
        HeartDataBase.getInstance().historyDao().delete(data.toHeartDataEntity())
}
