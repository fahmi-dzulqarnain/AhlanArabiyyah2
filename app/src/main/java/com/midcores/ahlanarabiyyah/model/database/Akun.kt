package com.midcores.ahlanarabiyyah.model.database

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Akun : RealmObject {
    @PrimaryKey
    var accountID: ObjectId = ObjectId()
    var username: String = ""
    var email: String = ""
    var password: String = ""
    var fullName: String = ""
}