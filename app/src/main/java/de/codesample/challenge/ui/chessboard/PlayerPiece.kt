package de.codesample.challenge.ui.chessboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.codesample.challenge.domain.model.player.Player


@Composable
fun PlayerPiece(player: Player, x: Int, y: Int) {

    val color = when (player.getColor()) {
        Player.Companion.PlayerColor.RED -> Color.Red
        Player.Companion.PlayerColor.GREEN -> Color.Green
        Player.Companion.PlayerColor.BLUE -> Color.Blue
    }

    Box(
        modifier = Modifier
            .size(30.dp)
            .offset(x.dp * 41, (boardFields - 1 - y).dp * 41)
            .background(color, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = player.getShortName(),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}