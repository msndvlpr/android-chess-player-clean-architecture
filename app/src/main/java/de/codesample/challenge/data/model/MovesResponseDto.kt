package de.codesample.challenge.data.model

import kotlinx.serialization.Serializable


@Serializable
data class MovesResponseDto(

    val moves: List<MoveDto>
)