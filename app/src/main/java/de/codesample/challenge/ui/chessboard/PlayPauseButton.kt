package de.codesample.challenge.ui.chessboard

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.codesample.challenge.ui.ChessBoardUiEventTypes
import de.codesample.challenge.ui.theme.buttonBg


@Composable
fun PlayPauseButton(
    playState: ChessBoardUiEventTypes,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonBg),
        modifier = Modifier.width(boardLength.dp)
    ) {
        Icon(
            imageVector = if (playState == ChessBoardUiEventTypes.PLAYING)
                Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = if (playState == ChessBoardUiEventTypes.PLAYING)
                "Pause" else "Play",
            tint = Color.White
        )
    }
}
