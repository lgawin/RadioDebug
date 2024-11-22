package dev.foqus.radio

import kotlinx.coroutines.flow.Flow

sealed class RadioProgram {
    object Unkown : RadioProgram()
    data class Program(val name: String, val frequencyInKHz: Int?) : RadioProgram()
}

interface RadioManagerExt {

    fun openTuner(index: Int, withAudio: Boolean = false): Flow<RadioProgram>
}
