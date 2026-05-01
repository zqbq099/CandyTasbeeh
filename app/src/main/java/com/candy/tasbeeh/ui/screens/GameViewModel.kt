package com.candy.tasbeeh.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.candy.tasbeeh.model.*
import kotlin.random.Random

class GameViewModel : ViewModel() {

    // حالة اللعبة
    var gameStatus by mutableStateOf(GameStatus.START)
    var score by mutableIntStateOf(0)
    var level by mutableIntStateOf(1)
    var comboMessage by mutableStateOf("")

    // اللوحة
    val rows = 8
    val cols = 6
    var board = Array(rows) { r -> Array(cols) { c -> createRandomTile(r, c) } }

    // السحب
    var draggedTile: Tile? = null
    var dragOffset = Pair(0f, 0f)

    // الجسيمات والكلمات العائمة
    var particles = mutableListOf<Particle>()
    var floatingWords = mutableListOf<FloatingWord>()

    // إنشاء حجر عشوائي
    private fun createRandomTile(r: Int, c: Int): Tile {
        val color = GEM_COLORS.random()
        val text = TASBEEH_LIST.random()
        return Tile(r, c, color, text)
    }

    // بدء اللعبة
    fun startGame() {
        score = 0
        level = 1
        comboMessage = ""
        board = Array(rows) { r -> Array(cols) { c -> createRandomTile(r, c) } }
        particles.clear()
        floatingWords.clear()
        gameStatus = GameStatus.PLAYING
    }

    // العثور على التطابقات
    fun findMatches(): List<Pair<Int, Int>> {
        val matched = mutableSetOf<Pair<Int, Int>>()

        // أفقي
        for (r in 0 until rows) {
            for (c in 0 until cols - 2) {
                val t1 = board[r][c]
                val t2 = board[r][c + 1]
                val t3 = board[r][c + 2]
                if (t1.color.hex == t2.color.hex && t2.color.hex == t3.color.hex) {
                    matched.add(Pair(r, c))
                    matched.add(Pair(r, c + 1))
                    matched.add(Pair(r, c + 2))
                }
            }
        }

        // رأسي
        for (c in 0 until cols) {
            for (r in 0 until rows - 2) {
                val t1 = board[r][c]
                val t2 = board[r + 1][c]
                val t3 = board[r + 2][c]
                if (t1.color.hex == t2.color.hex && t2.color.hex == t3.color.hex) {
                    matched.add(Pair(r, c))
                    matched.add(Pair(r + 1, c))
                    matched.add(Pair(r + 2, c))
                }
            }
        }

        return matched.toList()
    }

    // تبديل حجرين
    fun swapTiles(r1: Int, c1: Int, r2: Int, c2: Int): Boolean {
        // التحقق من أنهما متجاوران
        val isAdjacent = (r1 == r2 && kotlin.math.abs(c1 - c2) == 1) ||
                (c1 == c2 && kotlin.math.abs(r1 - r2) == 1)
        if (!isAdjacent) return false

        // تبديل
        val temp = board[r1][c1]
        board[r1][c1] = board[r2][c2]
        board[r2][c2] = temp

        // تحديث المواقع
        board[r1][c1] = board[r1][c1].copy(row = r1, col = c1)
        board[r2][c2] = board[r2][c2].copy(row = r2, col = c2)

        return true
    }

    // إسقاط الأحجار لملء الفراغات
    fun fillEmptySlots() {
        for (c in 0 until cols) {
            var emptyRow = rows - 1
            for (r in rows - 1 downTo 0) {
                if (board[r][c].opacity > 0.5f) {
                    val tile = board[r][c]
                    board[r][c] = board[emptyRow][c].copy(opacity = 0f) // حجر وهمي
                    board[emptyRow][c] = tile.copy(row = emptyRow, col = c)
                    emptyRow--
                }
            }
            // إنشاء أحجار جديدة في الأعلى
            for (r in emptyRow downTo 0) {
                board[r][c] = createRandomTile(r, c)
            }
        }
    }

    // إزالة التطابقات وحساب النقاط
    fun processMatches(matches: List<Pair<Int, Int>>, comboCount: Int) {
        val earned = matches.size * 10 * comboCount
        score += earned

        // حذف الأحجار المطابقة
        for ((r, c) in matches) {
            board[r][c] = board[r][c].copy(opacity = 0f)
        }

        if (comboCount > 1) {
            comboMessage = COMBO_MESSAGES.random()
        }
    }
}
