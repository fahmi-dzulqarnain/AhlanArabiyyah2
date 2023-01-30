package com.midcores.ahlanarabiyyah.view_model

import androidx.lifecycle.ViewModel
import com.midcores.ahlanarabiyyah.helper.Database.realm
import com.midcores.ahlanarabiyyah.model.database.Category
import com.midcores.ahlanarabiyyah.model.database.Kata
import com.midcores.ahlanarabiyyah.model.database.Level
import com.midcores.ahlanarabiyyah.model.enums.LevelType
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort

class KamusViewModel: ViewModel() {
    var arabicWordString: String = ""
    var translationString: String = ""
    var categoryString: String = ""

    fun kamusList(): List<Kata> {
        return realm.query<Kata>(
            "level.levelType = '${LevelType.KAMUS}'"
        ).sort("translation", Sort.DESCENDING).find()
    }

    fun categoryList(): List<String> {
        return realm.query<Category>().find().map {
            it.categoryName
        }
    }

    fun addKataToKamus() {
        realm.writeBlocking {
            val kata = copyToRealm(Kata().apply {
                arabicWord = arabicWordString
                translation = translationString
            })

            val category = query<Category>(
                "categoryName = '$categoryString'"
            ).first().find()

            val level = query<Level>(
                "levelType = '${LevelType.KAMUS}'"
            ).first().find()

            kata.category = category
            kata.level = level
        }
    }
}