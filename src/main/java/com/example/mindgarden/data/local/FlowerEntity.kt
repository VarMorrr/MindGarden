package com.example.mindgarden.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mindgarden.domain.model.Flower
import com.example.mindgarden.domain.model.FlowerType

@Entity(tableName = "flowers")
data class FlowerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String,
    val taskName: String,
    val durationMinutes: Int,
    val completedAt: Long,
    val isAlive: Boolean,
    val hourOfDay: Int
) {

    fun toDomain(): Flower = Flower(
        id = id,
        type = FlowerType.valueOf(type),
        taskName = taskName,
        durationMinutes = durationMinutes,
        completedAt = completedAt,
        isAlive = isAlive,
        hourOfDay = hourOfDay
    )
}


fun Flower.toEntity(): FlowerEntity = FlowerEntity(
    id = id,
    type = type.name,
    taskName = taskName,
    durationMinutes = durationMinutes,
    completedAt = completedAt,
    isAlive = isAlive,
    hourOfDay = hourOfDay
)
