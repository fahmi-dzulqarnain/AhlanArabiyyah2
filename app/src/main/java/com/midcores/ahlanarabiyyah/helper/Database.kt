package com.midcores.ahlanarabiyyah.helper

import com.midcores.ahlanarabiyyah.model.database.*
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object Database {
    private val databaseConfig = RealmConfiguration
        .Builder(setOf(
            Akun::class,
            Topic::class,
            Level::class,
            Category::class,
            Kata::class,
            Kalimat::class
        ))
        .build()

    val realm = Realm.open(databaseConfig)
}