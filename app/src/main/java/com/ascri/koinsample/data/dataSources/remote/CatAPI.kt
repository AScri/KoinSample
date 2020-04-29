package com.ascri.koinsample.data.dataSources.remote

import com.ascri.koinsample.data.models.Cat
import retrofit2.http.GET
import retrofit2.http.Query

interface CatAPI {
    @GET("images/search")
    suspend fun getCats(@Query("limit") limit: Int): List<Cat>
}