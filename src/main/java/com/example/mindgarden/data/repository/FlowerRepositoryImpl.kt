package com.example.mindgarden.data.repository

import com.example.mindgarden.data.local.FlowerDao
import com.example.mindgarden.data.local.toEntity
import com.example.mindgarden.domain.model.Flower
import com.example.mindgarden.domain.repository.FlowerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class FlowerRepositoryImpl @Inject constructor(
    private val dao: FlowerDao
) : FlowerRepository {

    override suspend fun saveFlower(flower: Flower): Long =
        dao.insert(flower.toEntity())

    override fun getAllFlowers(): Flow<List<Flower>> =
        dao.getAllFlowers().map { list -> list.map { it.toDomain() } }

    override fun getAliveFlowers(): Flow<List<Flower>> =
        dao.getAliveFlowers().map { list -> list.map { it.toDomain() } }

    override fun getTotalMinutes(): Flow<Int> = dao.getTotalMinutes()

    override fun getHourlyMinutes(): Flow<List<Int>> =
        dao.getMinutesByHour().map { stats ->
            val result = MutableList(24) { 0 }
            stats.forEach { result[it.hourOfDay] = it.total }
            result
        }

    override fun getWeeklyMinutes(): Flow<List<Int>> =
        dao.getMinutesByDayOfWeek().map { stats ->
            val result = MutableList(7) { 0 }
            stats.forEach { stat ->
                val idx = if (stat.dayOfWeek == 0) 6 else stat.dayOfWeek - 1
                result[idx] = stat.total
            }
            result
        }
}
