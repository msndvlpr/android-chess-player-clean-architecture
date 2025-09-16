package de.codesample.challenge.ui.chessboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.codesample.challenge.domain.model.player.Player
import de.codesample.challenge.domain.usecase.PlayerMovieUseCase
import de.codesample.challenge.ui.ChessBoardUiEventTypes
import de.codesample.challenge.ui.ChessBoardUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChessBoardViewModel @Inject constructor(
    private val playerMovieUseCase: PlayerMovieUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChessBoardUiState>(ChessBoardUiState.Loading)
    val uiState: StateFlow<ChessBoardUiState> = _uiState.asStateFlow()

    private val _playState = MutableStateFlow<ChessBoardUiEventTypes>(ChessBoardUiEventTypes.STOPPED)
    val playState: StateFlow<ChessBoardUiEventTypes> = _playState.asStateFlow()


    private val playerPositions = mutableMapOf<Player, Pair<Int, Int>>()

    init {
        _playState.value = ChessBoardUiEventTypes.PLAYING
        fetchMoves()
    }

    private fun fetchMoves() {
        viewModelScope.launch {
            playerMovieUseCase()
                .onCompletion { cause ->
                    // Emission has been completed, then should stop
                    if (cause == null) {
                        _playState.value = ChessBoardUiEventTypes.STOPPED
                    }
                }
                .catch { e ->
                    _uiState.value = ChessBoardUiState.Error("Failed to load moves: ${e.message}")
                }
                .collect { move ->
                    playerPositions[move.player] = move.posX to move.posY
                    _uiState.value = ChessBoardUiState.Success(players = playerPositions.toMap())
                }
        }
    }

    fun togglePlayPause() {
        when (_playState.value) {
            ChessBoardUiEventTypes.PAUSED -> {
                _playState.value = ChessBoardUiEventTypes.PLAYING
                playerMovieUseCase.resume()
            }

            ChessBoardUiEventTypes.PLAYING -> {
                _playState.value = ChessBoardUiEventTypes.PAUSED
                playerMovieUseCase.pause()
            }

            ChessBoardUiEventTypes.STOPPED -> {
                _playState.value = ChessBoardUiEventTypes.PLAYING
                fetchMoves()
            }
        }
    }

    fun pause() {
        if(_playState.value == ChessBoardUiEventTypes.PLAYING){
            playerMovieUseCase.pause()
            _playState.value = ChessBoardUiEventTypes.PAUSED
        }
    }

    fun resume() {
        viewModelScope.launch {
            if(_playState.value == ChessBoardUiEventTypes.PAUSED){
                // Some delay to make the change a bit more sensible
                delay(700)
                _playState.value = ChessBoardUiEventTypes.PLAYING
                playerMovieUseCase.resume()
            }
        }
    }

}
