package com.biggemot.trialpointschart.domain

import android.graphics.Bitmap
import com.biggemot.trialpointschart.domain.entity.ChartDataEntity
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class ChartInteractorImpl @Inject constructor(
    private val repo: ChartRepo
) : ChartInteractor {

    override suspend fun getChartData(pointCount: Int): NetworkResponse<ChartDataEntity, Unit> {
        return repo.getChartData(pointCount)
    }

    override suspend fun saveChartToFile(chartBitmap: Bitmap): String? {
        return repo.saveChartToFile(chartBitmap)
    }
}