package me.danielaguilar.pokedex.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.danielaguilar.pokedex.data.datasource.local.pokedex.PokemonDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {
    @Provides
    @Singleton
    fun providePokemonDb(@ApplicationContext applicationContext: Context): PokemonDatabase {
        return Room.databaseBuilder(
            applicationContext,
            PokemonDatabase::class.java, "pokemon-database"
        ).build()
    }
}