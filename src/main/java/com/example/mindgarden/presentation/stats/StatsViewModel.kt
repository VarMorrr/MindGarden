package com.example.mindgarden.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindgarden.domain.model.FlowerStats
import com.example.mindgarden.domain.usecase.GetFlowerStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    getFlowerStatsUseCase: GetFlowerStatsUseCase
) : ViewModel() {

    val stats: StateFlow<FlowerStats?> = getFlowerStatsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
}
