package com.example.mindgarden

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.mindgarden.presentation.navigation.MindGardenNavigation
import com.example.mindgarden.ui.theme.MindGardenTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindGardenTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MindGardenNavigation()
                }
            }
        }
    }
}
