package com.example.mindsmom

class Question(val questionText: String, val answerChoices: List<String>, val answerScores: List<Int>) {
    init {
        require(answerChoices.size == answerScores.size) {
            "The lists 'answerChoices' and 'answerScores' must have the same length."
        }
    }

    fun getScoreForAnswer(answer: String): Int {
        val index = answerChoices.indexOf(answer)
        return if (index >= 0 && index < answerScores.size) {
            answerScores[index]
        } else {
            throw IllegalArgumentException("Answer not found in choices.")
        }
    }
}
