package com.example.pokepedia.network

import com.example.pokepedia.network.data.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL =
    "https://pokeapi.co/api/v2/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object PokeApi {
    val retrofitService: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }
}

interface PokeApiService {
    @GET("generation")
    suspend fun getGenerationCount(): GenerationCount

    @GET("generation/{id}")
    suspend fun getGeneration(
        @Path("id") name: Int,
        @Query("pokemonSpecies") pokemonSpecies: String
    ): Generation

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Pokemon

    @GET("move/{name}")
    suspend fun getMove(@Path("name") name: String): Move

    @GET("pokemon-species/{name}")
    suspend fun getSpecies(@Path("name") name: String): PokemonSpecies

    @GET("pokemon/{name}")
    suspend fun getSprite(@Path("name") name: String): Sprite
}