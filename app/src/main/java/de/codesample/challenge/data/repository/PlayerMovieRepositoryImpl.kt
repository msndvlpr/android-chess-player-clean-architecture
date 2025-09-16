package de.codesample.challenge.data.repository

import de.codesample.challenge.data.datasource.local.PlayerAssetDataSource
import de.codesample.challenge.data.mapper.MoveMapper
import de.codesample.challenge.domain.model.move.Move
import de.codesample.challenge.domain.repository.PlayerMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PlayerMovieRepositoryImpl(
    private val playerAssetDataSource: PlayerAssetDataSource,
    private val mapper: MoveMapper
) : PlayerMovieRepository {

    override fun observeMoves(): Flow<Move> {
        val moveDtoFlow = playerAssetDataSource.getMovesStreamSmallSize()
        return moveDtoFlow.map { dto ->
            mapper.mapToDomain(dto)
        }
    }

    override fun pause() {
        playerAssetDataSource.pause()
    }

    override fun resume() {
        playerAssetDataSource.resume()
    }
}
