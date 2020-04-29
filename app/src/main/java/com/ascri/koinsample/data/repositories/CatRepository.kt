package com.ascri.koinsample.data.repositories

import com.ascri.koinsample.data.dataSources.remote.CatAPI
import com.ascri.koinsample.data.models.Cat
import com.ascri.koinsample.utils.NetResponse

class CatRepository(private val catApi: CatAPI) {
    suspend fun getCatList(count: Int): NetResponse<List<Cat>> {
        return try {
            val result = catApi.getCats(limit = count)
            NetResponse.Success(result)
        } catch (ex: Exception) {
            NetResponse.Error(ex)
        }
    }
}