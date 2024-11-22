package dev.foqus.radio

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun RadioManagerExt(context: Context, logger: Logger = LogcatLogger("RadioManagerExt")) = object : RadioManagerExt {

    override fun openTuner(index: Int, withAudio: Boolean): Flow<RadioProgram> {
        logger.d("Just a stub")
        return flow {

        }
    }
}
