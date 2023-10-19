package com.example.feature.search.ui.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.extensions.collectWithLifecycle
import com.example.core.extensions.hideKeyboard
import com.example.feature.search.ui.R
import com.example.feature.search.ui.adapter.SearchAdapter
import com.example.feature.search.ui.databinding.FragmentSearchBinding
import com.example.feature.search.ui.util.SearchStatusMessage
import com.example.feature.search.ui.viewmodel.SearchViewModel
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    private lateinit var navController: NavController

    private var searchAdapter: SearchAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        bindRecyclerView()
        initVMObservers()
        observeCurrentBackStackEntry()
        observeTextWatcher()

        findNavController().let {
            navController = it
        }
    }

    private fun initVMObservers() {
        collectWithLifecycle(viewModel.message) {
            val message = when (it) {
                SearchStatusMessage.NO_RESULT -> R.string.no_result
                SearchStatusMessage.SEARCH_SUCCESS -> R.string.search_success
                SearchStatusMessage.SEARCH_FAILURE -> R.string.search_failure
                SearchStatusMessage.LOADING -> R.string.loading
                SearchStatusMessage.SEARCH_SUCCESS_FROM_BACK_STACK_ENTRY -> R.string.search_success_with_back_stack
                SearchStatusMessage.EMPTY_LIST -> R.string.empty_list
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
        collectWithLifecycle(viewModel.cards) { heroList ->
            searchAdapter?.submitList(heroList)
            hideKeyboard()
        }
    }

    private fun observeTextWatcher() {
        binding.searchEditText.addTextChangedListener(searchTextWatcher)
    }

    private fun scrollToTop() {
        binding.searchRecyclerView.scrollToPosition(0)
    }

    private fun setEmptyText() {
        binding.searchEditText.setText("")
    }

    private fun clearList() {
        searchAdapter?.apply {
            submitList(emptyList())
            notifyDataSetChanged()
        }
    }

    private fun deleteSearch() {
        binding.closeImageView.setOnClickListener {
            clearList()
            scrollToTop()
            setEmptyText()
        }
    }

    private val searchTextWatcher: TextWatcher = (object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val heroHealthPoint = s?.toString()?.toIntOrNull()
            if (heroHealthPoint != null && s.isNotEmpty()) {
                if (viewModel.backStackFlag.value) {
                    viewModel.setCustomBackStackFlag(false)
                    return
                }
                binding.closeImageView.visibility = View.VISIBLE
                deleteSearch()
                viewModel.getSearchResult(heroHealthPoint, isBackStackEntry = false)
            } else {
                binding.closeImageView.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })

    private fun bindRecyclerView() {
        searchAdapter = SearchAdapter(heroClickListener = { hero ->
            (requireActivity() as ToFlowNavigatable).navigateToFlow(
                NavigationFlow.DetailFlow(
                    card = hero
                )
            )
        })
        binding.searchRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), GRID_COUNT)
            adapter = searchAdapter
        }
    }

    private fun getCurrentSearchTextAndRefreshList() {
        val heroHealthPoint = viewModel.healthPoint.value.toIntOrNull()
        if (heroHealthPoint != null) {
            viewModel.getSearchResult(heroHealthPoint, isBackStackEntry = true)
        }
    }

    private fun observeCurrentBackStackEntry() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                findNavController().currentBackStackEntry?.savedStateHandle?.getStateFlow(
                    BACK_STACK_OUTPUT,
                    ""
                )?.collect { result ->
                    if (result.isNotBlank()) {
                        viewModel.setCustomBackStackFlag(true)
                        viewModel.setCustomHealthPoint(result)
                        getCurrentSearchTextAndRefreshList()
                        clearBackStack()
                    }
                }
            }
        }
    }

    private fun clearBackStack() {
        findNavController().currentBackStackEntry?.savedStateHandle?.remove<String>(
            BACK_STACK_OUTPUT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.searchRecyclerView?.adapter = null
        _binding = null
    }

    companion object {
        const val BACK_STACK_OUTPUT = "backStackEntry"
        const val GRID_COUNT = 2
    }
}