package com.biggemot.trialpointschart.data

import com.biggemot.trialpointschart.domain.entity.ChartDataEntity
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ChartApi {

    @GET("api/test/points")
    suspend fun getChartData(@Query("count") count: Int): NetworkResponse<ChartDataEntity, Unit>
}