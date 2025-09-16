package de.codesample.challenge.domain.model.move

import de.codesample.challenge.domain.model.player.Player

data class Move(
    val player: Player,

    val posX: Int,

    val posY: Int
)