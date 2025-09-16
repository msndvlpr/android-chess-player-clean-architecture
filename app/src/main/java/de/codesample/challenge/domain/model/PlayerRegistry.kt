package de.codesample.challenge.domain.model

import de.codesample.challenge.domain.model.player.MoviePlayer
import de.codesample.challenge.domain.model.player.Player
import javax.inject.Inject

/**
 * This class acts as a central manager for Player instances in your app.
 * It uses a cache (mutableMapOf<Int, Player>) so that each id maps to exactly one Player instance.
 * If you ask twice for id = 2, you always get the same Player object.
 * This avoids duplicates and keeps identity consistent across the app.
 */
class PlayerRegistry @Inject constructor(
    private val colorOf: (Int) -> Player.Companion.PlayerColor
) {
    private val cache = mutableMapOf<Int, Player>()

    fun fromId(id: Int): Player {
        return cache.getOrPut(id) { MoviePlayer(id, colorOf(id)) }
        /**
          If the id is already in the map → return the existing value.
          If it’s not in the map → execute the lambda, put the result into the map, then return it.
         */
    }
}
