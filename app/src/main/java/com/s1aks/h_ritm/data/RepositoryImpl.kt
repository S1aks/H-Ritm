package com.s1aks.h_ritm.data

import com.s1aks.h_ritm.data.db.HeartDataBase
import com.s1aks.h_ritm.data.entities.HeartData
import com.s1aks.h_ritm.data.entities.PrefData
import com.s1aks.h_ritm.domain.Repository

class RepositoryImpl : Repository {
    override suspend fun getData(id: Int): HeartData =
        HeartDataBase.getInstance().historyDao().getData(id)

    override suspend fun getAllData(): List<HeartData> =
        HeartDataBase.getInstance().historyDao().getAll()

    override suspend fun insertData(data: HeartData) =
        HeartDataBase.getInstance().historyDao().insert(data)

    override suspend fun updateData(data: HeartData) =
        HeartDataBase.getInstance().historyDao().update(data)

    override suspend fun deleteData(id: Int) =
        HeartDataBase.getInstance().historyDao().delete(id)

    override suspend fun getPrefData(): PrefData? = null

    override suspend fun savePrefData(data: PrefData) {}
}
