package com.midcores.ahlanarabiyyah.view_model

import androidx.lifecycle.ViewModel
import com.midcores.ahlanarabiyyah.helper.Current
import com.midcores.ahlanarabiyyah.helper.Database.realm
import com.midcores.ahlanarabiyyah.model.database.Kata
import com.midcores.ahlanarabiyyah.model.database.Level
import com.midcores.ahlanarabiyyah.model.database.Topic
import io.realm.kotlin.ext.query

class LevelViewModel(topicName: String): ViewModel() {
    val selectedTopic = realm.query<Topic>(
        "topicName = '$topicName'"
    ).first().find()

    val levelList = realm.query<Level>(
        "levelTopic = '$topicName'"
    ).find()

    var kataList: ArrayList<Kata> = arrayListOf()

    fun wordsLevelList(): List<String> {
        val listOfKataKata = arrayListOf<String>()

        levelList.forEach {
            var finalKata = ""
            val kataKata = realm.query<Kata>(
                "level.levelID == $0", it.levelID
            ).find()

            kataKata.forEach { kata ->
                finalKata += if (finalKata.isEmpty())
                    kata.arabicWord
                else
                    "، ${kata.arabicWord}"

                kataList.add(kata)
            }

            listOfKataKata.add(finalKata)
        }

        return listOfKataKata
    }
}