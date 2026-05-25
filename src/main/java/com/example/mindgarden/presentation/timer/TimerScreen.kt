package com.example.mindgarden.presentation.timer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindgarden.domain.model.GrowthStage
import com.example.mindgarden.presentation.components.FlowerCanvas

@Composable
fun TimerScreen(
    onSessionFinished: () -> Unit,
    onLeaveConfirmed: () -> Unit,
    viewModel: TimerViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val isLandscape = LocalConfiguration.current.orientation ==
            android.content.res.Configuration.ORIENTATION_LANDSCAPE

    BackHandler {
        val canLeave = viewModel.onLeaveAttempt()
        if (canLeave) onLeaveConfirmed()
    }

    if (state.showLeaveDialog) {
        LeaveWarningDialog(
            onStay = viewModel::stayIn,
            onLeave = { viewModel.confirmLeave(); onLeaveConfirmed() }
        )
    }

    if (state.timerState == TimerState.FINISHED) {
        LaunchedEffectFinish(onSessionFinished)
    }

    if (isLandscape) {
        TimerLandscape(state, viewModel, onLeaveConfirmed)
    } else {
        TimerPortrait(state, viewModel, onLeaveConfirmed)
    }
}

@Composable
private fun TimerPortrait(
    state: TimerUiState,
    viewModel: TimerViewModel,
    onLeaveConfirmed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Text(state.flowerType.displayName,
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Medium)
            Text(state.taskName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
        }

        StageIndicator(state.stage, Modifier.padding(horizontal = 20.dp))

        if (state.isProtected) {
            ProtectedBadge(Modifier.padding(horizontal = 20.dp, vertical = 6.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFEAF3DE)),
            contentAlignment = Alignment.Center
        ) {
            FlowerCanvas(
                flowerType = state.flowerType,
                stage = state.stage,
                modifier = Modifier.size(150.dp, 185.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularTimer(state.progress, state.formattedTime, state.timerState)
        }

        Spacer(Modifier.weight(1f))

        ActionButtons(
            state = state,
            onToggle = viewModel::toggleTimer,
            onLeave = {
                val canLeave = viewModel.onLeaveAttempt()
                if (canLeave) onLeaveConfirmed()
            },
            onGoToGarden = onLeaveConfirmed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        )
    }
}

@Composable
private fun TimerLandscape(
    state: TimerUiState,
    viewModel: TimerViewModel,
    onLeaveConfirmed: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(state.flowerType.displayName,
                style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFEAF3DE)),
                contentAlignment = Alignment.Center
            ) {
                FlowerCanvas(
                    flowerType = state.flowerType,
                    stage = state.stage,
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                )
            }
            Spacer(Modifier.height(6.dp))
            StageIndicator(state.stage, Modifier)
        }

        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isProtected) ProtectedBadge(Modifier.padding(bottom = 6.dp))

            Text(state.taskName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1, textAlign = TextAlign.Center)

            Spacer(Modifier.height(8.dp))
            CircularTimer(state.progress, state.formattedTime, state.timerState)
            Spacer(Modifier.height(12.dp))

            ActionButtons(
                state = state,
                onToggle = viewModel::toggleTimer,
                onLeave = {
                    val canLeave = viewModel.onLeaveAttempt()
                    if (canLeave) onLeaveConfirmed()
                },
                onGoToGarden = onLeaveConfirmed,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun StageIndicator(currentStage: GrowthStage, modifier: Modifier) {
    val stages = GrowthStage.entries.filter { it != GrowthStage.SEED }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        stages.forEach { stage ->
            Box(
                modifier = Modifier
                    .size(9.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            stage < currentStage -> Color(0xFF97C459)
                            stage == currentStage -> Color(0xFF3B6D11)
                            else -> Color(0xFFD3D1C7)
                        }
                    )
            )
        }
        Spacer(Modifier.width(6.dp))
        Text(currentStage.label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ProtectedBadge(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            "Safe mode: flower won`t wilt if you leave",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun CircularTimer(progress: Float, timeText: String, timerState: TimerState) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(150.dp)) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(150.dp),
            color = Color(0xFF639922),
            strokeWidth = 8.dp,
            strokeCap = StrokeCap.Round,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(timeText, fontSize = 32.sp, fontWeight = FontWeight.Medium, letterSpacing = (-1).sp)
            Text(
                when (timerState) {
                    TimerState.IDLE -> "Click to start"
                    TimerState.RUNNING -> "Growing..."
                    TimerState.PAUSED -> "Paused"
                    TimerState.FINISHED -> "Done!"
                },
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ActionButtons(
    state: TimerUiState,
    onToggle: () -> Unit,
    onLeave: () -> Unit,
    onGoToGarden: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(
            onClick = onToggle,
            modifier = Modifier.weight(1f).height(52.dp),
            shape = RoundedCornerShape(14.dp),
            enabled = state.timerState != TimerState.FINISHED,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (state.timerState == TimerState.RUNNING)
                    Color(0xFF993C1D) else Color(0xFF3B6D11)
            )
        ) {
            Icon(
                imageVector = if (state.timerState == TimerState.RUNNING)
                    Icons.Default.Clear else Icons.Default.PlayArrow,
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text(
                when (state.timerState) {
                    TimerState.IDLE -> "Start"
                    TimerState.RUNNING -> "Pause"
                    TimerState.PAUSED -> "Continue"
                    TimerState.FINISHED -> "Completed"
                },
                fontWeight = FontWeight.Medium
            )
        }

        if (state.timerState != TimerState.FINISHED) {
            OutlinedButton(
                onClick = onLeave,
                modifier = Modifier.height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) { Text("Leave") }
        } else {
            Button(
                onClick = onGoToGarden,
                modifier = Modifier.height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF639922))
            ) { Text("To the garden") }
        }
    }
}

@Composable
private fun LeaveWarningDialog(onStay: () -> Unit, onLeave: () -> Unit) {
    AlertDialog(
        onDismissRequest = onStay,
        icon = { Text("🥀", fontSize = 38.sp) },
        title = {
            Text("The flower will wilt!", fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        },
        text = {
            Text("If you leave now, the flower will wilt and timer will reset",
                textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
        },
        confirmButton = {
            Button(onClick = onStay, modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B6D11))
            ) { Text("Stay and continue") }
        },
        dismissButton = {
            TextButton(onClick = onLeave, modifier = Modifier.fillMaxWidth()) {
                Text("If you leave the flower will wilt", color = MaterialTheme.colorScheme.error)
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
private fun LaunchedEffectFinish(onFinished: () -> Unit) {
    androidx.compose.runtime.LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        onFinished()
    }
}
