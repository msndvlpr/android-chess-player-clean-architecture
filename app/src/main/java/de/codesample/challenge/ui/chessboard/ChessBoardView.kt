package de.codesample.challenge.ui.chessboard

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.codesample.challenge.domain.model.player.Player
import de.codesample.challenge.ui.ChessBoardUiEventTypes
import de.codesample.challenge.ui.ChessBoardUiState
import de.codesample.challenge.ui.theme.cellDark
import de.codesample.challenge.ui.theme.cellLight
import de.codesample.challenge.ui.theme.creamWhite


const val boardFields = 7
const val boardLength = boardFields * 40

@Composable
fun Board(
    viewModel: ChessBoardViewModel = hiltViewModel()
) {

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    val playState by viewModel.playState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.playState.collect { playState ->
            if(playState == ChessBoardUiEventTypes.STOPPED) {
                Toast.makeText(context, "The end", Toast.LENGTH_LONG).show()
            }
        }
    }

    LifecycleEventObserver(
        onPause = viewModel::pause,
        onResume = viewModel::resume
    )

    BoardContent(
        state = viewState,
        playState = playState,
        onTogglePlayPause = { viewModel.togglePlayPause() }
    )
}

@Composable
private fun BoardContent(
    state: ChessBoardUiState,
    playState: ChessBoardUiEventTypes,
    onTogglePlayPause: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (state) {
                is ChessBoardUiState.Loading -> CircularProgressIndicator()

                is ChessBoardUiState.Error -> Text("Error: ${state.message}")

                is ChessBoardUiState.Success -> {
                    ChessBoard(players = state.players)

                    Spacer(Modifier.height(24.dp))

                    PlayPauseButton(
                        playState = playState,
                        onClick = onTogglePlayPause
                    )
                }
            }
        }
    }
}


@Composable
fun LifecycleEventObserver(
    onPause: () -> Unit,
    onResume: () -> Unit
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> { onPause() }
                Lifecycle.Event.ON_RESUME -> { onResume() }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun ChessBoard(players: Map<Player, Pair<Int, Int>>) {

    Box(
        modifier = Modifier.size(boardLength.dp)
    ) {
        Column(
            Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            for (rank in boardFields - 1 downTo 0) {
                Row(Modifier.weight(1f)) {
                    for (file in 0 until boardFields) {
                        val isDarkSquare = isDarkSquare(rank, file)
                        Square(
                            isDarkSquare,
                            Modifier
                                .weight(1F, true)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }

        // Draw the players on the board afterwards
        players.forEach { (player, pos) ->
            PlayerPiece(
                player = player,
                x = pos.first,
                y = pos.second
            )
        }
    }
}

@Composable
fun Square(isDarkSquare: Boolean, modifier: Modifier) {
    Canvas(modifier = modifier) { drawRect(color = if (isDarkSquare) cellDark else cellLight) }
}

@Preview(showBackground = true, backgroundColor = creamWhite, widthDp = 40, heightDp = 40)
@Composable
internal fun SquarePreview(@PreviewParameter(BooleanParameterProvider::class) isDarkSquare: Boolean) {
    Square(isDarkSquare = isDarkSquare, modifier = Modifier.fillMaxSize())
}

private fun isDarkSquare(rank: Int, file: Int): Boolean = (rank + file % 2) % 2 == 1

internal class BooleanParameterProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(true, false)
}

