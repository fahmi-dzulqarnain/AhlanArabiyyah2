package com.midcores.ahlanarabiyyah.model.database

import com.midcores.ahlanarabiyyah.model.enums.LevelType
import com.midcores.ahlanarabiyyah.model.enums.StatusTopic
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Level: RealmObject {
    @PrimaryKey
    var levelID: ObjectId = ObjectId()
    var levelTopic: String = ""
    var levelName: String = ""
    var levelType: String = LevelType.KAMUS.name
    var status: String = StatusTopic.LOCKED.name
    var finishedAt: String = ""
    var order: Int = 0
}