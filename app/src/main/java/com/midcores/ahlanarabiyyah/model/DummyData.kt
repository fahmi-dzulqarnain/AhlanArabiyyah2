package com.midcores.ahlanarabiyyah.model

import com.midcores.ahlanarabiyyah.R
import com.midcores.ahlanarabiyyah.helper.Database.realm
import com.midcores.ahlanarabiyyah.model.database.Category
import com.midcores.ahlanarabiyyah.model.database.Kata
import com.midcores.ahlanarabiyyah.model.database.Level
import com.midcores.ahlanarabiyyah.model.database.Topic
import com.midcores.ahlanarabiyyah.model.enums.LevelType
import com.midcores.ahlanarabiyyah.model.enums.StatusTopic
import io.realm.kotlin.ext.query

class DummyData {
    init {
        addTopic()
        addLevel()
        addCategories()
        addWords()
    }

    private fun addTopic() {
        val imageList = arrayListOf(
            R.drawable.ic_handshake,
            R.drawable.ic_worker,
            R.drawable.ic_fruit,
            R.drawable.ic_vegetable
        )

        val topicNameList = arrayListOf(
            "Perkenalan",
            "Pekerjaan",
            "Buah-Buahan",
            "Sayuran"
        )

        val topicStatus = arrayListOf(
            StatusTopic.CURRENT,
            StatusTopic.LOCKED,
            StatusTopic.LOCKED,
            StatusTopic.LOCKED
        )

        for (i in 0 until imageList.size) {
            realm.writeBlocking {
                this.copyToRealm(Topic().apply {
                    image = imageList[i]
                    topicName = topicNameList[i]
                    status = topicStatus[i].name
                })
            }
        }
    }

    private fun addLevel() {
        val levelTopics = realm.query<Topic>().find()
        val topicLists = levelTopics.map {
            it.topicName
        }
        val levelList: ArrayList<Level> = arrayListOf()
        val levelNames = arrayListOf(
            "Pelajaran Pertama",
            "Pelajaran Kedua",
//            "Pelajaran Ketiga"
        )
        var topicCounter = -1
        var levelCounter = 0
        val levelSize = levelNames.size

        for (i in 0 until topicLists.size * levelSize) {
            val statusLevel =
                if (i == 0) StatusTopic.CURRENT
                else StatusTopic.LOCKED

            if (i % levelSize == 0) {
                topicCounter++
            }

            levelList.add(
                Level().apply {
                    levelTopic = topicLists[topicCounter]
                    levelName = levelNames[levelCounter]
                    levelType = LevelType.EASY.name
                    status = statusLevel.name
                    finishedAt = ""
                    order = i
                }
            )

            if (levelCounter < levelSize - 1) {
                levelCounter++
            } else {
                levelCounter = 0
            }
        }

        for (level in levelList) {
            realm.writeBlocking {
                this.copyToRealm(level)
            }
        }

        realm.writeBlocking {
            this.copyToRealm(Level().apply {
                levelTopic = "kamus"
                levelName = "kamus"
                levelType = LevelType.KAMUS.name
                status = StatusTopic.FINISHED.name
                finishedAt = ""
                order = 0
            })
        }
    }

    private fun addCategories() {
        val categoryLists = arrayListOf(
            Category().apply { categoryName = "Kata Ganti" },
            Category().apply { categoryName = "Kata Hubung" },
            Category().apply { categoryName = "Kata Benda" },
            Category().apply { categoryName = "Kata Sifat" },
            Category().apply { categoryName = "Kata Kerja" },
            Category().apply { categoryName = "Perkenalan" }
        )

        for (kategori in categoryLists) {
            realm.writeBlocking {
                this.copyToRealm(kategori)
            }
        }
    }

    private fun addWords() {
        val kataGanti = "Kata Ganti"

        val perkenalan = "Perkenalan"
        val pelajaranPertama = "Pelajaran Pertama"
        val pelajaranKedua = "Pelajaran Kedua"
        val pelajaranKetiga = "Pelajaran Ketiga"

        val wordLists = arrayListOf(
            Kata().apply {
                arabicWord = "أَنَا"
                transliteration = "Ana"
                translation = "Saya"
                category = null
                level = null
            },
            Kata().apply {
                arabicWord = "أَنْتَ"
                transliteration = "Ant(a)"
                translation = "Kamu Laki-laki"
                category = null
                level = null
            },
            Kata().apply {
                arabicWord = "أَنْتِ"
                transliteration = "Ant(i)"
                translation = "Kamu Perempuan"
                category = null
                level = null
            },
            Kata().apply {
                arabicWord = "هُوَ"
                transliteration = "Huw(a)"
                translation = "Dia Laki-laki"
                category = null
                level = null
            },
            Kata().apply {
                arabicWord = "هِيَ"
                transliteration = "Hiy(a)"
                translation = "Dia Perempuan"
                category = null
                level = null
            },
            Kata().apply {
                arabicWord = "نَحْنُ"
                transliteration = "Nahn(u)"
                translation = "Kami / Kita"
                category = null
                level = null
            }
        )

        for (kata in wordLists) {
            realm.writeBlocking {
                val saved = copyToRealm(kata)
                val category = query<Category>(
                    "categoryName = '$kataGanti'"
                ).first().find()
                val level = query<Level>(
                    "levelTopic = '$perkenalan' AND levelName = '$pelajaranPertama'"
                ).first().find()

                findLatest(saved)?.category = category
                findLatest(saved)?.level = level
            }
        }

        val levelTwoWordLists = arrayListOf(
            Kata().apply {
                arabicWord = "اِسْمٌ"
                transliteration = "Ism(un)"
                translation = "Nama"
                category = null
                level = null
            },
            Kata().apply {
                arabicWord = "اِسْمِيْ"
                transliteration = "Ismii"
                translation = "Nama Saya"
                category = null
                level = null
            },
            Kata().apply {
                arabicWord = "اِسْمُكَ"
                transliteration = "Ismuk(a)"
                translation = "Nama Kamu"
                category = null
                level = null
            },
            Kata().apply {
                arabicWord = "مَا"
                transliteration = "Maa"
                translation = "Apa"
                category = null
                level = null
            },
            Kata().apply {
                arabicWord = "مَاسْمُك؟َ"
                transliteration = "Masmuk(a)?"
                translation = "Siapa Namamu?"
                category = null
                level = null
            },
            Kata().apply {
                arabicWord = "بِخَيْرٍ"
                transliteration = "Bikhair(in)"
                translation = "Baik"
                category = null
                level = null
            }
        )

        for (kata in levelTwoWordLists) {
            realm.writeBlocking {
                val saved = copyToRealm(kata)
                val category = query<Category>(
                    "categoryName = '$perkenalan'"
                ).first().find()
                val level = query<Level>(
                    "levelTopic = '$perkenalan' AND levelName = '$pelajaranKedua'"
                ).first().find()

                findLatest(saved)?.category = category
                findLatest(saved)?.level = level
            }
        }
    }
}