package com.purnendu.PocketNews.Ui.ViewModel.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.purnendu.PocketNews.Repository
import com.purnendu.PocketNews.Ui.ViewModel.SettingViewModel


class SettingViewModelFactory(private val  repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingViewModel(repository) as T
    }
}