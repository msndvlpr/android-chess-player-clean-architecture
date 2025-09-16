package de.codesample.challenge.domain.repository

import de.codesample.challenge.domain.model.move.Move
import kotlinx.coroutines.flow.Flow

interface PlayerMovieRepository {
    fun observeMoves(): Flow<Move>

    fun pause()

    fun resume()
}