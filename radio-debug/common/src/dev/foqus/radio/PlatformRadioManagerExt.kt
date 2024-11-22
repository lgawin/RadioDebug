package dev.foqus.radio

import android.hardware.radio.RadioManager
import android.hardware.radio.RadioTuner
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

internal class PlatformRadioManagerExt internal constructor(private val radioManager: RadioManager, private val logger: Logger) : RadioManagerExt {

    private val modules: List<RadioManager.ModuleProperties>

    init {
        val tempModules = arrayListOf<RadioManager.ModuleProperties>()
        val listModulesResult = radioManager.listModules(tempModules)
        logger.d("listModules returns: ${listResultToString(listModulesResult)}")
        if (listModulesResult == RadioManager.STATUS_OK) {
            listOf("${tempModules.size} modules found")
                    .plus(tempModules.map(RadioManager.ModuleProperties::toString))
                    .forEach(logger::d)
        }
        modules = tempModules.toList()
    }

    private fun listResultToString(result: Int): String = when (result) {
        RadioManager.STATUS_OK -> "success"
        RadioManager.STATUS_ERROR -> "unspecified error"
        RadioManager.STATUS_NO_INIT -> "native service cannot be reached"
        RadioManager.STATUS_BAD_VALUE -> "modules is null"
        RadioManager.STATUS_DEAD_OBJECT -> "the binder transaction to the native service fails"
        else -> throw IllegalStateException("unexpected result: $result")
    }

    override fun openTuner(index: Int, withAudio: Boolean) = callbackFlow {
        logger.d("opening module $index")
        val module = modules[index]
        logger.d("   $module...")
        val cbHandler = Handler(Looper.myLooper()!!)
        val tuner = radioManager.openTuner(
                /* moduleId = */ module.id,
                /* config = */ null,
                /* withAudio = */ withAudio,
                /* callback = */
                object : RadioTuner.Callback() {

                    override fun onAntennaState(connected: Boolean) {
                        super.onAntennaState(connected)
                        logger.d("onAntennaState: $connected")
                    }

                    override fun onProgramListChanged() {
                        super.onProgramListChanged()
                        logger.d("onProgramListChanged")
                        trySend(RadioProgram.Unkown)
                    }

                    override fun onProgramInfoChanged(info: RadioManager.ProgramInfo?) {
                        super.onProgramInfoChanged(info)
                        logger.d("onProgramInfoChanged: $info")
                        val program = info?.toRadioProgram() ?: RadioProgram.Unkown
                        trySend(program)
                    }
                },
                /* handler = */ cbHandler,
        )
        logger.d("tuner: $tuner")

        awaitClose {
            tuner.close()
        }
    }
}

private fun RadioManager.ProgramInfo.toRadioProgram(): RadioProgram =
        RadioProgram.Program(
                name = this.getName(),
                frequencyInKHz = this.getFrequency(),
        )

// TODO
private fun RadioManager.ProgramInfo.getFrequency(): Int? = null

// TODO
private fun RadioManager.ProgramInfo.getName(): String  = ""
