package com.example.mindgarden.presentation.garden

import androidx.lifecycle.ViewModel
import com.example.mindgarden.domain.model.Flower
import com.example.mindgarden.domain.usecase.GetAllFlowersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GardenViewModel @Inject constructor(
    getAllFlowersUseCase: GetAllFlowersUseCase
) : ViewModel() {
    val flowers: StateFlow<List<Flower>> = getAllFlowersUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}
