package com.midcores.ahlanarabiyyah.ui.screen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.midcores.ahlanarabiyyah.AddWordActivity
import com.midcores.ahlanarabiyyah.ui.component.LottieLoader
import com.midcores.ahlanarabiyyah.ui.component.RoundedButton
import com.midcores.ahlanarabiyyah.ui.theme.SugarCrystal
import com.midcores.ahlanarabiyyah.ui.theme.arabicFonts
import com.midcores.ahlanarabiyyah.ui.theme.spacing
import com.midcores.ahlanarabiyyah.view_model.KamusViewModel
import com.midcores.ahlanarabiyyah.R

@Composable
fun KamusScreen(
    activity: Activity,
    modifier: Modifier,
    viewModel: KamusViewModel = viewModel()
) {
    Box(modifier = modifier
        .background(SugarCrystal)
        .fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ){
        RoundedButton(
            label = "Tambah Kata",
            height = spacing.veryLarge,
            cornerRadius = spacing.lessLarge,
            modifier = Modifier
                .padding(spacing.large)
                .padding(bottom = spacing.extraHuge)
        ) {
            activity.startActivity(
                Intent(activity, AddWordActivity::class.java)
            )
        }

        Column(modifier = Modifier
            .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier
                .padding(15.dp)
            )
            Column(
                modifier = modifier
                    .padding(
                        vertical = spacing.lessLarge,
                        horizontal = spacing.large
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Kamus Saya",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(bottom = spacing.medium)
                )

                KamusList(viewModel = viewModel)
                
                Spacer(modifier = Modifier.defaultMinSize(minHeight = spacing.large))
            }
        }

    }
}

@Composable
fun KamusList(
    viewModel: KamusViewModel
) {
    val kamusList = viewModel.kamusList()

    if (kamusList.isEmpty()) {
        NoDataKamus()
    } else {
        kamusList.forEach {
            KamusItem(
                category = it.category?.categoryName!!,
                arabicWord = it.arabicWord,
                translation = it.translation
            ) { }
        }
    }
}

@Composable
fun NoDataKamus() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = spacing.huge)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LottieLoader(
                rawAsset = R.raw.searching_for_word,
                modifier = Modifier.height(spacing.doubledHuge)
            )
            Text(
                text = "Belum ada kamus, tekan tombol di kiri bawah untuk menambahkan",
                style = typography.body2.copy(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = spacing.lessLarge)
            )
        }
    }
}

@Composable
fun KamusItem(
    category: String,
    arabicWord: String,
    translation: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(spacing.medium),
        modifier = Modifier
            .padding(top = spacing.medium)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(spacing.lessLarge)
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.h6.copy(
                        fontSize = 12.sp
                    )
                )
                Text(
                    text = translation,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 17.sp
                    )
                )
            }

            Text(
                text = arabicWord,
                style = TextStyle(
                    fontFamily = arabicFonts,
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp
                ),
                modifier = Modifier.padding(spacing.medium)
            )
        }
    }
}
