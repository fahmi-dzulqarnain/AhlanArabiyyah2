package com.midcores.ahlanarabiyyah.model

import com.midcores.ahlanarabiyyah.model.enums.GameType

data class Question(
    val arabicWord: String,
    val transliteration: String,
    val answer: String,
    val options: List<String>,
    val gameType: GameType
)
