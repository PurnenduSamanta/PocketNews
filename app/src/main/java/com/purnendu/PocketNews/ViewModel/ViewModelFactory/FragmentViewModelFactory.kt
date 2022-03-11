package com.purnendu.PocketNews.ViewModel.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.purnendu.PocketNews.Repository
import com.purnendu.PocketNews.ViewModel.FragmentViewModel

class FragmentViewModelFactory(
    private val repository: Repository,
    private val countryCode: String,
    private val category: String,
    private val KEY: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return FragmentViewModel(repository, countryCode, category, KEY) as T
    }
}