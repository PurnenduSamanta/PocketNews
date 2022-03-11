package com.purnendu.PocketNews.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.purnendu.PocketNews.Repository
import com.purnendu.PocketNews.Retrofit.ResponseNewsModel

class SearchViewModel(
    private val repository: Repository,
    keyWord: String,
    KEY: String
) : ViewModel() {

    val searchResult: LiveData<ArrayList<ResponseNewsModel.Article>>
        get() = repository.searchNewsData

    init {
        repository.search(keyWord, KEY)
    }
}