package com.example.mindgarden.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlowerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(flower: FlowerEntity): Long

    @Query("SELECT * FROM flowers ORDER BY completedAt DESC")
    fun getAllFlowers(): Flow<List<FlowerEntity>>

    @Query("SELECT * FROM flowers WHERE isAlive = 1 ORDER BY completedAt DESC")
    fun getAliveFlowers(): Flow<List<FlowerEntity>>

    @Query("SELECT COUNT(*) FROM flowers WHERE isAlive = 1")
    fun getAliveCount(): Flow<Int>

    @Query("SELECT COALESCE(SUM(durationMinutes), 0) FROM flowers WHERE isAlive = 1")
    fun getTotalMinutes(): Flow<Int>


    @Query("""
        SELECT hourOfDay, COALESCE(SUM(durationMinutes), 0) as total
        FROM flowers
        WHERE isAlive = 1
        GROUP BY hourOfDay
        ORDER BY hourOfDay
    """)
    fun getMinutesByHour(): Flow<List<HourStat>>


    @Query("""
        SELECT 
            CAST(strftime('%w', datetime(completedAt / 1000, 'unixepoch')) AS INTEGER) as dayOfWeek,
            COALESCE(SUM(durationMinutes), 0) as total
        FROM flowers
        WHERE isAlive = 1
        GROUP BY dayOfWeek
    """)
    fun getMinutesByDayOfWeek(): Flow<List<DayStat>>
}

data class HourStat(val hourOfDay: Int, val total: Int)

data class DayStat(val dayOfWeek: Int, val total: Int)
