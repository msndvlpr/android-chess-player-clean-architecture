package de.codesample.challenge.data.datasource.local

import android.content.Context
import android.content.res.AssetManager
import androidx.test.core.app.ApplicationProvider
import de.codesample.challenge.data.model.MoveDto
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream


class PlayerAssetDataSourceTest {


    private lateinit var playerAssetDataSource: PlayerAssetDataSource
    private lateinit var mockContext: Context
    private lateinit var mockAssetManager: AssetManager
    private val json = Json {ignoreUnknownKeys = true}

    @Before
    fun setup(){

        mockContext = mockk()
        mockAssetManager = mockk()
        every{ mockContext.assets } returns mockAssetManager
        playerAssetDataSource = PlayerAssetDataSource(context = mockContext, json = json)
    }

    @Test
    fun `GIVEN a json file WHEN context is provided THEN should successfully fetch the content and pars it` () = runTest{
        val jsonString = """
            {
  "moves": [
    {
      "p": 0,
      "x": 0,
      "y": 0
    },
    {
      "p": 1,
      "x": 3,
      "y": 0
    },
    {
      "p": 2,
      "x": 6,
      "y": 0
    }]}
        """.trimIndent()

        val mockInputStream = ByteArrayInputStream(jsonString.encodeToByteArray())
        every { mockAssetManager.open("play.json") } returns mockInputStream
        val moves = mutableListOf<MoveDto>()
        playerAssetDataSource.getMovesStreamSmallSize().collect {
            moves.add(it)
        }

        assertEquals(3, moves.size)
        assertEquals(0, moves[0].id)
        assertEquals(0, moves[0].playerPosX)
        assertEquals(0, moves[0].playerPosY)
    }
}