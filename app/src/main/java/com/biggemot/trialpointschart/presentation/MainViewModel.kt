package com.biggemot.trialpointschart.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.biggemot.trialpointschart.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>(UiState.Empty)
    val uiState: LiveData<UiState>
        get() = _uiState

    private val _navEvent = SingleLiveEvent<NavDirections>()
    val navEvent: LiveData<NavDirections>
        get() = _navEvent

    fun pointCountTextChanged(text: String?) {
        Timber.d("pointCountTextChanged - %s", text)
        if (text.isNullOrBlank()) {
            _uiState.value = UiState.Empty
        } else {
            val count = parsePointCount(text)
            if (count < 1 || count > 1000) {
                _uiState.value = UiState.Error
            } else {
                _uiState.value = UiState.Default
            }
        }
    }

    fun startButtonClick(pointCountText: String?) {
        Timber.d("startButtonClick - %s", pointCountText)

        pointCountText.takeUnless { it.isNullOrBlank() }?.let { parsePointCount(it) }?.let {
            _navEvent.value = MainFragmentDirections.actionMainFragmentToChartFragment(it)
        } ?: run { _uiState.value = UiState.Error }
    }

    private fun parsePointCount(text: String) = text.trim().toIntOrNull() ?: -1

    sealed class UiState {
        object Empty : UiState()
        object Default : UiState()
        object Error : UiState()
    }
}