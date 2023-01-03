package com.example.pokepedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pokepedia.databinding.FragmentMovesBinding
import com.example.pokepedia.model.PokemonViewModel
import com.example.pokepedia.ui.adapter.MoveListAdapter

class MovesFragment: Fragment() {
    private var _binding: FragmentMovesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PokemonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMoveList()
        setUpRecyclerView()
        setTextViewIsSelected()
        setTextViewOnClickListeners()
    }

    private fun setUpMoveList() {
        viewModel.setMoveNameList()
        viewModel.moveNameList.observe(viewLifecycleOwner) { nameList ->
            println(viewModel.moveNameList.value)
            viewModel.moveNameList.value?.let { println(it.size) }
            nameList.map { name ->
                viewModel.setMove(name)
            }
        }
        viewModel.move.observe(viewLifecycleOwner) { newMove ->
            viewModel.addToMoveList(newMove)
        }
    }

    private fun setUpRecyclerView() {
        val adapter = MoveListAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.moveList.observe(viewLifecycleOwner) { newMoveList ->
            adapter.submitList(newMoveList)
        }
    }

    private fun setTextViewOnClickListeners() {
        binding.apply {
            name.setOnClickListener{viewModel.sortMovesByName()}
            accuracy.setOnClickListener{viewModel.sortMovesByAccuracy()}
            damageClass.setOnClickListener{viewModel.sortMovesByDamageClass()}
            power.setOnClickListener{viewModel.sortMovesByPower()}
            pp.setOnClickListener{viewModel.sortMovesByPP()}
            priority.setOnClickListener{viewModel.sortMovesByPriority()}
            type.setOnClickListener{viewModel.sortMovesByType()}
        }
    }

    private fun setTextViewIsSelected() {
        binding.apply {
            name.isSelected = true
            accuracy.isSelected = true
            damageClass.isSelected = true
            power.isSelected = true
            pp.isSelected = true
            priority.isSelected = true
            type.isSelected = true
        }
    }

    override fun onDestroyView() {
        viewModel.clearMoveNameListAndMoveList()
        super.onDestroyView()
    }
}