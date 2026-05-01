package com.candy.tasbeeh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.candy.tasbeeh.ui.theme.CandyTasbeehTheme
import com.candy.tasbeeh.ui.screens.GameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CandyTasbeehTheme {
                GameScreen()
            }
        }
    }
}
