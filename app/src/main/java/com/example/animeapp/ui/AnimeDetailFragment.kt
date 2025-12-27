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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animeapp.R
import com.example.animeapp.data.model.Anime
import com.example.animeapp.databinding.FragmentAnimeDetailBinding
import com.example.animeapp.ui.adapter.CastAdapter
import com.example.animeapp.ui.viewmodel.AnimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeDetailFragment : Fragment() {

    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnimeViewModel by viewModels()
    private val args: AnimeDetailFragmentArgs by navArgs()

    private val castAdapter = CastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeViewModel()

        binding.shimmerLayout.shimmerViewContainer.startShimmer()
        binding.shimmerLayout.root.isVisible = true
        binding.contentScrollView.isVisible = false

        viewModel.loadAnimeDetail(args.animeId)
    }

    private fun setupViews() {
        binding.castRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.castRecyclerView.adapter = castAdapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.selectedAnime.collect { anime ->
                        anime?.let {
                            binding.shimmerLayout.shimmerViewContainer.stopShimmer()
                            binding.shimmerLayout.root.isVisible = false
                            binding.contentScrollView.isVisible = true
                            updateUI(it)
                        }
                    }
                }
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        if (isLoading && viewModel.selectedAnime.value == null) {
                            binding.shimmerLayout.shimmerViewContainer.startShimmer()
                            binding.shimmerLayout.root.isVisible = true
                            binding.contentScrollView.isVisible = false
                        } else if (!isLoading && viewModel.selectedAnime.value != null) {
                            binding.shimmerLayout.shimmerViewContainer.stopShimmer()
                            binding.shimmerLayout.root.isVisible = false
                            binding.contentScrollView.isVisible = true
                        }
                        binding.progressBar.isVisible = false
                    }
                }
                launch {
                    viewModel.error.collect { error ->
                        error?.let {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun updateUI(anime: Anime) {
        binding.title.text = anime.title
        binding.synopsis.text = anime.synopsis ?: "No synopsis available."
        binding.genres.text = anime.genres.joinToString(", ")
        binding.episodes.text = getString(R.string.episodes_format, anime.episodes ?: 0)
        binding.rating.text =
            getString(R.string.rating_format, anime.score?.toString() ?: "N/A")

        Glide.with(this)
            .load(anime.imageUrl)
            .transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade())
            .fitCenter()
            .into(binding.poster)

        if (!anime.trailerUrl.isNullOrEmpty()) {
            binding.btnPlayTrailer.isVisible = true
            binding.trailerLabel.isVisible = true
            binding.btnPlayTrailer.setOnClickListener {
                try {
                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(anime.trailerUrl))
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Could not open trailer", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            binding.btnPlayTrailer.isVisible = false
            binding.trailerLabel.isVisible = false
            binding.btnPlayTrailer.setOnClickListener(null)
        }

        if (anime.cast.isNotEmpty()) {
            binding.castLabel.isVisible = true
            binding.castRecyclerView.isVisible = true
            castAdapter.submitList(anime.cast)
        } else {
            binding.castLabel.isVisible = false
            binding.castRecyclerView.isVisible = false
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
