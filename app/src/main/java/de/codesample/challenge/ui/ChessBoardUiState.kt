package de.codesample.challenge.ui

import de.codesample.challenge.domain.model.player.Player


sealed class ChessBoardUiState {

    object Loading : ChessBoardUiState()

    data class Success(val players: Map<Player, Pair<Int, Int>>) : ChessBoardUiState()

    data class Error(val message: String) : ChessBoardUiState()
}


enum class ChessBoardUiEventTypes {
    STOPPED,
    PAUSED,
    PLAYING
}

