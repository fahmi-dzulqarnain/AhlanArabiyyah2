package com.midcores.ahlanarabiyyah.model.database

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Category: RealmObject {
    @PrimaryKey
    var categoryID: ObjectId = ObjectId()
    var categoryName: String = "kamus"
}