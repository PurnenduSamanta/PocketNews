package com.purnendu.PocketNews.Ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purnendu.PocketNews.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: Repository,
    private val countryCode: String,
    private val category: String,
    private val KEY: String
) : ViewModel() {


    suspend fun getUpToDateNews() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLiveNewsData(
                countryCode,
                category,
                KEY
            )
        }

    }


}