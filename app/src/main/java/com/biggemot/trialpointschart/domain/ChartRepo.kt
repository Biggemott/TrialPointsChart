package com.biggemot.trialpointschart.domain

import com.biggemot.trialpointschart.domain.entity.ChartDataEntity
import com.haroldadmin.cnradapter.NetworkResponse

interface ChartRepo {
    suspend fun getChartData(pointCount: Int): NetworkResponse<ChartDataEntity, Unit>
}