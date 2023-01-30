package com.midcores.ahlanarabiyyah.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.midcores.ahlanarabiyyah.helper.Current
import com.midcores.ahlanarabiyyah.helper.Database.realm
import com.midcores.ahlanarabiyyah.model.GameUIState
import com.midcores.ahlanarabiyyah.model.Question
import com.midcores.ahlanarabiyyah.model.database.Level
import com.midcores.ahlanarabiyyah.model.enums.GameType
import com.midcores.ahlanarabiyyah.model.enums.StatusTopic
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.*

class LearnViewModel: ViewModel() {
    private val questions = generateQuestions()
    private val _uiState = MutableStateFlow(GameUIState(currentQuestion = questions[0]))

    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()
    var numberOfQuestion: Int = 0
    var maximumScore: Int = questions.filter {
        Log.d("TAG", "updateGameState: gameType ${it.gameType}")
        it.gameType != GameType.MEMORIZE
    }.size

    private fun generateQuestions(): List<Question> {
        val wordList = Current.wordList
        val questionList = arrayListOf<Question>()

        numberOfQuestion = wordList.count()

        wordList.forEach {
            questionList.add(
                Question(
                    it.arabicWord,
                    it.transliteration,
                    it.translation,
                    arrayListOf(""),
                    GameType.MEMORIZE
                )
            )

            val options = arrayListOf(it.translation)
            val rawOptions = wordList.shuffled()

            for (kata in rawOptions) {
                val translation = kata.translation

                if (!options.contains(translation) &&
                    options.size < 4
                ){
                    options.add(translation)
                }
            }

            questionList.add(
                Question(
                    it.arabicWord,
                    it.transliteration,
                    it.translation,
                    options.shuffled(),
                    GameType.EXERCISE
                )
            )
        }

        return questionList
    }

    fun updateGameState(
        updatedScore: Int,
    ) {
        val oneProgress = 1f / numberOfQuestion.toFloat()
        val currentProgress: Float =
            ((_uiState.value.currentCount).toFloat() / (numberOfQuestion).toFloat()) + oneProgress

        if (_uiState.value.currentCount + 1 == numberOfQuestion){
            val percentage = 100f / maximumScore.toFloat()
            val finalScore = updatedScore * percentage
            Log.d("TAG", "updateGameState: percentage $percentage skor $updatedScore max score $maximumScore")
            _uiState.update { currentState ->
                currentState.copy(
                    currentCount = currentState.currentCount.inc(),
                    currentProgress = 1f,
                    score = finalScore.toInt(),
                    gameType = GameType.FINISHED,
                    isGameOver = true
                )
            }
        } else {
            _uiState.update { currentState ->
                val currentCount = currentState.currentCount.inc()
                val currentQuestion = questions[currentCount]

                currentState.copy(
                    currentCount = currentState.currentCount.inc(),
                    currentProgress = currentProgress,
                    currentQuestion = currentQuestion,
                    gameType = currentQuestion.gameType,
                    score = updatedScore
                )
            }
        }
    }

    fun finishLevel() {
        realm.writeBlocking {
            val currentLevel = query<Level>(
                "levelID == $0", Current.level?.levelID
            ).first().find()

            currentLevel?.finishedAt = getCurrentDate()
            currentLevel?.status = StatusTopic.FINISHED.name

            val nextLevel = query<Level>(
                "order > $0", currentLevel?.order
            ).first().find()

            nextLevel?.status = StatusTopic.CURRENT.name
        }
    }

    private fun getCurrentDate(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        return formatter.format(time)
    }
}