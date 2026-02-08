package com.jgene.aijobfinder.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgene.aijobfinder.data.repository.JobSearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SearchUiState(
    val loading: Boolean = false,
    val error: String? = null
)

class SearchViewModel(
    private val repo: JobSearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun startSearch(
        role: String,
        locations: String,
        jobType: String
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = SearchUiState(loading = true)
                repo.triggerN8nSearch(role, locations, jobType)
                _uiState.value = SearchUiState(loading = false)
            } catch (e: Exception) {
                _uiState.value = SearchUiState(error = e.message)
            }
        }
    }
}
