package com.midcores.ahlanarabiyyah.helper

import com.midcores.ahlanarabiyyah.model.database.Kata
import com.midcores.ahlanarabiyyah.model.database.Level

object Current {
    var level: Level? = null
    var wordList: List<Kata> = arrayListOf()
    var answer: String = ""
}