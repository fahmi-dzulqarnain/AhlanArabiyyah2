package com.midcores.ahlanarabiyyah.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp

@Composable
fun MaterialTextField(
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLines: Int = 1,
    leadingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = typography.body1,
    placeholderTextStyle: TextStyle = typography.body2,
    isPasswordField: Boolean = false,
    onTextChange: (String) -> Unit = {}
) {

    val inputValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val passwordEnabled by remember {
        mutableStateOf(isPasswordField)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        TextField(
            value = inputValue.value,
            onValueChange = {
                inputValue.value = it
                onTextChange(it.text)
            },
            label = {
                Text(
                    text = label,
                    style = typography.body2
                )
            },
            placeholder = {
                Text(
                    text = if (placeholder == "") label else placeholder,
                    style = placeholderTextStyle
                )
            },
            modifier = modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            visualTransformation =
                if (passwordEnabled)
                    PasswordVisualTransformation()
                else
                    VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = keyboardType,
            ),
            textStyle = textStyle,
            maxLines = maxLines,
            leadingIcon = leadingIcon
        )
    }
}