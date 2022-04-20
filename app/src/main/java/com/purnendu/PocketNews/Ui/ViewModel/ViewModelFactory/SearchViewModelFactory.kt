package com.purnendu.PocketNews.Ui.ViewModel.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.purnendu.PocketNews.Repository
import com.purnendu.PocketNews.Ui.ViewModel.SearchViewModel

class SearchViewModelFactory(private val repository: Repository,
                             val keyWord: String,
                             val KEY: String) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return  SearchViewModel(repository,keyWord,KEY) as T
    }
}