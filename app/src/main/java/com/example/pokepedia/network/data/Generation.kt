package com.example.pokepedia.network.data

import com.squareup.moshi.Json

data class Generation(
    @Json(name = "pokemon_species") val pokemonSpecies: List<NamedApiResource>,
)
data class GenerationCount(
    val count: Int
)