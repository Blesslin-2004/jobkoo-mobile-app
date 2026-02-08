package com.jgene.aijobfinder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jgene.aijobfinder.data.repository.JobSearchRepository
import com.jgene.aijobfinder.feature.home.HomeViewModel
import com.jgene.aijobfinder.feature.home.SearchViewModel

class JobViewModelFactory(
    private val repository: JobSearchRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(repository) as T

            modelClass.isAssignableFrom(SearchViewModel::class.java) ->
                SearchViewModel(repository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel")
        }
    }
}
