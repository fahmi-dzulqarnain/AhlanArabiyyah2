package com.midcores.ahlanarabiyyah.view_model

import androidx.lifecycle.ViewModel
import com.midcores.ahlanarabiyyah.helper.Database.realm
import com.midcores.ahlanarabiyyah.model.WeeklyProgress
import com.midcores.ahlanarabiyyah.model.database.*
import io.realm.kotlin.ext.query

class LearnJourneyViewModel: ViewModel() {
    val dayInWeek = arrayListOf(
        WeeklyProgress("S", true),
        WeeklyProgress("S"),
        WeeklyProgress("R"),
        WeeklyProgress("K"),
        WeeklyProgress("J"),
        WeeklyProgress("S"),
        WeeklyProgress("A", isToday = true)
    )

    fun allTopics(): List<Topic> {
        return realm.query<Topic>().find()
    }
}