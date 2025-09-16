package de.codesample.challenge.domain.model.player

interface Player {

    fun getId(): Int
    fun getColor(): PlayerColor
    fun getShortName(): String

    companion object {
        enum class PlayerColor {
            RED,
            GREEN,
            BLUE
        }
    }

}