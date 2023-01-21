package com.midcores.ahlanarabiyyah.ui.component

import androidx.annotation.DrawableRes
import com.midcores.ahlanarabiyyah.R

sealed class Screen(
    val title: String,
    val route: String,
    @DrawableRes val iconID: Int
) {
    object Beranda: Screen("Beranda", "beranda", R.drawable.ic_beranda)
    object Kamus: Screen("Kamus", "kamus", R.drawable.ic_kamus)

    object Items {
        val list = listOf(
            Beranda, Kamus
        )
    }
}