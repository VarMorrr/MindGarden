package com.example.mindgarden.presentation.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindgarden.domain.model.Flower
import com.example.mindgarden.domain.model.FlowerType
import com.example.mindgarden.domain.model.GrowthStage
import com.example.mindgarden.domain.usecase.SaveFlowerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

enum class TimerState { IDLE, RUNNING, PAUSED, FINISHED }

data class TimerUiState(
    val flowerType: FlowerType = FlowerType.ROSE,
    val taskName: String = "",
    val totalSeconds: Int = 25 * 60,
    val remainSeconds: Int = 25 * 60,
    val timerState: TimerState = TimerState.IDLE,
    val stage: GrowthStage = GrowthStage.SEED,
    val isProtected: Boolean = false,
    val showLeaveDialog: Boolean = false
) {
    val progress: Float
        get() = if (totalSeconds == 0) 0f else 1f - remainSeconds.toFloat() / totalSeconds

    val formattedTime: String
        get() {
            val m = remainSeconds / 60
            val s = remainSeconds % 60
            return "%02d:%02d".format(m, s)
        }
}

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val saveFlowerUseCase: SaveFlowerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun initSession(type: FlowerType, task: String, durationMinutes: Int) {
        timerJob?.cancel()
        val totalSecs = durationMinutes * 60
        _uiState.value = TimerUiState(
            flowerType = type,
            taskName = task,
            totalSeconds = totalSecs,
            remainSeconds = totalSecs,
            timerState = TimerState.IDLE,
            stage = GrowthStage.SEED,
            isProtected = durationMinutes <= 10
        )
    }

    fun startOrResume() {
        _uiState.update { it.copy(timerState = TimerState.RUNNING) }
        timerJob = viewModelScope.launch {
            while (_uiState.value.remainSeconds > 0 &&
                _uiState.value.timerState == TimerState.RUNNING) {
                delay(1000L)
                _uiState.update { state ->
                    val newRemain = state.remainSeconds - 1
                    val newStage = GrowthStage.fromProgress(
                        1f - newRemain.toFloat() / state.totalSeconds
                    )
                    state.copy(remainSeconds = newRemain, stage = newStage)
                }
            }
            if (_uiState.value.remainSeconds == 0) {
                finishSession()
            }
        }
    }

    fun pause() {
        timerJob?.cancel()
        _uiState.update { it.copy(timerState = TimerState.PAUSED) }
    }


    fun toggleTimer() {
        when (_uiState.value.timerState) {
            TimerState.IDLE, TimerState.PAUSED -> startOrResume()
            TimerState.RUNNING -> pause()
            TimerState.FINISHED -> {}
        }
    }

    fun onLeaveAttempt(): Boolean {
        val state = _uiState.value
        if (state.timerState != TimerState.RUNNING && state.timerState != TimerState.PAUSED) {
            return true
        }
        return if (state.isProtected) {
            saveDeadFlower()
            true
        } else {
            _uiState.update { it.copy(showLeaveDialog = true) }
            false
        }
    }

    fun stayIn() {
        _uiState.update { it.copy(showLeaveDialog = false) }
    }


    fun confirmLeave() {
        timerJob?.cancel()
        _uiState.update { it.copy(showLeaveDialog = false) }
        saveDeadFlower()
        resetSession()
    }


    private fun finishSession() {
        timerJob?.cancel()
        _uiState.update { it.copy(timerState = TimerState.FINISHED, stage = GrowthStage.BLOOM) }
        viewModelScope.launch {
            saveFlowerUseCase(buildFlower(alive = true))
        }
    }

    private fun saveDeadFlower() {
        val state = _uiState.value
        val elapsedMinutes = (state.totalSeconds - state.remainSeconds) / 60
        if (elapsedMinutes < 1) return // Меньше минуты — не сохраняем
        viewModelScope.launch {
            saveFlowerUseCase(buildFlower(alive = false))
        }
    }

    private fun buildFlower(alive: Boolean): Flower {
        val state = _uiState.value
        val elapsed = (state.totalSeconds - state.remainSeconds) / 60
        val cal = Calendar.getInstance()
        return Flower(
            type = state.flowerType,
            taskName = state.taskName.ifBlank { "Без названия" },
            durationMinutes = if (alive) state.totalSeconds / 60 else elapsed,
            completedAt = System.currentTimeMillis(),
            isAlive = alive,
            hourOfDay = cal.get(Calendar.HOUR_OF_DAY)
        )
    }

    private fun resetSession() {
        _uiState.update {
            it.copy(
                timerState = TimerState.IDLE,
                remainSeconds = it.totalSeconds,
                stage = GrowthStage.SEED
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
