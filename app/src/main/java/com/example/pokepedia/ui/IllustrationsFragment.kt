package com.example.pokepedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pokepedia.databinding.FragmentIllustrationsBinding
import com.example.pokepedia.model.PokemonViewModel

class IllustrationsFragment: Fragment() {
    private var _binding: FragmentIllustrationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PokemonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIllustrationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pokemon.observe(viewLifecycleOwner) {
            binding.apply {
                defaultFront = it.sprites.frontDefault
                defaultBack = it.sprites.backDefault
                shinyFront = it.sprites.frontShiny
                shinyBack = it.sprites.backShiny
            }
        }
    }
}