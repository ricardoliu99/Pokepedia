package com.example.pokepedia.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokepedia.R
import com.example.pokepedia.databinding.ActivityMainBinding
import com.example.pokepedia.model.PokemonViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var isSearchHidden: Boolean = true
    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setUpNavController()
        setUpNavBar()
        setUpHomeButton()
        setUpSearchButton()
        setUpEditText()

    }

    private fun setUpEditText() {
        binding.searchEditText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (count < before) {
                        viewModel.resetSpriteList()
                    }
                    viewModel.filterSpriteList(s.toString())
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            }
        )
    }

    private fun setUpHomeButton() {
        binding.homeButton.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }
    }

    private fun setUpSearchButton() {
        binding.searchButton.setOnClickListener {
            isSearchHidden = !isSearchHidden
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            if (isSearchHidden) {
                binding.searchEditTextLayout.visibility = View.GONE
                imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
                searchPokemon()
            } else {
                binding.searchEditTextLayout.visibility = View.VISIBLE
                binding.searchEditText.requestFocus()
                imm.showSoftInput(binding.searchEditText, 0)
            }
        }
    }

    private fun searchPokemon() {
        val searchInput = binding.searchEditText.text.toString().lowercase()
        viewModel.pokemonNameList.value?.let { nameList ->
            if (nameList.contains(searchInput)) {
                val argBundle = Bundle()
                argBundle.putString("pokemon", searchInput)
                navController.navigate(R.id.traitsFragment, argBundle)
            }
        }
        binding.searchEditText.text?.clear()
    }

    private fun setUpNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setUpNavBar() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (isHomeFragment(destination)) {
                binding.homeButton.visibility = View.GONE
                binding.bottomNavigation.visibility = View.GONE
                binding.searchButton.visibility = View.VISIBLE
            } else {
                binding.homeButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.searchButton.visibility = View.GONE

                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
                binding.searchEditTextLayout.visibility = View.GONE
                isSearchHidden = true
            }
        }
        binding.bottomNavigation.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.traitsFragment,
                R.id.movesFragment,
                R.id.illustrationsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun isHomeFragment(destination: NavDestination): Boolean {
        return destination.id == R.id.homeFragment
    }
}