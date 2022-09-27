package com.biggemot.trialpointschart.data

import com.biggemot.trialpointschart.domain.ChartRepo
import com.biggemot.trialpointschart.domain.entity.ChartDataEntity
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class ChartRepoImpl @Inject constructor(
    private val chartApi: ChartApi
) : ChartRepo {

    override suspend fun getChartData(pointCount: Int): NetworkResponse<ChartDataEntity, Unit> {
        return chartApi.getChartData(pointCount)
    }
}