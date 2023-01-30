package com.midcores.ahlanarabiyyah.model

import com.midcores.ahlanarabiyyah.model.enums.GameType

data class GameUIState (
    val score: Int = 0,
    val currentProgress: Float = 0F,
    val currentCount: Int = 0,
    val currentQuestion: Question,
    val gameType: GameType = GameType.MEMORIZE,
    val isGameOver: Boolean = false
)