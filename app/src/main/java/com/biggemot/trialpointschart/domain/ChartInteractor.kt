package com.biggemot.trialpointschart.domain

import android.graphics.Bitmap
import com.biggemot.trialpointschart.domain.entity.ChartDataEntity
import com.haroldadmin.cnradapter.NetworkResponse

interface ChartInteractor {
    suspend fun getChartData(pointCount: Int): NetworkResponse<ChartDataEntity, Unit>

    suspend fun saveChartToFile(chartBitmap: Bitmap): String?
}