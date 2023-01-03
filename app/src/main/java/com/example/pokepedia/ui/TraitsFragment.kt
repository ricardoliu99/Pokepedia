package com.example.pokepedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.pokepedia.R
import com.example.pokepedia.databinding.FragmentTraitsBinding
import com.example.pokepedia.model.PokemonViewModel
import com.example.pokepedia.network.data.Pokemon
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class TraitsFragment : Fragment() {
    private var _binding: FragmentTraitsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PokemonViewModel by activityViewModels()
    private lateinit var chartDataSet: BarDataSet
    private lateinit var chartData: BarData
    private lateinit var pokemonStatsLabels: Array<String>
    private lateinit var pokemonStatsValues: List<Float>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTraitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPokemonTraits()
        bindPokemonTraits()
        bindPokemonSpeciesTraits()
    }

    private fun setPokemonTraits() {
        arguments?.let {
            val pokemon = it.getString("pokemon")
            if (pokemon != null) {
                viewModel.setPokemon(pokemon)
                viewModel.setPokemonSpecies(pokemon)
            }
        }
    }

    private fun bindPokemonTraits() {
        viewModel.pokemon.observe(viewLifecycleOwner) {
            val pokemonTypes = it.types.joinToString("\n") { pType ->
                pType.type.name
            }.uppercase()

            val pokemonAbilities = it.abilities.joinToString("\n") { pType ->
                pType.ability.name
            }.uppercase()

            binding.apply {
                pokemonNameTrait.text = it.name.uppercase()
                pokemonImage = it.sprites.frontDefault
                val heightMeters = it.height.toFloat() / 10.0
                val weightKg = it.weight.toFloat() / 10.0
                heightValue.text = getString(R.string.heightVal, heightMeters)
                weightValue.text = getString(R.string.weightVal, weightKg)
                typesValue.text = pokemonTypes
                abilitiesValue.text = pokemonAbilities
            }
            bindBaseStatsChart(it)
        }
    }

    private fun bindPokemonSpeciesTraits() {
        viewModel.pokemonSpecies.observe(viewLifecycleOwner) {
            binding.apply {
                colorValue.text = it.color.name.uppercase()
                shapeValue.text = it.shape.name.uppercase()
                evolvesFromValue.text =
                    it.evolvesFromSpecies?.name?.uppercase() ?: getString(R.string.none)
            }
        }
    }

    private fun bindBaseStatsChart(pokemon: Pokemon) {
        setUpBarData(pokemon)
        configureBarChart()
    }

    private fun setUpBarData(pokemon: Pokemon) {
        pokemonStatsLabels = pokemon.stats.map {
            it.stat.name.uppercase()
        }.toTypedArray()
        pokemonStatsValues = pokemon.stats.map {
            it.baseStat.toFloat()
        }
        val chartValues: ArrayList<BarEntry> = ArrayList()
        for (i in 0 until pokemon.stats.size) {
            chartValues.add(
                BarEntry(i.toFloat(), pokemonStatsValues[i])
            )
        }
        chartDataSet = BarDataSet(chartValues, "")
        chartData = BarData(chartDataSet)
    }

    private fun configureBarChart() {
        val colorTheme = ResourcesCompat.getColor(resources, R.color.light_red, null)
        chartDataSet.valueTextColor = colorTheme
        chartDataSet.color = colorTheme
        chartDataSet.valueTextSize = 14f

        binding.statsChart.apply {
            notifyDataSetChanged()
            data = chartData
            invalidate()
            legend.isEnabled = false
            description.isEnabled = false
            setTouchEnabled(false)
            xAxis.setDrawGridLines(false)
            xAxis.textColor = colorTheme
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return pokemonStatsLabels[value.toInt()]
                }
            }
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.labelRotationAngle = -45f
            extraBottomOffset = 64f
            extraTopOffset = 32f
            axisRight.isEnabled = false
            axisLeft.setDrawGridLines(false)
            axisLeft.textColor = colorTheme
            axisLeft.axisMinimum = 0f
            axisLeft.axisMaximum = pokemonStatsValues.max()
            barData.setValueFormatter(
                object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return value.toInt().toString()
                    }
                }
            )
        }
    }
}