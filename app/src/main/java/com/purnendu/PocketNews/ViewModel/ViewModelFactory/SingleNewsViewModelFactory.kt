package com.purnendu.PocketNews.ViewModel.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.purnendu.PocketNews.Repository
import com.purnendu.PocketNews.ViewModel.SingleNewsViewModel

class SingleNewsViewModelFactory(private val  repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SingleNewsViewModel(repository) as T
    }
}