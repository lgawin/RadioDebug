package dev.foqus.radio

import android.content.Context
import android.hardware.radio.RadioManager
import androidx.core.content.getSystemService

fun RadioManagerExt(context: Context, logger: Logger = LogcatLogger("RadioManagerExt")): RadioManagerExt {
    return PlatformRadioManagerExt(context.getSystemService<RadioManager>()!!, logger)
}
