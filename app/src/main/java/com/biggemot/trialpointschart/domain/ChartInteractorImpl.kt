package com.biggemot.trialpointschart.domain

import com.biggemot.trialpointschart.domain.entity.ChartDataEntity
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class ChartInteractorImpl @Inject constructor(
    private val repo: ChartRepo
) : ChartInteractor {

    override suspend fun getChartData(pointCount: Int): NetworkResponse<ChartDataEntity, Unit> {
        return repo.getChartData(pointCount)
    }
}