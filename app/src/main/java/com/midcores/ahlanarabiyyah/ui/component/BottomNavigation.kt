package com.midcores.ahlanarabiyyah.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.midcores.ahlanarabiyyah.ui.theme.GentianBlue
import com.midcores.ahlanarabiyyah.ui.theme.InactiveGentianBlue
import com.midcores.ahlanarabiyyah.ui.theme.spacing

@Composable
fun BottomNavigation(
    currentScreenRoute: String,
    modifier: Modifier,
    onItemSelected: (Screen) -> Unit
) {
    val bottomMenuShape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)
    val items = Screen.Items.list
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .shadow(spacing.large, bottomMenuShape)
            .background(MaterialTheme.colors.surface, bottomMenuShape)
            .padding(
                bottom = spacing.large,
                top = spacing.lessLarge,
                start = spacing.medium,
                end = spacing.medium)
    ){
        items.forEach { item ->
            BottomNavigationItems(item = item, isSelected = item.route == currentScreenRoute) {
                onItemSelected(item)
            }
        }
    }
}

@Composable
fun BottomNavigationItems(
    item: Screen,
    isSelected: Boolean,
    activeIconColor: Color = GentianBlue,
    inactiveIconColor: Color = InactiveGentianBlue,
    onClick:() -> Unit
) {
    val contentIconColor = if (isSelected) activeIconColor else inactiveIconColor
    val density = LocalDensity.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(spacing.medium))
        ) {
            Icon(
                painter = painterResource(id = item.iconID),
                contentDescription = item.title,
                tint = contentIconColor,
                modifier = Modifier
                    .size(spacing.extraLarge)
                    .padding(3.dp)
            )
        }

        Row{
            AnimatedVisibility(
                visible = isSelected,
                enter = slideInVertically {
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    initialAlpha = 0.3f
                )
            ) {
                Text(
                    text = item.title,
                    style = typography.caption
                )
            }
        }
    }
}