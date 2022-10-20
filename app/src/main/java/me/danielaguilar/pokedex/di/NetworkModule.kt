package me.danielaguilar.pokedex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.danielaguilar.pokedex.BuildConfig
import me.danielaguilar.pokedex.data.datasource.remote.pokedex.PokedexApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providePokedexApi(): PokedexApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.POKEDEX_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(PokedexApi::class.java)
    }

}