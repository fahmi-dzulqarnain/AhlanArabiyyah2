package com.midcores.ahlanarabiyyah.model.database

import com.midcores.ahlanarabiyyah.model.enums.StatusTopic
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Topic: RealmObject {
    @PrimaryKey
    var topicID: ObjectId = ObjectId()
    var image: Int = 0
    var topicName: String = ""
    var status: String = StatusTopic.LOCKED.name
}