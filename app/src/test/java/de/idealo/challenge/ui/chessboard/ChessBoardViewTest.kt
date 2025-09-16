package de.codesample.challenge.ui.chessboard

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.codesample.challenge.ui.ChessBoardUiEventTypes
import de.codesample.challenge.ui.ChessBoardUiState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

class CustomRobolectricRunner : RobolectricTestRunner(AndroidJUnit4::class.java) {
    // You can add more configuration here if needed
}

//@RunWith(AndroidJUnit4::class)
@RunWith(CustomRobolectricRunner::class)
class BoardComposableTest {

    // 1. The Compose Test Rule
    @get:Rule
    val composeTestRule = createComposeRule()

    // 2. Mock the ViewModel
    private lateinit var mockViewModel: ChessBoardViewModel
    private lateinit var mockUiState: MutableStateFlow<ChessBoardUiState>
    private lateinit var mockPlayState: MutableStateFlow<ChessBoardUiEventTypes>

    // 3. Setup function before each test
    private fun setupMockViewModel(initialUiState: ChessBoardUiState, initialPlayState: ChessBoardUiEventTypes) {
        mockViewModel = mockk(relaxed = true) // 'relaxed = true' stubs all functions to do nothing
        mockUiState = MutableStateFlow(initialUiState)
        mockPlayState = MutableStateFlow(initialPlayState)

        every { mockViewModel.uiState } returns mockUiState.asStateFlow()
        every { mockViewModel.playState } returns mockPlayState.asStateFlow()
    }

    // 4. Test for the Loading state
    @Test
    fun givenLoadingState_whenComposed_thenShowsCircularProgressIndicator() {
        // GIVEN the ViewModel is in a Loading state
        setupMockViewModel(
            initialUiState = ChessBoardUiState.Loading,
            initialPlayState = ChessBoardUiEventTypes.STOPPED
        )

        // WHEN the Composable is set
        composeTestRule.setContent {
            Board(viewModel = mockViewModel)
        }

        // THEN a CircularProgressIndicator is displayed
        composeTestRule.onNodeWithTag("loadingIndicator").assertIsDisplayed()
    }
}