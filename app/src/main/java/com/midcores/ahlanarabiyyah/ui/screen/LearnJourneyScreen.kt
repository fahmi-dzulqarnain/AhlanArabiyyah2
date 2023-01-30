package com.midcores.ahlanarabiyyah.ui.screen

import android.content.Intent
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.midcores.ahlanarabiyyah.JourneyActivity
import com.midcores.ahlanarabiyyah.LevelActivity
import com.midcores.ahlanarabiyyah.R
import com.midcores.ahlanarabiyyah.model.database.Topic
import com.midcores.ahlanarabiyyah.model.enums.StatusTopic
import com.midcores.ahlanarabiyyah.ui.component.TopicItem
import com.midcores.ahlanarabiyyah.ui.component.WeeklyProgressItem
import com.midcores.ahlanarabiyyah.ui.theme.SugarCrystal
import com.midcores.ahlanarabiyyah.ui.theme.spacing
import com.midcores.ahlanarabiyyah.view_model.LearnJourneyViewModel

@Composable
fun LearnJourneyScreen(
    modifier: Modifier,
    activity: JourneyActivity,
    viewModel: LearnJourneyViewModel = viewModel()
) {
    Box(modifier = modifier
        .background(SugarCrystal)
        .fillMaxSize()
    ){
        Column {
            Spacer(modifier = Modifier.padding(15.dp))
            Column(
                modifier = modifier
                    .padding(
                        vertical = spacing.lessLarge,
                        horizontal = spacing.large
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Belajar",
                    style = typography.h4,
                    modifier = Modifier.padding(bottom = spacing.lessMedium)
                )
                
                Text(
                    text = "Progres Anda pekan ini",
                    style = typography.body1,
                    modifier = Modifier.padding(bottom = spacing.medium)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = spacing.lessLarge)
                ) {
                    viewModel.dayInWeek.forEach {
                        WeeklyProgressItem(
                            dayInitial = it.dayInitial,
                            checked = it.checked,
                            isToday = it.isToday
                        )
                    }
                }

                Card(
                    elevation = spacing.extraSmall,
                    shape = RoundedCornerShape(spacing.medium)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(spacing.lessLarge)
                        ) {
                            Text(
                                text = "Yuk Belajar!",
                                style = typography.h6
                            )
                            Text(
                                text = "Perkenalan",
                                style = typography.h5
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.learn_illustration),
                            contentDescription = "Learn Illustration",
                            modifier = Modifier.offset(x = 2.dp, y = (-4).dp)
                        )
                    }
                }

                TopicList(activity = activity, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun TopicView(topic: Topic, activity: JourneyActivity) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        TopicItem(
            image = topic.image,
            topicLabel = topic.topicName,
            statusTopic = enumValueOf(topic.status)
        ) {
            clickTopic(
                activity = activity,
                topic =  topic
            )
        }
    }
}

@Composable
fun TopicView(
    firstTopic: Topic,
    secondTopic: Topic,
    activity: JourneyActivity
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        TopicItem(
            image = firstTopic.image,
            topicLabel = firstTopic.topicName,
            statusTopic = enumValueOf(firstTopic.status)
        ) {
            clickTopic(
                activity = activity,
                topic =  firstTopic
            )
        }

        TopicItem(
            image = secondTopic.image,
            topicLabel = secondTopic.topicName,
            statusTopic = enumValueOf(secondTopic.status)
        ) {
            clickTopic(
                activity = activity,
                topic =  secondTopic
            )
        }
    }
}

@Composable
fun TopicList(
    activity: JourneyActivity,
    viewModel: LearnJourneyViewModel
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spacing.large)
    ) {
        var counter = 0
        var index = 0
        val topicList = viewModel.allTopics()

        while (index < topicList.size) {
            if (counter == 0) {
                TopicView(
                    topic = topicList[index],
                    activity = activity
                )
            } else {
                if (index + 1 == topicList.size) {
                    TopicView(
                        topic = topicList[index],
                        activity = activity
                    )
                } else {
                    TopicView(
                        firstTopic = topicList[index],
                        secondTopic = topicList[index + 1],
                        activity = activity
                    )

                    index++
                }
            }

            index++

            if (counter == 1) {
                counter = 0
            } else {
                counter++
            }
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing.huge)
        )
    }
}

fun clickTopic(
    activity: JourneyActivity,
    topic: Topic
) {
    if (topic.status != StatusTopic.LOCKED.name) {
        val intent = Intent(activity, LevelActivity::class.java)
        intent.putExtra("topicName", topic.topicName)

        activity.startActivity(intent)
    } else {
        Toast.makeText(
            activity,
            "Pelajari yang sebelumnya dulu yuk!",
            LENGTH_SHORT
        ).show()
    }
}
