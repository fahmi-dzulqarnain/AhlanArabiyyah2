package com.midcores.ahlanarabiyyah.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.midcores.ahlanarabiyyah.model.enums.StatusTopic
import com.midcores.ahlanarabiyyah.ui.theme.*

@Composable
fun TopicItem(
    image: Int,
    topicLabel: String,
    size: Dp = spacing.lessHuge,
    checkedStrokeColour: Color = GentianBlue,
    strokeWidth: Dp = spacing.extraSmall,
    statusTopic: StatusTopic = StatusTopic.LOCKED,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .width(size + spacing.lessLarge)
            .height(size + spacing.lessLarge)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        GentianBlue.copy(alpha = 0.26f),
                        Color.Transparent,
                    )
                )
            )
        )

        Box(modifier = Modifier
            .background(
                color =
                when (statusTopic) {
                    StatusTopic.CURRENT -> White
                    StatusTopic.FINISHED -> MintCream
                    StatusTopic.LOCKED -> White.copy(0.7f)
                },
                shape = CircleShape
            )
            .width(size)
            .height(size)
            .clip(CircleShape)
            .clickable {
                onClick()
            },
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                Modifier
                    .width(size)
                    .height(size)
            ) {
                if (statusTopic == StatusTopic.CURRENT) {
                    drawCircle(
                        color = checkedStrokeColour,
                        style = Stroke(width = strokeWidth.toPx())
                    )
                } else if (statusTopic == StatusTopic.FINISHED) {
                    drawCircle(
                        color = CreamyApricot,
                        style = Stroke(width = strokeWidth.toPx())
                    )
                }
            }

            Image(
                painter = painterResource(image),
                contentDescription = topicLabel,
                alpha = if (statusTopic == StatusTopic.LOCKED) 0.7f else 1f
            )
        }
    }
}