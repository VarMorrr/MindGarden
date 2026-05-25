package com.example.mindgarden.presentation.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.Calendar

private val DAY_LABELS = listOf("Mon", "Tu", "Wed", "Thu", "Fri", "Sat", "Sun")

@Composable
fun StatsScreen(viewModel: StatsViewModel = hiltViewModel()) {
    val stats by viewModel.stats.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Text(
                "Statistics",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
        }

        if (stats == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF639922))
            }
            return
        }

        val s = stats!!

        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCard(
                value = s.totalFlowers.toString(),
                label = "Flower",
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                value = formatMinutes(s.totalMinutes),
                label = "Total focus time",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(12.dp))

        if (s.bestPeriodMinutes > 0) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFEAF3DE),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF97C459))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        "THE MOST PRODUCTIVE TIME",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF3B6D11),
                        letterSpacing = 0.5.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        s.bestPeriod,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF27500A)
                    )
                    Text(
                        "${formatMinutes(s.bestPeriodMinutes)} суммарно",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF3B6D11)
                    )
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        ChartSection(title = "Activity this week") {
            val todayIdx = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 5) % 7
            BarChart(
                values = s.weeklyMinutes,
                labels = DAY_LABELS,
                highlightIndex = todayIdx,
                barColor = Color(0xFF97C459),
                highlightColor = Color(0xFF3B6D11)
            )
        }

        Spacer(Modifier.height(16.dp))

        ChartSection(title = "By hours of the day") {
            val hourValues = s.hourlyMinutes.subList(6, 23) // 6:00–22:00
            val hourLabels = listOf("6", "9", "12", "15", "18", "21")
            BarChart(
                values = hourValues,
                labels = null,
                extraLabels = hourLabels,
                barColor = Color(0xFF97C459),
                highlightColor = Color(0xFF3B6D11)
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun MetricCard(value: String, label: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ChartSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(10.dp))
        content()
    }
}

@Composable
private fun BarChart(
    values: List<Int>,
    labels: List<String>?,
    highlightIndex: Int = -1,
    extraLabels: List<String>? = null,
    barColor: Color,
    highlightColor: Color
) {
    val maxVal = values.maxOrNull()?.takeIf { it > 0 } ?: 1
    val chartHeight = 80.dp

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartHeight),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            values.forEachIndexed { i, v ->
                val fraction = v.toFloat() / maxVal
                val barH = (chartHeight.value * fraction.coerceAtLeast(0.03f)).dp
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(barH)
                        .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                        .background(if (i == highlightIndex) highlightColor else barColor)
                )
            }
        }

        Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp)

        if (labels != null) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                labels.forEach { lbl ->
                    Text(
                        text = lbl,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontSize = 10.sp
                    )
                }
            }
        }

        if (extraLabels != null) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                extraLabels.forEach { lbl ->
                    Text(
                        text = lbl,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

private fun formatMinutes(minutes: Int): String {
    val h = minutes / 60
    val m = minutes % 60
    return if (h > 0) "${h}h ${m}m" else "${m}m"
}
