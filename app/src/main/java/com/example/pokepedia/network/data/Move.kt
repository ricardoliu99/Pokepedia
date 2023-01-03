package com.example.pokepedia.network.data

import com.squareup.moshi.Json

data class Move(
    val name: String,
    val accuracy: Int?,
    val pp: Int,
    val priority: Int,
    val power: Int?,
    @Json(name = "damage_class") val damageClass: NamedApiResource,
    val type: NamedApiResource
)