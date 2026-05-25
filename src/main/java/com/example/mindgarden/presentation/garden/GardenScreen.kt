package com.example.mindgarden.presentation.garden

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindgarden.domain.model.Flower
import com.example.mindgarden.domain.model.GrowthStage
import com.example.mindgarden.presentation.components.FlowerCanvas
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun GardenScreen(viewModel: GardenViewModel = hiltViewModel()) {
    val flowers by viewModel.flowers.collectAsStateWithLifecycle()
    var selectedFlower by remember { mutableStateOf<Flower?>(null) }
    val isLandscape = LocalConfiguration.current.orientation ==
            android.content.res.Configuration.ORIENTATION_LANDSCAPE
    val columns = if (isLandscape) 5 else 3

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Text("My garden", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Medium)
            val aliveCount = flowers.count { it.isAlive }
            Text("$aliveCount ${flowerWord(aliveCount)} grown",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        if (flowers.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("🌱", fontSize = 48.sp)
                    Text("Garden is empty", style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Grow your first flower!", style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(flowers) { flower ->
                    FlowerCard(flower = flower, onClick = { selectedFlower = flower })
                }
            }
        }
    }

    selectedFlower?.let { flower ->
        FlowerDetailDialog(flower = flower, onDismiss = { selectedFlower = null })
    }
}

@Composable
private fun FlowerCard(flower: Flower, onClick: () -> Unit) {
    val bgColor = if (flower.isAlive) MaterialTheme.colorScheme.surfaceVariant
                  else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        FlowerCanvas(
            flowerType = flower.type,
            stage = if (flower.isAlive) GrowthStage.BLOOM else GrowthStage.LEAVES,
            isAlive = flower.isAlive,
            modifier = Modifier.size(58.dp, 64.dp)
        )
        Text(flower.taskName, style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Text(
            if (flower.isAlive) "${flower.durationMinutes} min" else "Wilted",
            style = MaterialTheme.typography.labelSmall, fontSize = 10.sp,
            color = if (flower.isAlive) Color(0xFF3B6D11) else MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun FlowerDetailDialog(flower: Flower, onDismiss: () -> Unit) {
    val dateFormat = SimpleDateFormat("d MMM, HH:mm", Locale("eng"))
    val dateStr = dateFormat.format(Date(flower.completedAt))

    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()) {
                FlowerCanvas(
                    flowerType = flower.type,
                    stage = if (flower.isAlive) GrowthStage.BLOOM else GrowthStage.LEAVES,
                    isAlive = flower.isAlive,
                    modifier = Modifier.size(120.dp, 150.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(flower.type.displayName, style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
                Spacer(Modifier.height(4.dp))
                Text(flower.taskName, style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                Spacer(Modifier.height(14.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    modifier = Modifier.fillMaxWidth()) {
                    Surface(shape = RoundedCornerShape(99.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant) {
                        Text(dateStr,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Surface(shape = RoundedCornerShape(99.dp),
                        color = if (flower.isAlive) Color(0xFFEAF3DE) else Color(0xFFFCEBEB)) {
                        Text(
                            if (flower.isAlive) "${flower.durationMinutes} min" else "Not completed",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Medium,
                            color = if (flower.isAlive) Color(0xFF27500A) else Color(0xFF791F1F)
                        )
                    }
                }
                if (!flower.isAlive) {
                    Spacer(Modifier.height(10.dp))
                    Text("The session was interrupted",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B6D11))
            ) { Text("Close") }
        },
        shape = RoundedCornerShape(20.dp)
    )
}

private fun flowerWord(count: Int): String = when {
    count == 1 -> "flower"
    else -> "flowers"
}
