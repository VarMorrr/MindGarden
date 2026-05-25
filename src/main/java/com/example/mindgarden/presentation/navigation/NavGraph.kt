package com.example.mindgarden.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mindgarden.domain.model.FlowerType
import com.example.mindgarden.presentation.choose.ChooseScreen
import com.example.mindgarden.presentation.garden.GardenScreen
import com.example.mindgarden.presentation.stats.StatsScreen
import com.example.mindgarden.presentation.timer.TimerScreen
import com.example.mindgarden.presentation.timer.TimerViewModel

sealed class Screen(val route: String) {
    object Choose : Screen("choose")
    object Timer : Screen("timer")
    object Garden : Screen("garden")
    object Stats : Screen("stats")
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

private val bottomNavItems = listOf(
    BottomNavItem(Screen.Choose, "Grow", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(Screen.Garden, "Garden", Icons.Filled.Star, Icons.Outlined.Star),
    BottomNavItem(Screen.Stats, "Statistics", Icons.Filled.List, Icons.Filled.List)
)

@Composable
fun MindGardenNavigation() {
    val navController = rememberNavController()
    val timerViewModel: TimerViewModel = hiltViewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute != Screen.Timer.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = navBackStackEntry?.destination
                            ?.hierarchy?.any { it.route == item.screen.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Choose.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Choose.route) {
                ChooseScreen(
                    onStart = { type: FlowerType, task: String, duration: Int ->
                        timerViewModel.initSession(type, task, duration)
                        navController.navigate(Screen.Timer.route)
                    }
                )
            }
            composable(Screen.Timer.route) {
                TimerScreen(
                    onSessionFinished = {
                        navController.navigate(Screen.Garden.route) {
                            popUpTo(Screen.Choose.route)
                        }
                    },
                    onLeaveConfirmed = {
                        navController.navigate(Screen.Choose.route) {
                            popUpTo(Screen.Choose.route) { inclusive = true }
                        }
                    },
                    viewModel = timerViewModel
                )
            }
            composable(Screen.Garden.route) { GardenScreen() }
            composable(Screen.Stats.route) { StatsScreen() }
        }
    }
}
