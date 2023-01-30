package com.midcores.ahlanarabiyyah.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midcores.ahlanarabiyyah.R
import com.midcores.ahlanarabiyyah.ui.theme.*

@Composable
fun WeeklyProgressItem(
    strokeColour: Color = EnglishDaisy,
    checkedStrokeColour: Color = GentianBlue,
    background: Color = SugarCrystal,
    checkedBackground: Color = PurpleAnemone,
    size: Dp = spacing.quiteLarge,
    strokeWidth: Dp = spacing.veryTiny,
    dayInitial: String = "",
    checked: Boolean = false,
    isToday: Boolean = false
) {
    val todayBackground = CreamyApricot

    Box(modifier = Modifier
        .background(
            color =
            if (checked) checkedBackground
            else if (isToday) todayBackground
            else background,
            shape = RoundedCornerShape(spacing.small)
        )
        .width(size)
        .height(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            Modifier
                .width(spacing.veryLarge)
                .height(spacing.veryLarge)
        ) {
            if (checked) {
                drawRoundRect(
                    color = checkedStrokeColour,
                    topLeft = Offset(0f, 0f),
                    size = Size(size.toPx(), size.toPx()),
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                    style = Stroke(width = strokeWidth.toPx())
                )
            } else {
                drawRoundRect(
                    color = strokeColour,
                    topLeft = Offset(0f, 0f),
                    size = Size(size.toPx(), size.toPx()),
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                    style = Stroke(width = strokeWidth.toPx())
                )
            }
        }

        if (checked) {
            Icon(
                painter = painterResource(R.drawable.ic_check),
                contentDescription = "Check",
                tint = White
            )
        } else {
            Text(
                text = dayInitial,
                style =
                if (isToday)
                    TextStyle(
                        fontFamily = fonts,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                else
                    typography.body2
            )
        }
    }
}

@Preview
@Composable
private fun DemoPreview() {
    WeeklyProgressItem(dayInitial = "S")
}