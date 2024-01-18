package com.example.mindsmom

class Message(private var msg: String, private var sender: String) {

    val message: String
        get() = msg

    val sentBy: String
        get() = sender

    fun setMessage(message: String) {
        this.msg = message
    }

    fun setSentBy(sentBy: String) {
        this.sender = sentBy
    }

    companion object {
        const val SENT_BY_ME = "me"
        const val SENT_BY_BOT = "bot"
    }
}
