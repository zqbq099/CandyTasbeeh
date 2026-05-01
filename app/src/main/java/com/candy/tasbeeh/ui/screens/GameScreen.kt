package com.candy.tasbeeh.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.candy.tasbeeh.model.GameStatus

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (viewModel.gameStatus) {
            GameStatus.START -> {
                // شاشة البداية
                StartScreen(onStartClick = { viewModel.startGame() })
            }
            GameStatus.PLAYING -> {
                // شاشة اللعبة
                PlayingScreen(viewModel = viewModel)
            }
            GameStatus.GAME_OVER -> {
                // شاشة النهاية (اختياري)
                StartScreen(onStartClick = { viewModel.startGame() })
            }
        }
    }
}

@Composable
fun StartScreen(onStartClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "تَسْبِيحٌ مُبَارَك",
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "اجمع الأحجار المتشابهة لتزهر الشاشة بذكر الله",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "ابدأ الذكر",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

@Composable
fun PlayingScreen(viewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // رأس الصفحة (النقاط)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "النقاط",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
                Text(
                    text = viewModel.score.toString(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // لوحة اللعبة
        BoardCanvas(
            board = viewModel.board,
            rows = viewModel.rows,
            cols = viewModel.cols,
            particles = viewModel.particles,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
