package com.midcores.ahlanarabiyyah.model

data class WeeklyProgress (
    val dayInitial: String,
    val checked: Boolean = false,
    val isToday: Boolean = false
)