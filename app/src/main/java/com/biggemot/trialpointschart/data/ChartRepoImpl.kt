package com.biggemot.trialpointschart.data

import android.graphics.Bitmap
import android.os.Environment
import com.biggemot.trialpointschart.di.IoDispatcher
import com.biggemot.trialpointschart.domain.ChartRepo
import com.biggemot.trialpointschart.domain.entity.ChartDataEntity
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ChartRepoImpl @Inject constructor(
    private val chartApi: ChartApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ChartRepo {

    override suspend fun getChartData(pointCount: Int): NetworkResponse<ChartDataEntity, Unit> {
        return chartApi.getChartData(pointCount)
    }

    override suspend fun saveChartToFile(chartBitmap: Bitmap): String? {
        return withContext(ioDispatcher) {
            try {
                val timestamp =
                    SimpleDateFormat(ChartRepo.CHART_FILE_DATE_FORMAT, Locale.ROOT).format(
                        Date()
                    )
                val fileName = "${ChartRepo.CHART_FILE_PREFIX}_$timestamp"

                val directory: File =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val file = File(directory, "$fileName.${ChartRepo.CHART_FILE_EXT}")

                val fileOutputStream = FileOutputStream(file)

                chartBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.close()

                return@withContext fileName
            } catch (e: Exception) {
                Timber.e(e)

                return@withContext null
            }
        }
    }
}