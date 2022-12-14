package com.biggemot.trialpointschart.presentation

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biggemot.trialpointschart.domain.ChartInteractor
import com.biggemot.trialpointschart.domain.entity.ChartDataEntity
import com.biggemot.trialpointschart.domain.entity.PointEntity
import com.biggemot.trialpointschart.presentation.model.PointModel
import com.biggemot.trialpointschart.utils.Mapper
import com.biggemot.trialpointschart.utils.SingleLiveEvent
import com.github.mikephil.charting.data.Entry
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val interactor: ChartInteractor,
    private val pointModelMapper: Mapper<PointEntity, PointModel>
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState>
        get() = _uiState

    private val _saveChartResult = SingleLiveEvent<SaveChartResult>()
    val saveChartResult: LiveData<SaveChartResult>
        get() = _saveChartResult

    private var pointCount = 0

    fun setArgs(args: ChartFragmentArgs) {
        val count = args.pointCount
        if (count != pointCount) {
            pointCount = count
            loadPoints()
        }
    }

    private fun loadPoints() {
        _uiState.postValue(UiState.Loading)
        viewModelScope.launch {
            interactor.getChartData(pointCount).process()
        }
    }

    private fun NetworkResponse<ChartDataEntity, Unit>.process() {
        _uiState.postValue(
            when (this) {
                is NetworkResponse.Success -> {
                    UiState.Data(
                        body.points.map(pointModelMapper::map),
                        prepareChartData(body.points)
                    )
                }
                is NetworkResponse.NetworkError -> {
                    UiState.NetworkError
                }
                is NetworkResponse.ServerError -> {
                    UiState.ServerError
                }
                else -> {
                    UiState.UnknownError
                }
            }
        )
    }

    private fun prepareChartData(points: List<PointEntity>): List<Entry> {
        return points.sortedBy { it.x }.map { Entry(it.x, it.y) }
    }

    fun retryClick() {
        loadPoints()
    }

    fun saveChartClick(chartBitmap: Bitmap) {
        saveChartToFile(chartBitmap)
    }

    private fun saveChartToFile(bitmap: Bitmap) {
        viewModelScope.launch {
            val fileName = interactor.saveChartToFile(bitmap)
            _saveChartResult.postValue(
                fileName?.let { SaveChartResult.Success(it) } ?: SaveChartResult.Error
            )
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Data(val tableData: List<PointModel>, val chartData: List<Entry>) : UiState()
        object NetworkError : UiState()
        object ServerError : UiState()
        object UnknownError : UiState()
    }

    sealed class SaveChartResult {
        data class Success(val fileName: String) : SaveChartResult()
        object Error : SaveChartResult()
    }
}