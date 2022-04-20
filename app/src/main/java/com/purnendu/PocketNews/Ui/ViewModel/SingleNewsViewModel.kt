package com.purnendu.PocketNews.Ui.ViewModel


import androidx.lifecycle.ViewModel
import com.purnendu.PocketNews.Repository

class SingleNewsViewModel(private val repository: Repository) : ViewModel() {


    suspend fun insertBookmark(title:String, newsUrl:String):Int
    {
      return  repository.insertBookmark(title,newsUrl)
    }


}