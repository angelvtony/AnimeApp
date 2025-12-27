package com.example.animeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentAnimeListBinding
import com.example.animeapp.ui.adapter.AnimeAdapter
import com.example.animeapp.ui.viewmodel.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeListFragment : Fragment() {

    private var _binding: FragmentAnimeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnimeViewModel by viewModels()
    private lateinit var adapter: AnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = AnimeAdapter { anime ->
            val action = AnimeListFragmentDirections.actionAnimeListFragmentToAnimeDetailFragment(anime.id)
            findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter
        
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshList()
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchAnime(it)
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    viewModel.clearSearch()
                }
                return true
            }
        })

        binding.recyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as androidx.recyclerview.widget.GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= 25
                ) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.animeList.collect { list ->
                        adapter.submitList(list)
                    }
                }
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        val isListEmpty = adapter.currentList.isEmpty()
                        val showShimmer = isLoading && isListEmpty
                        
                        binding.shimmerLayout.root.isVisible = showShimmer
                        binding.recyclerView.isVisible = !showShimmer
                        
                        if (showShimmer) {
                            binding.shimmerLayout.shimmerViewContainer.startShimmer()
                        } else {
                            binding.shimmerLayout.shimmerViewContainer.stopShimmer()
                        }
                        
                        binding.swipeRefresh.isRefreshing = isLoading && !isListEmpty
                        
                        binding.progressBar.isVisible = false
                    }
                }
                launch {
                    viewModel.error.collect { error ->
                        if (error != null) {
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            viewModel.clearError()
                        }
                    }
                }
                launch {
                    viewModel.isPaginating.collect { isPaginating ->
                        binding.paginationProgressBar.isVisible = isPaginating
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
