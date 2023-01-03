package com.example.pokepedia.network.data

import com.squareup.moshi.Json

data class Pokemon(
    val name: String,
    @Json(name = "base_experience") val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val moves: List<PokemonMove>,
    val sprites: PokemonSprites,
    val types: List<PokemonType>,
    val abilities: List<PokemonAbility>,
    val stats: List<PokemonStat>
)

data class PokemonStat(
    val stat: NamedApiResource,
    @Json(name = "base_stat") val baseStat: Int
)

data class PokemonAbility(
    @Json(name = "is_hidden") val isHidden: Boolean,
    val ability: NamedApiResource
)


data class PokemonMove(
    val move: NamedApiResource
)

data class PokemonSprites(
    @Json(name = "front_default") val frontDefault: String,
    @Json(name = "front_shiny") val frontShiny: String,
    @Json(name = "back_default") val backDefault: String,
    @Json(name = "back_shiny") val backShiny: String,
)

data class PokemonType(
    val type: NamedApiResource
)

data class PokemonSpecies(
    val color: PokemonColor,
    val shape: PokemonShape,
    @Json(name = "evolves_from_species") val evolvesFromSpecies: NamedApiResource?
)

data class PokemonColor(
    val name: String
)

data class PokemonShape(
    val name: String
)

data class Sprite(
    val name: String,
    val sprites: PokemonSprites
)