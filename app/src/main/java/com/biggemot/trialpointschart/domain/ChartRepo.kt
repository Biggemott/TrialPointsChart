package com.biggemot.trialpointschart.domain

import android.graphics.Bitmap
import com.biggemot.trialpointschart.domain.entity.ChartDataEntity
import com.haroldadmin.cnradapter.NetworkResponse

interface ChartRepo {

    companion object {
        const val CHART_FILE_DATE_FORMAT = "yyyyMMdd_HHmmss"
        const val CHART_FILE_PREFIX = "chart"
        const val CHART_FILE_EXT = "jpeg"
    }

    suspend fun getChartData(pointCount: Int): NetworkResponse<ChartDataEntity, Unit>
    suspend fun saveChartToFile(chartBitmap: Bitmap): String?
}