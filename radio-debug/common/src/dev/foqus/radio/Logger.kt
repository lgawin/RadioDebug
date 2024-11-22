package dev.foqus.radio

interface Logger {
    val tag: String

    fun d(message: String)
}

class LogcatLogger(override val tag: String) : Logger {
    override fun d(message: String) {
        android.util.Log.d(tag, message)
    }
}
