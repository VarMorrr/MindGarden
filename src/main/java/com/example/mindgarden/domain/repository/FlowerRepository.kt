package com.example.mindgarden.domain.repository

import com.example.mindgarden.domain.model.Flower
import kotlinx.coroutines.flow.Flow

interface FlowerRepository {

    suspend fun saveFlower(flower: Flower): Long
    fun getAllFlowers(): Flow<List<Flower>>
    fun getAliveFlowers(): Flow<List<Flower>>
    fun getTotalMinutes(): Flow<Int>
    fun getHourlyMinutes(): Flow<List<Int>>
    fun getWeeklyMinutes(): Flow<List<Int>>
}
