package com.example.mindgarden.domain.model

import androidx.compose.ui.graphics.Color

enum class FlowerType(
    val displayName: String,
    val petalColor: Color,
    val centerColor: Color,
    val lightColor: Color,
    val stemColor: Color = Color(0xFF4A7C3F)
) {
    ROSE(
        displayName = "Rose",
        petalColor = Color(0xFFE24B4A),
        centerColor = Color(0xFFA32D2D),
        lightColor = Color(0xFFF09595)
    ),
    TULIP(
        displayName = "Tulip",
        petalColor = Color(0xFFD85A30),
        centerColor = Color(0xFF712B13),
        lightColor = Color(0xFFF0997B)
    ),
    SUNFLOWER(
        displayName = "Sunflower",
        petalColor = Color(0xFFEF9F27),
        centerColor = Color(0xFF412402),
        lightColor = Color(0xFFFAC775)
    ),
    LAVENDER(
        displayName = "Lavender",
        petalColor = Color(0xFF7F77DD),
        centerColor = Color(0xFF3C3489),
        lightColor = Color(0xFFAFA9EC)
    )
}

enum class GrowthStage(val label: String, val progressThreshold: Float) {
    SEED("Seed", 0f),
    SPROUT("Sprout", 0.01f),
    LEAVES("Leaves", 0.25f),
    BUD("Bud", 0.50f),
    OPENING("Opening", 0.75f),
    BLOOM("Bloom", 1.0f);

    companion object {
        fun fromProgress(progress: Float): GrowthStage = when {
            progress <= 0f -> SEED
            progress < 0.25f -> SPROUT
            progress < 0.50f -> LEAVES
            progress < 0.75f -> BUD
            progress < 1.00f -> OPENING
            else -> BLOOM
        }
    }
}


data class Flower(
    val id: Long = 0,
    val type: FlowerType,
    val taskName: String,
    val durationMinutes: Int,
    val completedAt: Long,
    val isAlive: Boolean,
    val hourOfDay: Int
)


data class FlowerStats(
    val totalFlowers: Int,
    val totalMinutes: Int,
    val bestPeriod: String,
    val bestPeriodMinutes: Int,
    val weeklyMinutes: List<Int>,
    val hourlyMinutes: List<Int>
)
