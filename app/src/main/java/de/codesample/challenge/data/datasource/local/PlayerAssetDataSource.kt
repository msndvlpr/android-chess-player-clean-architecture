package de.codesample.challenge.data.datasource.local

import android.content.Context
import android.util.JsonReader
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.mutableStateOf
import de.codesample.challenge.data.model.MoveDto
import de.codesample.challenge.data.model.MovesResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.decodeToSequence
import javax.inject.Inject


class PlayerAssetDataSource (
    private val context: Context,
    private val json: Json
) {

    @OptIn(ExperimentalSerializationApi::class)
    fun getMovesStreamSmallSize(): Flow<MoveDto> = flow { /** it will be optimised small json file  */

        context.assets.open("play.json").use { inputStream ->
            val moveList = json.decodeFromStream<MovesResponseDto>(inputStream) /**  will load all json content all at once but via stream */

            moveList.moves.forEach { move ->
                paused.filter { !it }.first() /** `pause` is a hot flow and here `first()` will suspend until this flow has it's first value.
                                                   So emission will be suspended as long as the `pause` is true */
                emit(move)
                delay(1_000)
            }
        }

    }.flowOn(Dispatchers.IO)

    @VisibleForTesting
    private val paused = MutableStateFlow(false)

    fun pause() {
        paused.value = true
    }

    fun resume() {
        paused.value = false
    }

}
