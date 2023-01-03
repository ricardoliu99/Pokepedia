package com.example.pokepedia.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pokepedia.R
import com.example.pokepedia.databinding.FragmentHomeBinding
import com.example.pokepedia.model.PokemonViewModel
import com.example.pokepedia.network.data.Sprite
import com.example.pokepedia.ui.adapter.SpriteListAdapter


class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpGenerationSpinner()
        setUpPokemonSpriteList()
        setUpRecyclerView()
    }

    private fun setUpPokemonSpriteList() {
        viewModel.pokemonNameList.observe(viewLifecycleOwner) { nameList ->
            nameList.map { name ->
                viewModel.setSprite(name)
            }
        }
        viewModel.sprite.observe(viewLifecycleOwner) { newSprite ->
            viewModel.addToSpriteList(
                Sprite(newSprite.name, newSprite.sprites)
            )
        }
    }

    private fun setUpRecyclerView() {
        val adapter = SpriteListAdapter() {
            println("${it.name} ${it.sprites.frontDefault}")
            val action = HomeFragmentDirections.actionHomeFragmentToTraitsFragment(it.name)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        viewModel.spriteList.observe(viewLifecycleOwner) { spriteList ->
            val sortedSpriteList = spriteList.sortedBy { it.name }
            adapter.submitList(sortedSpriteList)
        }

        binding.recyclerView.layoutManager = GridAutofitLayoutManager(requireContext(), 360)
    }

    private fun setUpGenerationSpinner() {
        viewModel.generationCount.observe(viewLifecycleOwner
        ) { newCount ->
            val generations = (1 .. newCount).toList()
            ArrayAdapter(requireContext(),
                R.layout.spinner_item,
                generations)
                .also { adapter ->
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    binding.generationSpinner.adapter = adapter
                    binding.generationSpinner.setSelection(viewModel.generation.value?.minus(1) ?: 0)
                }
            binding.generationSpinner.onItemSelectedListener = this
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.clearPokemonNameListAndSpriteList()
        viewModel.setGeneration(p0!!.getItemAtPosition(p2) as Int)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onDestroyView() {
        viewModel.clearPokemonNameListAndSpriteList()
        super.onDestroyView()
    }
}