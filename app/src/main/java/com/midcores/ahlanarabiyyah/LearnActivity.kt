package com.midcores.ahlanarabiyyah

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.midcores.ahlanarabiyyah.helper.Current
import com.midcores.ahlanarabiyyah.helper.StatusBarHelper.setTransparentStatusBar
import com.midcores.ahlanarabiyyah.model.Question
import com.midcores.ahlanarabiyyah.model.enums.GameType
import com.midcores.ahlanarabiyyah.ui.component.LottieLoader
import com.midcores.ahlanarabiyyah.ui.component.RoundedButton
import com.midcores.ahlanarabiyyah.ui.theme.*
import com.midcores.ahlanarabiyyah.view_model.LearnViewModel

class LearnActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTransparentStatusBar(this)
        setContent {
            AhlanArabiyyahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SugarCrystal
                ) {
                    LearnScreen(this)
                }
            }
        }
    }
}

@Composable
fun LearnScreen(
    activity: Activity,
    viewModel: LearnViewModel = viewModel()
) {
    val gameUiState by viewModel.uiState.collectAsState()

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SugarCrystal)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CreamyApricot)
                    .padding(
                        top = spacing.large,
                        start = spacing.lessMedium,
                        end = spacing.lessMedium
                    )
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_xmark),
                        contentDescription = "Close",
                        modifier = Modifier
                            .padding(spacing.medium)
                            .clip(RoundedCornerShape(spacing.small))
                            .clickable {
                                activity.finish()
                            }
                    )
                }

                Spacer(modifier = Modifier.defaultMinSize(minWidth = spacing.small))

                Text(
                    text =
                    when (gameUiState.gameType) {
                        GameType.FINISHED -> "Pembelajaran Selesai!"
                        else -> "${gameUiState.currentCount + 1} of ${viewModel.numberOfQuestion}"
                    },
                    style = typography.body1
                )

                Spacer(modifier = Modifier
                    .defaultMinSize(minWidth = spacing.veryLarge)
                    .padding(spacing.medium))
            }

            val progress = rememberUpdatedState(gameUiState.currentProgress)
            val progressAnimDuration = 1500
            val progressAnimation by animateFloatAsState(
                targetValue = progress.value,
                animationSpec = tween(
                    durationMillis = progressAnimDuration,
                    easing = FastOutSlowInEasing
                )
            )

            LinearProgressIndicator(
                color = PurpleAnemone,
                backgroundColor = White,
                progress = progressAnimation,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(spacing.small)
            )

            when (gameUiState.gameType) {
                GameType.MEMORIZE -> {
                    MemorizeScreen(gameUiState.currentQuestion)
                }
                GameType.EXERCISE -> {
                    ExerciseScreen(gameUiState.currentQuestion)
                }
                else -> {
                    FinishedScreen(gameUiState.score)
                }
            }
        }

        RoundedButton(
            label =
            when (gameUiState.gameType) {
                GameType.MEMORIZE -> "Saya Hafal"
                GameType.FINISHED -> "Selesai"
                else -> "Periksa"
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.large)
                .padding(bottom = spacing.medium)
            ,
            height = spacing.veryLarge
        ) {
            if (gameUiState.gameType == GameType.FINISHED) {
                viewModel.finishLevel()
                activity.finish()
            } else {
                val isMemorize = gameUiState.gameType == GameType.MEMORIZE
                var score = gameUiState.score

                if (!isMemorize &&
                    Current.answer == gameUiState.currentQuestion.answer
                ) {
                    score = gameUiState.score.inc()
                } else if (isMemorize) {
                    score = gameUiState.score.inc()
                }

                viewModel.updateGameState(score)
            }
        }
    }
}

@Composable
fun MemorizeScreen(
    question: Question
) {
    Card(
        shape = RoundedCornerShape(spacing.medium),
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.lessLarge)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(spacing.medium)
        ) {
            Text(
                text = question.arabicWord,
                style = TextStyle(
                    fontFamily = arabicFonts,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = question.transliteration,
                style = typography.body1.copy(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(bottom = spacing.medium)
            )
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.veryLarge)
    ) {
        Text(
            text = question.answer,
            style = typography.h3.copy(
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun ExerciseScreen(
    question: Question
) {
    val options = question.options

    Card(
        shape = RoundedCornerShape(spacing.medium),
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.lessLarge)
            .padding(bottom = spacing.default)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(spacing.medium)
        ) {
            Text(
                text = question.arabicWord,
                style = TextStyle(
                    fontFamily = arabicFonts,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = question.transliteration,
                style = typography.body1,
                modifier = Modifier.padding(bottom = spacing.medium)
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.lessLarge)
            .padding(top = spacing.default)
    ) {
        var clickedIndex by remember {
            mutableStateOf(-1)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing.medium),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        ) {
            AnswerOption(
                answer = options[0],
                onClick = {
                    clickedIndex = 0
                },
                modifier = Modifier.weight(1f),
                clickedIndex = clickedIndex,
                index = 0
            )

            AnswerOption(
                answer = options[1],
                onClick = {
                    clickedIndex = 1
                },
                modifier = Modifier.weight(1f),
                clickedIndex = clickedIndex,
                index = 1
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing.medium),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        ) {
            AnswerOption(
                answer = options[2],
                onClick = {
                    clickedIndex = 2
                },
                modifier = Modifier.weight(1f),
                clickedIndex = clickedIndex,
                index = 2
            )

            AnswerOption(
                answer = options[3],
                onClick = {
                    clickedIndex = 3
                },
                modifier = Modifier.weight(1f),
                clickedIndex = clickedIndex,
                index = 3
            )
        }
    }
}

@Composable
fun AnswerOption(
    answer: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    clickedIndex: Int,
    index: Int
) {
    val clicked = rememberUpdatedState(clickedIndex)

    Card(
        shape = RoundedCornerShape(spacing.medium),
        elevation = spacing.default,
        backgroundColor = if (clicked.value == index) CreamyApricot else White,
        modifier = modifier
            .clip(RoundedCornerShape(spacing.medium))
            .height(spacing.veryHuge)
            .fillMaxWidth()
            .clickable {
                Current.answer = answer
                onClick()
            }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = answer,
                style = TextStyle(
                    fontFamily = fonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(spacing.medium)
            )
        }
    }
}

@Composable
fun FinishedScreen(
    score: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.large)
    ) {
        var title = "Selamat!"
        val currentLevel = Current.level
        var message = "Anda telah menyelesaikan ${currentLevel?.levelName} pada topik ${currentLevel?.levelTopic} "

        if (score >= 85) {
            LottieLoader(rawAsset = R.raw.trophy)
        } else if (score > 70) {
            LottieLoader(rawAsset = R.raw.congratulation_badge)
        } else {
            title = "Tetap Semangat!"
            message = "Yuk ulang lagi ${currentLevel?.levelName} pada topik ${currentLevel?.levelTopic}, supaya lebih lancar"
            LottieLoader(rawAsset = R.raw.sad_emotion)
        }

        Text(
            text = title,
            style = typography.h4.copy(
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = "Skor Anda adalah $score",
            style = typography.h6.copy(
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = message,
            style = typography.body1.copy(
                textAlign = TextAlign.Center
            )
        )
    }
}