package com.example.mindsmom
import android.view.View

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MentalHealthAssessmentActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var totalScore = 0
    private lateinit var questions: List<Question>
    private lateinit var moodEmojiImageView: ImageView
    private lateinit var progressBar: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mental_health_assessment)
        moodEmojiImageView = findViewById(R.id.mood_emoji_image_view)
        progressBar = findViewById(R.id.progress_bar)
        questions = listOf(
            // PHQ-9 Questions
            Question("Over the last two weeks, how often have you been bothered by little interest or pleasure in doing things?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by feeling down, depressed, or hopeless?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by trouble falling asleep, staying asleep, or sleeping too much?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by feeling tired or having little energy?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by poor appetite or overeating?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by feeling bad about yourself or that you are a failure or have let yourself or your family down?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by trouble concentrating on things, such as reading the newspaper or watching television?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by moving or speaking so slowly that other people could have noticed? Or the opposite, being so fidgety or restless that you have been moving around a lot more than usual?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by thoughts that you would be better off dead, or of hurting yourself in some way?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),

            // GAD-7 Questions
            Question("Over the last two weeks, how often have you been bothered by feeling nervous, anxious, or on edge?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by not being able to stop or control worrying?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by worrying too much about different things?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by trouble relaxing?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by being so restless that it is hard to sit still?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by becoming easily annoyed or irritable?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3)),
            Question("Over the last two weeks, how often have you been bothered by feeling afraid as if something awful might happen?", listOf("Not at all", "Several days", "More than half the days", "Nearly every day"), listOf(0, 1, 2, 3))
        )


        progressBar.max = questions.size * 100

        loadQuestion(currentQuestionIndex)
    }

    private fun loadQuestion(index: Int) {
        val currentQuestion = questions[index]
        val questionTextView = findViewById<TextView>(R.id.question_text_view)
        questionTextView.text = currentQuestion.questionText

        val answerChoicesRadioGroup = findViewById<RadioGroup>(R.id.answer_choices_radio_group)
        answerChoicesRadioGroup.removeAllViews()
        answerChoicesRadioGroup.clearCheck() // Uncheck all radio buttons

        for (choice in currentQuestion.answerChoices) {
            val radioButton = RadioButton(this)
            radioButton.text = choice
            answerChoicesRadioGroup.addView(radioButton)
        }

        val progress = ((index + 1) * 100) / questions.size

        // Update ProgressBar
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        val nextButton = findViewById<Button>(R.id.next_button)
        nextButton.setOnClickListener {
            handleUserResponse()
        }
    }

    private fun interpretDepressionScore(score: Int) {
        val moodEmojiImageView = findViewById<ImageView>(R.id.mood_emoji_image_view)
        val resultTextView = findViewById<TextView>(R.id.result_text_view)

        when {
            score in 0..4 -> {
                // Minimal depression symptoms
                moodEmojiImageView.setImageResource(R.drawable.happy)
                moodEmojiImageView.visibility = View.VISIBLE
                resultTextView.text = "You have minimal depression symptoms."
            }
            score in 5..9 -> {
                // Mild depression
                moodEmojiImageView.setImageResource(R.drawable.natural)
                moodEmojiImageView.visibility = View.VISIBLE
                resultTextView.text = "You have mild depression."
            }
            else -> {
                // Moderate or severe depression
                moodEmojiImageView.setImageResource(R.drawable.sad)
                moodEmojiImageView.visibility = View.VISIBLE
                resultTextView.text = "You have moderate or severe depression."
            }
        }
    }




    private fun updateMoodEmoji() {
        // Determine which emoji to display based on totalScore
        val emojiResource = when {
            totalScore <= 4 -> R.drawable.happy // Use your own resource IDs
            totalScore <= 9 -> R.drawable.natural
            else -> R.drawable.sad
        }

        moodEmojiImageView.setImageResource(emojiResource)
    }

    private fun handleUserResponse() {
        val answerChoicesRadioGroup = findViewById<RadioGroup>(R.id.answer_choices_radio_group)
        val selectedRadioButtonId = answerChoicesRadioGroup.checkedRadioButtonId

        if (selectedRadioButtonId != -1) {
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            totalScore += questions[currentQuestionIndex].getScoreForAnswer(selectedRadioButton.text.toString())

            currentQuestionIndex++
            progressBar.progress += 100
            if (currentQuestionIndex < questions.size) {
                loadQuestion(currentQuestionIndex)
            } else {
                // Assessment is complete, calculate final score or show results
                interpretDepressionScore(totalScore)

                updateMoodEmoji()

                // Disable next button to prevent further interactions
                findViewById<Button>(R.id.next_button).isEnabled = false
            }
        } else {
            // Show a toast if no answer is selected
            Toast.makeText(this, "Please select an answer.", Toast.LENGTH_SHORT).show()
        }
    }






}
