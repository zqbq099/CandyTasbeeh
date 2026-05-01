package com.candy.tasbeeh.model

import androidx.compose.ui.geometry.Offset

// أنواع الأذكار
val TASBEEH_LIST = listOf(
    "سُبْحَانَ\nاللَّه",
    "الْحَمْدُ\nلِلَّه",
    "لَا إِلَهَ\nإِلَّا اللَّه",
    "اللَّهُ\nأَكْبَرُ",
    "أَسْتَغْفِرُ\nاللَّه",
    "لَا حَوْلَ\nوَلَا قُوَّةَ"
)

// ألوان الأحجار
val GEM_COLORS = listOf(
    GemColor("أحمر", 0xFFEF4444),
    GemColor("أخضر", 0xFF22C55E),
    GemColor("أزرق", 0xFF3B82F6),
    GemColor("ذهبي", 0xFFFBBF24),
    GemColor("بنفسجي", 0xFFA855F7),
    GemColor("لؤلؤي", 0xFFFFFFFF)
)

data class GemColor(val name: String, val hex: Long)

// حجر واحد في اللوحة
data class Tile(
    val row: Int,
    val col: Int,
    val color: GemColor,
    val text: String,
    var offsetX: Float = 0f,
    var offsetY: Float = 0f,
    var scale: Float = 1f,
    var opacity: Float = 1f
)

// حالة اللعبة
enum class GameStatus {
    START,    // شاشة البداية
    PLAYING,  // اللعبة جارية
    GAME_OVER // نهاية اللعبة (اختياري)
}

// جسيم الورد
data class Particle(
    val x: Float,
    val y: Float,
    var vx: Float,
    var vy: Float,
    var life: Float = 1f,
    val char: String,
    val size: Float,
    var rotation: Float = 0f,
    val rotSpeed: Float = 0f
)

// كلمة عائمة
data class FloatingWord(
    val id: String,
    val text: String,
    val x: Float,
    val y: Float,
    val drift: Float,
    val type: FloatingWordType = FloatingWordType.SCORE
)

enum class FloatingWordType { SCORE, COMBO }

// رسائل التميز
val COMBO_MESSAGES = listOf(
    "مَا شَاءَ اللَّه",
    "تَبَارَكَ اللَّه",
    "أَحْسَنْت",
    "تَسْبِيحٌ مُبَارَك"
)
