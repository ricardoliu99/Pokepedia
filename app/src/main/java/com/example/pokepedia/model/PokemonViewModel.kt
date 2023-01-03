package com.example.pokepedia.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokepedia.network.*
import com.example.pokepedia.network.data.*
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {
    private val _pokemonNameList = MutableLiveData<List<String>>()
    val pokemonNameList: LiveData<List<String>> = _pokemonNameList

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon

    private val _sprite = MutableLiveData<Sprite>()
    val sprite: LiveData<Sprite> = _sprite

    private val _move = MutableLiveData<Move>()
    val move: LiveData<Move> = _move

    private val _pokemonSpecies = MutableLiveData<PokemonSpecies>()
    val pokemonSpecies: LiveData<PokemonSpecies> = _pokemonSpecies

    private val _generationCount = MutableLiveData<Int>()
    val generationCount: LiveData<Int> = _generationCount

    private val _generation = MutableLiveData<Int>()
    val generation: LiveData<Int> = _generation

    private val _spriteList = MutableLiveData<List<Sprite>>()
    val spriteList: LiveData<List<Sprite>> = _spriteList

    private val _moveNameList = MutableLiveData<List<String>>()
    val moveNameList: LiveData<List<String>> = _moveNameList

    private val _moveList = MutableLiveData<List<Move>>()
    val moveList: LiveData<List<Move>> = _moveList

    private val originalSpriteList: MutableList<Sprite> = mutableListOf()


    init {
        setGenerationCount()
        generation.value?.let { setGenerationInfo(it) }
    }

    private fun setGenerationCount() {
        viewModelScope.launch {
            try {
                _generationCount.value = PokeApi.retrofitService.getGenerationCount().count
            } catch (_: Exception) {

            }
        }
    }

    private fun setGenerationInfo(id: Int) {
        viewModelScope.launch {
            try {
                val generation = PokeApi
                    .retrofitService
                    .getGeneration(id, "pokemon_species")
                setPokemonNameList(generation)
            } catch (e: Exception) {
                _pokemonNameList.value = listOf()
            }
        }
    }

    private fun setPokemonNameList(generation: Generation) {
        _pokemonNameList.value = generation
            .pokemonSpecies
            .map { it.name }
            .sorted()
    }

    fun setPokemon(name: String) {
        viewModelScope.launch {
            try {
                _pokemon.value = PokeApi
                    .retrofitService
                    .getPokemon(name)
            } catch (_: Exception) {

            }
        }
    }

    fun setSprite(name: String) {
        viewModelScope.launch {
            try {
                _sprite.value = PokeApi
                    .retrofitService
                    .getSprite(name)
            } catch (_: Exception) {

            }
        }
    }

    fun setMove(move: String) {
        viewModelScope.launch {
            try {
                _move.value = PokeApi
                    .retrofitService
                    .getMove(move)
            } catch (_: Exception) {

            }
        }
    }

    fun setPokemonSpecies(species: String) {
        viewModelScope.launch {
            try {
                _pokemonSpecies.value = PokeApi
                    .retrofitService
                    .getSpecies(species)
            } catch (_: Exception) {

            }
        }
    }

    fun setGeneration(generationNum: Int) {
        clearPokemonNameListAndSpriteList()
        _generation.value = generationNum
        generation.value?.let { setGenerationInfo(it) }
    }

    fun addToSpriteList(pokemonSprite: Sprite) {
        _spriteList.value = _spriteList.value?.plus(pokemonSprite) ?: listOf(pokemonSprite)
        originalSpriteList.add(pokemonSprite)
    }

    fun clearPokemonNameListAndSpriteList() {
        _pokemonNameList.value = listOf()
        _spriteList.value = listOf()
        originalSpriteList.clear()
    }

    fun setMoveNameList() {
        _moveNameList.value = pokemon.value?.moves?.map {
            it.move.name
        }
    }

    fun addToMoveList(pokemonMove: Move) {
        _moveList.value = _moveList.value?.plus(pokemonMove) ?: listOf(pokemonMove)
    }

    fun clearMoveNameListAndMoveList() {
        _moveNameList.value = listOf()
        _moveList.value = listOf()
    }

    fun sortMovesByName() {
        _moveList.value = _moveList.value?.sortedBy { it.name }
    }

    fun sortMovesByAccuracy() {
        _moveList.value = _moveList.value?.sortedBy { it.accuracy }
    }

    fun sortMovesByDamageClass() {
        _moveList.value = _moveList.value?.sortedBy { it.damageClass.name }
    }

    fun sortMovesByPower() {
        _moveList.value = _moveList.value?.sortedBy { it.power }
    }

    fun sortMovesByPP() {
        _moveList.value = _moveList.value?.sortedBy { it.pp }
    }

    fun sortMovesByPriority() {
        _moveList.value = _moveList.value?.sortedBy { it.priority }
    }

    fun sortMovesByType() {
        _moveList.value = _moveList.value?.sortedBy { it.type.name }
    }

    fun resetSpriteList() {
        _spriteList.value = originalSpriteList
    }

    fun filterSpriteList(pokemonName: String) {
        _spriteList.value = originalSpriteList.filter {
            it.name.contains(pokemonName)
        }
    }
}