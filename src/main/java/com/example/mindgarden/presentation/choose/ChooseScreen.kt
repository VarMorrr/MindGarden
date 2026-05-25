package com.example.mindgarden.presentation.choose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindgarden.domain.model.FlowerType
import com.example.mindgarden.domain.model.GrowthStage
import com.example.mindgarden.presentation.components.FlowerCanvas

private val DURATIONS = listOf(5, 10, 25, 50)

@Composable
fun ChooseScreen(
    onStart: (FlowerType, String, Int) -> Unit
) {
    var selectedFlower by rememberSaveable { mutableStateOf(FlowerType.ROSE.name) }
    var taskName by rememberSaveable { mutableStateOf("") }
    var selectedDuration by rememberSaveable { mutableIntStateOf(25) }

    val flower = FlowerType.valueOf(selectedFlower)
    val isLandscape = LocalConfiguration.current.orientation ==
            android.content.res.Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        ChooseLandscape(
            selectedFlower = flower,
            taskName = taskName,
            selectedDuration = selectedDuration,
            onFlowerSelect = { selectedFlower = it.name },
            onTaskChange = { taskName = it },
            onDurationSelect = { selectedDuration = it },
            onStart = { onStart(flower, taskName, selectedDuration) }
        )
    } else {
        ChoosePortrait(
            selectedFlower = flower,
            taskName = taskName,
            selectedDuration = selectedDuration,
            onFlowerSelect = { selectedFlower = it.name },
            onTaskChange = { taskName = it },
            onDurationSelect = { selectedDuration = it },
            onStart = { onStart(flower, taskName, selectedDuration) }
        )
    }
}

@Composable
private fun ChoosePortrait(
    selectedFlower: FlowerType,
    taskName: String,
    selectedDuration: Int,
    onFlowerSelect: (FlowerType) -> Unit,
    onTaskChange: (String) -> Unit,
    onDurationSelect: (Int) -> Unit,
    onStart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Column {
            Text("New session", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Medium)
            Text("Choose a flower to grow",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        FlowerGrid(selectedFlower, onFlowerSelect)
        TaskField(taskName, onTaskChange)
        DurationRow(selectedDuration, onDurationSelect)
        StartButton(onStart)
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
private fun ChooseLandscape(
    selectedFlower: FlowerType,
    taskName: String,
    selectedDuration: Int,
    onFlowerSelect: (FlowerType) -> Unit,
    onTaskChange: (String) -> Unit,
    onDurationSelect: (Int) -> Unit,
    onStart: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Choose a flower", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
            FlowerGrid(selectedFlower, onFlowerSelect)
        }

        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Parameters", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
            TaskField(taskName, onTaskChange)
            DurationRow(selectedDuration, onDurationSelect)
            StartButton(onStart)
        }
    }
}

@Composable
private fun FlowerGrid(selectedFlower: FlowerType, onSelect: (FlowerType) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Flower", style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FlowerType.entries.forEach { type ->
                FlowerSelectCard(
                    type = type,
                    isSelected = selectedFlower == type,
                    onClick = { onSelect(type) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TaskField(taskName: String, onTaskChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Task", style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        OutlinedTextField(
            value = taskName,
            onValueChange = onTaskChange,
            placeholder = { Text("What is today`s task?") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
    }
}

@Composable
private fun DurationRow(selectedDuration: Int, onSelect: (Int) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Session time", style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DURATIONS.forEach { dur ->
                FilterChip(
                    selected = selectedDuration == dur,
                    onClick = { onSelect(dur) },
                    label = { Text("$dur min") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFEAF3DE),
                        selectedLabelColor = Color(0xFF27500A)
                    )
                )
            }
        }
        if (selectedDuration <= 10) {
            Surface(shape = RoundedCornerShape(10.dp), color = Color(0xFFEAF3DE)) {
                Text(
                    "Safe mode: the flower will survive if you leave",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF27500A),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun StartButton(onStart: () -> Unit) {
    Button(
        onClick = onStart,
        modifier = Modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B6D11))
    ) {
        Text("Start growing", fontWeight = FontWeight.Medium, fontSize = 15.sp)
    }
}

@Composable
private fun FlowerSelectCard(
    type: FlowerType, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) Color(0xFF3B6D11) else Color.Transparent
    val bgColor = if (isSelected) Color(0xFFEAF3DE) else MaterialTheme.colorScheme.surfaceVariant

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .border(2.dp, borderColor, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        FlowerCanvas(
            flowerType = type,
            stage = GrowthStage.BLOOM,
            modifier = Modifier.size(54.dp, 66.dp)
        )
        Text(
            type.displayName,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            color = if (isSelected) Color(0xFF27500A) else MaterialTheme.colorScheme.onSurface
        )
    }
}
