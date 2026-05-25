package com.example.mindgarden.domain.usecase

import com.example.mindgarden.domain.model.Flower
import com.example.mindgarden.domain.model.FlowerStats
import com.example.mindgarden.domain.repository.FlowerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class SaveFlowerUseCase @Inject constructor(
    private val repository: FlowerRepository
) {
    suspend operator fun invoke(flower: Flower): Long =
        repository.saveFlower(flower)
}

class GetAllFlowersUseCase @Inject constructor(
    private val repository: FlowerRepository
) {
    operator fun invoke(): Flow<List<Flower>> = repository.getAllFlowers()
}

class GetFlowerStatsUseCase @Inject constructor(
    private val repository: FlowerRepository
) {
    operator fun invoke(): Flow<FlowerStats> = combine(
        repository.getAliveFlowers(),
        repository.getTotalMinutes(),
        repository.getHourlyMinutes(),
        repository.getWeeklyMinutes()
    ) { flowers, totalMinutes, hourly, weekly ->

        val periods = listOf(
            "Morning (6–10h)" to (6..9).sumOf { hourly.getOrElse(it) { 0 } },
            "Noon (10–14h)" to (10..13).sumOf { hourly.getOrElse(it) { 0 } },
            "Afternoon (14–18h)" to (14..17).sumOf { hourly.getOrElse(it) { 0 } },
            "Evening (18–22h)" to (18..21).sumOf { hourly.getOrElse(it) { 0 } },
        )
        val best = periods.maxByOrNull { it.second } ?: ("–" to 0)

        FlowerStats(
            totalFlowers = flowers.size,
            totalMinutes = totalMinutes,
            bestPeriod = best.first,
            bestPeriodMinutes = best.second,
            weeklyMinutes = weekly,
            hourlyMinutes = hourly
        )
    }
}
