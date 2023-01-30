package com.midcores.ahlanarabiyyah.view_model

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.midcores.ahlanarabiyyah.helper.Database.realm
import com.midcores.ahlanarabiyyah.model.DummyData
import com.midcores.ahlanarabiyyah.model.database.Akun
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

class LoginViewModel(private val sharedPreferences: SharedPreferences): ViewModel() {
    var username: String = ""
    var password: String = ""
    var dialogTitle: String = ""
    var dialogDescription: String = ""

    fun checkIfLoggedIn(): Boolean = sharedPreferences.getBoolean("isLoggedIn", false)

    fun authenticateLogin(): Boolean {
        checkIfFieldIsEmpty()

        val checkUsername = realm.query<Akun>(
            "username = '$username'"
        ).find()

        if (checkUsername.isEmpty()) {
            dialogTitle = "Autentikasi Gagal"
            dialogDescription = "Username tidak ditemukan"

            return false
        }

        val authenticated = realm.query<Akun>(
            "username = '$username' AND password = '$password'"
        ).find()

        if (!authenticated.isEmpty()) {
            val userLoggedIn = sharedPreferences.edit()
            userLoggedIn.putBoolean("isLoggedIn", true)
            userLoggedIn.apply()

            DummyData()

            return true
        } else {
            dialogTitle = "Autentikasi Gagal"
            dialogDescription = "Password yang Anda masukkan salah"
        }

        return false
    }

    private fun checkIfFieldIsEmpty(): Boolean {
        if (username.isEmpty() && password.isEmpty()) {
            dialogTitle = "Ada Field Kosong!"
            dialogDescription = "Mohon isi semua field"
        } else if (username.isEmpty()) {
            dialogTitle = "Ada Field Kosong!"
            dialogDescription = "Mohon isi field username"
        } else if (password.isEmpty()) {
            dialogTitle = "Ada Field Kosong!"
            dialogDescription = "Mohon isi field password"
        } else {
            return true
        }

        return false
    }
}