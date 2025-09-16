package de.codesample.challenge.data.mapper

import de.codesample.challenge.data.model.MoveDto
import de.codesample.challenge.domain.model.PlayerRegistry
import de.codesample.challenge.domain.model.move.Move
import javax.inject.Inject


class MoveMapper @Inject constructor(
    private val players: PlayerRegistry
){

    fun mapToDomain(dto: MoveDto): Move {
        val player = players.fromId(dto.id ?: -1)
        return Move(player = player, posX = dto.playerPosX ?: -1, posY = dto.playerPosY ?: -1)
    }

}