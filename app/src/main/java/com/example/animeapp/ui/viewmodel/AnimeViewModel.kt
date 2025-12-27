package com.example.animeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.model.Anime
import com.example.animeapp.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    
    private val _searchResults = MutableStateFlow<List<Anime>?>(null)

    val animeList: StateFlow<List<Anime>> = kotlinx.coroutines.flow.combine(
        repository.animeList,
        _searchResults
    ) { dbList, searchList ->
        searchList ?: dbList
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _selectedAnime = MutableStateFlow<Anime?>(null)
    val selectedAnime: StateFlow<Anime?> = _selectedAnime.asStateFlow()

    private var currentPage = 1
    private var isLastPage = false
    private val _isPaginating = MutableStateFlow(false)
    val isPaginating: StateFlow<Boolean> = _isPaginating.asStateFlow()

    init {
        refreshList()
    }

    fun refreshList() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            currentPage = 1
            isLastPage = false
            
            if (_searchResults.value != null) {
                 _searchResults.value = null
                 val result = repository.refreshAnimeList(1)
                 if (result.isFailure) {
                     _error.value = result.exceptionOrNull()?.message ?: "Unknown error"
                 }
            } else {
                val result = repository.refreshAnimeList(1)
                if (result.isFailure) {
                    _error.value = result.exceptionOrNull()?.message ?: "Unknown error"
                }
            }
            _isLoading.value = false
        }
    }

    fun loadNextPage() {
        if (_isLoading.value || _isPaginating.value || isLastPage || _searchResults.value != null) return

        viewModelScope.launch {
            _isPaginating.value = true
            val nextPage = currentPage + 1
            val result = repository.refreshAnimeList(nextPage)
            if (result.isSuccess) currentPage = nextPage
            _isPaginating.value = false
        }
    }

    fun searchAnime(query: String) {
        if (query.isBlank()) {
            _searchResults.value = null
            return
        }
        
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.searchAnime(query)
            if (result.isSuccess) {
                _searchResults.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Search failed"
            }
            _isLoading.value = false
        }
    }

    fun clearSearch() {
        _searchResults.value = null
    }

    fun loadAnimeDetail(id: Int) {
        _selectedAnime.value = null
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            val detailResult = repository.getAnimeDetail(id)
            
            if (detailResult.isSuccess) {
                val anime = detailResult.getOrNull()
                _selectedAnime.value = anime
            } else {
                _error.value = detailResult.exceptionOrNull()?.message ?: "Failed to load details"
            }
            _isLoading.value = false
        }
    }
    
    fun clearError() {
        _error.value = null
    }
}
