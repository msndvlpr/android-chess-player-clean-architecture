package de.codesample.challenge.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoveDto (

    @SerialName("p")
    val id: Int?,

    @SerialName("x")
    val playerPosX: Int?,

    @SerialName("y")
    val playerPosY: Int?
)