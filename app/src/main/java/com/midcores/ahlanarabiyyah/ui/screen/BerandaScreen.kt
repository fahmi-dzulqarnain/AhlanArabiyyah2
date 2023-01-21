package com.midcores.ahlanarabiyyah.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.midcores.ahlanarabiyyah.ui.theme.SugarCrystal
import com.midcores.ahlanarabiyyah.ui.theme.spacing

@Composable
fun BerandaScreen(context: Context, modifier: Modifier) {

    Box(modifier = modifier
        .background(SugarCrystal)
        .fillMaxSize()
    ){
        Column {
            Spacer(modifier = Modifier.padding(15.dp))
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = modifier
                    .padding(vertical = 15.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = "Beranda",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(
                        top = spacing.lessLarge,
                        start = spacing.lessLarge)
                )
            }
        }
    }
}
