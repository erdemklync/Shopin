package com.erdemklync.shopin.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.erdemklync.shopin.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val categoryAdapter = CategoryAdapter { category ->
        viewModel.selectFilter(category)
    }

    private val productAdapter = SearchAdapter { product ->
        val action = SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product.id ?: 0)
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.filteredList.collect { filteredList ->
                        productAdapter.submitList(filteredList).also {
                            binding.recyclerViewProducts.smoothScrollToPosition(0)
                        }
                    }
                }
                launch {
                    viewModel.state.collect { state ->
                        categoryAdapter.submitList(state.categories).also {
                            binding.recyclerViewProducts.smoothScrollToPosition(0)
                        }
                    }
                }
            }
        }

        with(binding) {
            editTextSearch.doAfterTextChanged { query ->
                viewModel.setQuery(query.toString())
            }

            recyclerViewCategories.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = categoryAdapter
            }
            recyclerViewProducts.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = productAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}