package com.midcores.ahlanarabiyyah.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import com.midcores.ahlanarabiyyah.ui.theme.spacing

@Composable
fun DropdownField(
    label: String,
    placeholder: String,
    listOfString: List<String>,
    modifier: Modifier,
    onTextChange: (String) -> Unit = {}
){
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedText by remember {
        mutableStateOf("")
    }
    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    val icon = if (isExpanded)
        Icons.Rounded.KeyboardArrowUp
    else
        Icons.Rounded.KeyboardArrowDown

    Column(
        modifier
            .clickable {
                isExpanded = !isExpanded
            }
    ) {
        TextField(
            value = selectedText,
            onValueChange = {
                selectedText = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = spacing.medium)
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2
                )
            },
            placeholder = {
                Text(
                    text = if (placeholder == "") label else placeholder,
                    style = MaterialTheme.typography.body2
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "contentDescription",
                    modifier = Modifier.clickable {
                        isExpanded = !isExpanded
                    }
                )
            }
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){
                    textFieldSize.width.toDp()
                }),
        ) {
            listOfString.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    onTextChange(selectedText)

                    isExpanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}