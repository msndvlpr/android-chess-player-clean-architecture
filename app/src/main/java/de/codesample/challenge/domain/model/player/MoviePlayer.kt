package de.codesample.challenge.domain.model.player

class MoviePlayer(
    private val id: Int,
    private val color: Player.Companion.PlayerColor
) : Player {

    override fun getId(): Int {
        return id
    }

    override fun getColor(): Player.Companion.PlayerColor {
        return color
    }

    override fun getShortName(): String {
        return color.name.first().toString()
    }


    override fun equals(other: Any?): Boolean {
        return other is Player && other.getId() == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    //todo: override fun toString(): String {}


}