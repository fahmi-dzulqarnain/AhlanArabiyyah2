package com.midcores.ahlanarabiyyah.model.database

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.text.SimpleDateFormat
import java.util.*

class Kata: RealmObject {
    @PrimaryKey
    var wordID: ObjectId = ObjectId()
    var arabicWord: String = ""
    var transliteration: String = ""
    var translation: String = ""
    var image: Int? = null
    var category: Category? = null
    var level: Level? = null
    var createdAt: String = getCurrentDate()

    private fun getCurrentDate(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        return formatter.format(time)
    }
}