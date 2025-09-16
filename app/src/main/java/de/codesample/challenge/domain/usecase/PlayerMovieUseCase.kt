package de.codesample.challenge.domain.usecase

import de.codesample.challenge.domain.model.move.Move
import de.codesample.challenge.domain.repository.PlayerMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PlayerMovieUseCase @Inject constructor(
    private val repository: PlayerMovieRepository
) {
    operator fun invoke(): Flow<Move> = repository.observeMoves()

    fun pause() = repository.pause()

    fun resume() = repository.resume()
}
