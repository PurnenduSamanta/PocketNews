package com.purnendu.PocketNews.Ui.ViewModel.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.purnendu.PocketNews.Repository
import com.purnendu.PocketNews.Ui.ViewModel.BookmarkViewModel


class BookmarkViewModelFactory(private val  repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookmarkViewModel(repository) as T
    }
}