package de.codesample.challenge.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.codesample.challenge.data.datasource.local.PlayerAssetDataSource
import de.codesample.challenge.data.mapper.MoveMapper
import de.codesample.challenge.data.repository.PlayerMovieRepositoryImpl
import de.codesample.challenge.domain.model.PlayerRegistry
import de.codesample.challenge.domain.model.player.Player
import de.codesample.challenge.domain.repository.PlayerMovieRepository
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

    @Provides
    @Singleton
    fun providePlayerMoveDataSource(
        @ApplicationContext context: Context,
        json: Json
    ): PlayerAssetDataSource {
        return PlayerAssetDataSource(context, json)
    }

    @Provides
    @Singleton
    internal fun provideMoveRepository(
        playerAssetDataSource: PlayerAssetDataSource,
        mapper: MoveMapper
    ): PlayerMovieRepository = PlayerMovieRepositoryImpl(
        playerAssetDataSource,
        mapper
    )

    @Provides
    @Singleton
    fun provideMoveMapper(playerRegistry: PlayerRegistry): MoveMapper {
        return MoveMapper(playerRegistry)
    }

    @Provides
    fun provideColorOfFunction(): @JvmSuppressWildcards (Int) -> Player.Companion.PlayerColor = { id ->
        when (id) {
            0 -> Player.Companion.PlayerColor.RED
            1 -> Player.Companion.PlayerColor.GREEN
            2 -> Player.Companion.PlayerColor.BLUE
            else -> Player.Companion.PlayerColor.BLUE //fallback
        }
    }

    @Provides
    @Singleton
    fun providePlayerRegistry(
        colorOf: @JvmSuppressWildcards (Int) -> Player.Companion.PlayerColor
    ): PlayerRegistry = PlayerRegistry(colorOf)

}



