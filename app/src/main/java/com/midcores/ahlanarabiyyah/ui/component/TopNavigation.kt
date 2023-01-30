package com.midcores.ahlanarabiyyah.ui.component

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.midcores.ahlanarabiyyah.R
import com.midcores.ahlanarabiyyah.ui.theme.spacing

@Composable
fun TopNavigation(
    activity: Activity,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = spacing.medium),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(bottom = spacing.lessLarge)
    ) {
        Card(
            elevation = spacing.default,
            shape = RoundedCornerShape(spacing.lessMedium),
            modifier = Modifier
                .clip(RoundedCornerShape(spacing.lessMedium))
                .clickable {
                    activity.finish()
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.chevron_left_solid),
                contentDescription = "Back Button"
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.h4
        )
    }
}