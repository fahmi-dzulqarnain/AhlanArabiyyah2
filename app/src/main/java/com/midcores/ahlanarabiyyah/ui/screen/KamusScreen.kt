package com.midcores.ahlanarabiyyah.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun KamusScreen(context: Context, modifier: Modifier) {

    Box(modifier = modifier.verticalScroll(rememberScrollState())){
        Column {
            Text("Saya Fahmi")
        }
    }
}
