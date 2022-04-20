package com.purnendu.PocketNews.Ui.ViewModel.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.purnendu.PocketNews.Repository
import com.purnendu.PocketNews.Ui.ViewModel.SplashViewModel

class SplashViewModelFactory(
    private val repository: Repository,
    private val countryCode: String,
    private val category: String,
    private val KEY: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return SplashViewModel(repository, countryCode, category, KEY) as T
    }
}