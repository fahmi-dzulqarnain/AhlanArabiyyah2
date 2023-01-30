package com.midcores.ahlanarabiyyah.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.midcores.ahlanarabiyyah.ui.theme.SugarCrystal
import com.midcores.ahlanarabiyyah.ui.theme.spacing

@Composable
fun CustomDialog(
    title: String,
    description: String,
    isOpen: Boolean = false,
    onDismiss: () -> Unit
) {
    val shouldShowDialog = rememberUpdatedState(isOpen)

    if (shouldShowDialog.value) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                Modifier
                    .background(
                        color = SugarCrystal,
                        shape = RoundedCornerShape(spacing.medium)
                    )
                    .padding(
                        horizontal = spacing.large,
                        vertical = spacing.medium
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = typography.h5,
                        modifier = Modifier.padding(vertical = spacing.medium)
                    )

                    Text(
                        text = description,
                        style = typography.body2,
                        modifier = Modifier.padding(bottom = spacing.medium)
                    )
                }
            }
        }
    }
}