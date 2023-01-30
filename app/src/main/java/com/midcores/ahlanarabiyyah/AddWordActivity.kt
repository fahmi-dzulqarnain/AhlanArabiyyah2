package com.midcores.ahlanarabiyyah

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.midcores.ahlanarabiyyah.helper.StatusBarHelper.setTransparentStatusBar
import com.midcores.ahlanarabiyyah.ui.component.DropdownField
import com.midcores.ahlanarabiyyah.ui.component.MaterialTextField
import com.midcores.ahlanarabiyyah.ui.component.RoundedButton
import com.midcores.ahlanarabiyyah.ui.component.TopNavigation
import com.midcores.ahlanarabiyyah.ui.theme.AhlanArabiyyahTheme
import com.midcores.ahlanarabiyyah.ui.theme.SugarCrystal
import com.midcores.ahlanarabiyyah.ui.theme.spacing
import com.midcores.ahlanarabiyyah.view_model.KamusViewModel

class AddWordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTransparentStatusBar(this)
        setContent {
            AhlanArabiyyahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AddWordScreen(this)
                }
            }
        }
    }
}

@Composable
fun AddWordScreen(
    activity: AddWordActivity,
    modifier: Modifier = Modifier,
    viewModel: KamusViewModel = viewModel()
) {
    Column(
        modifier = modifier
            .background(SugarCrystal)
            .fillMaxSize()
            .padding(
                top = spacing.extraLarge
            )
    ) {
        TopNavigation(
            activity = activity,
            title = "Tambah Kata",
            modifier = Modifier.padding(
                horizontal = spacing.large
            )
        )
        
        MaterialTextField(
            label = "Kosa Kata Bahasa Arab",
            textStyle = typography.body1.copy(
                textDirection = TextDirection.Rtl
            ),
            placeholder = "المفردات",
            placeholderTextStyle = typography.caption.copy(
                textDirection = TextDirection.Rtl
            ),
            modifier = Modifier.padding(
                horizontal = spacing.medium
            ),
            onTextChange = {
                viewModel.arabicWordString = it
            }
        )

        MaterialTextField(
            label = "Arti Bahasa Indonesia",
            placeholder = "Arti / Makna dalam Bahasa Indonesia",
            modifier = Modifier.padding(
                horizontal = spacing.medium
            ),
            onTextChange = {
                viewModel.translationString = it
            }
        )

        DropdownField(
            label = "Kategori",
            placeholder = "Pilih Kategori",
            listOfString = viewModel.categoryList(),
            modifier = Modifier.padding(
                horizontal = spacing.medium
            ),
            onTextChange = {
                viewModel.categoryString = it
                Toast.makeText(activity, "Selected ${viewModel.categoryString}", LENGTH_SHORT).show()
            }
        )

        RoundedButton(
            label = "Tambah",
            height = spacing.veryLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.lessLarge)
                .padding(top = spacing.large)
        ) {
            viewModel.addKataToKamus()
            activity.finish()
        }
    }
}