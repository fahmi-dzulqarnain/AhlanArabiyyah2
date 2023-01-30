package com.midcores.ahlanarabiyyah.view_model

import androidx.lifecycle.ViewModel
import com.midcores.ahlanarabiyyah.helper.Database.realm
import com.midcores.ahlanarabiyyah.model.database.Akun
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class RegisterViewModel: ViewModel() {
    var usernameData = ""
    var emailData = ""
    var fullNameData = ""
    var passwordData = ""
    var dialogTitle = ""
    var dialogDescription = ""

    fun addAkun(): Boolean {
        val isNotAvailable = checkAvailability()

        return if (isNotAvailable) {
            realm.writeBlocking {
                this.copyToRealm(Akun().apply {
                    username = usernameData
                    email = emailData
                    fullName = fullNameData
                    password = passwordData
                })
            }

            true
        } else {
            false
        }
    }

    private fun checkAvailability(): Boolean {
        val checkUsername: RealmResults<Akun> = realm.query<Akun>(
            "username = '$usernameData'"
        ).find()

        if (!checkUsername.isEmpty()) {
            dialogTitle = "Username Terdaftar"
            dialogDescription = "Mohon untuk menggunakan username yang lain"

            return false
        }

        val checkEmail: RealmResults<Akun> = realm.query<Akun>(
            "email = '$emailData'"
        ).find()

        if (!checkEmail.isEmpty()) {
            dialogTitle = "Email Terdaftar"
            dialogDescription = "Mohon untuk menggunakan email yang lain"

            return false
        }

        return true
    }
}