package com.midcores.ahlanarabiyyah

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.midcores.ahlanarabiyyah.helper.Current
import com.midcores.ahlanarabiyyah.helper.StatusBarHelper.setTransparentStatusBar
import com.midcores.ahlanarabiyyah.model.database.Level
import com.midcores.ahlanarabiyyah.model.enums.StatusTopic
import com.midcores.ahlanarabiyyah.ui.component.TopNavigation
import com.midcores.ahlanarabiyyah.ui.theme.*
import com.midcores.ahlanarabiyyah.view_model.LevelViewModel

class LevelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatusBar(this)

        val topicName = intent.getStringExtra("topicName")

        setContent {
            AhlanArabiyyahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SugarCrystal
                ) {
                    LevelScreen(
                        activity = this,
                        viewModel = LevelViewModel(topicName ?: "")
                    )
                }
            }
        }
    }
}

@Composable
fun LevelScreen(
    activity: LevelActivity,
    viewModel: LevelViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.bottom_end),
            contentDescription = "Background"
        )

        val topicImage: Int = viewModel.selectedTopic?.image!!

        Image(
            painter = painterResource(id = topicImage),
            contentDescription = "Background",
            modifier = Modifier
                .padding(spacing.veryLarge)
                .size(spacing.huge)
        )
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(
            top = spacing.extraLarge,
            start = spacing.large,
            end = spacing.large
        )
    ) {
        TopNavigation(
            activity = activity,
            title = viewModel.selectedTopic?.topicName ?: "Topic"
        )

        val wordsList = viewModel.wordsLevelList()

        Column(
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            viewModel.levelList.forEachIndexed { index, level ->
                LevelCard(
                    activity = activity,
                    levelName = level.levelName,
                    words = wordsList[index],
                    status = enumValueOf(level.status),
                    viewModel = viewModel,
                    level = level
                )
            }
            
            Spacer(modifier = Modifier.defaultMinSize(minWidth = spacing.large))
        }

    }
}

@Composable
fun LevelCard(
    activity: Activity,
    levelName: String = "Pelajaran Pertama",
    words: String = "أنا، أنت، أنتِ، هو، هي، نحن",
    status: StatusTopic = StatusTopic.LOCKED,
    viewModel: LevelViewModel,
    level: Level
) {
    Card(
        elevation = spacing.extraSmall,
        shape = RoundedCornerShape(spacing.medium),
        backgroundColor = if (status == StatusTopic.FINISHED) MintCream else White,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(spacing.medium))
            .alpha(if (status == StatusTopic.LOCKED) 0.7f else 1f)
            .clickable {
                clickLevel(activity, status, viewModel, level)
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = spacing.lessLarge)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(
                        top = spacing.medium,
                        start = spacing.medium,
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_kamus),
                        contentDescription = "Book Level",
                        tint = GentianBlue,
                        modifier = Modifier
                            .size(spacing.extraLarge)
                            .padding(spacing.extraSmall)
                    )

                    Text(
                        text = levelName,
                        style = typography.h6
                    )
                }

                when(status) {
                    StatusTopic.CURRENT -> Spacer(modifier = Modifier)
                    StatusTopic.FINISHED -> {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check),
                            contentDescription = "Checked",
                            tint = IslamicGreen,
                            modifier = Modifier
                                .size(spacing.lessLarge)
                        )
                    }
                    StatusTopic.LOCKED -> {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_password),
                            contentDescription = "Checked",
                            tint = InactiveGentianBlue,
                            modifier = Modifier
                                .size(spacing.lessLarge)
                        )
                    }
                }

            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.defaultMinSize(minWidth = spacing.lessLarge))

                Text(
                    text = words,
                    style = typography.caption,
                    modifier = Modifier.padding(
                        bottom = spacing.large,
                        start = spacing.medium,
                        end = spacing.lessLarge
                    )
                )
            }

        }
    }
}

fun clickLevel(
    activity: Activity,
    status: StatusTopic,
    viewModel: LevelViewModel,
    level: Level
) {
    if (status != StatusTopic.LOCKED) {
        Current.wordList = viewModel.kataList
        Current.level = level

        activity.startActivity(
            Intent(activity, LearnActivity::class.java)
        )
        activity.finish()
    } else {
        Toast.makeText(
            activity,
            "Pelajari yang sebelumnya dulu yuk!",
            Toast.LENGTH_SHORT
        ).show()
    }
}