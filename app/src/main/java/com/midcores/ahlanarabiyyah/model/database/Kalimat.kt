package com.midcores.ahlanarabiyyah.model.database

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Kalimat: RealmObject {
    @PrimaryKey
    var kalimatID: ObjectId = ObjectId()
    var correctSentence: String = ""
    var wordsAnswer: List<String> = arrayListOf()
    var level: Level? = null
}