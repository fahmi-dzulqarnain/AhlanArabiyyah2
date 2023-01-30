package com.midcores.ahlanarabiyyah.ui.component

import androidx.annotation.DrawableRes
import com.midcores.ahlanarabiyyah.R

sealed class Screen(
    val title: String,
    val route: String,
    @DrawableRes val iconID: Int
) {
    object Beranda: Screen("Belajar", "beranda", R.drawable.ic_belajar)
    object Kamus: Screen("Kamus Saya", "kamus", R.drawable.ic_kamus)

    object Items {
        val list = listOf(
            Beranda, Kamus
        )
    }
}