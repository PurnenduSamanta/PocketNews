package com.purnendu.PocketNews.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.purnendu.PocketNews.Repository
import com.purnendu.PocketNews.RoomDb.BookmarksTableModel

class BookmarkViewModel(private val repository: Repository) : ViewModel() {

    val bookMarkList:LiveData<List<BookmarksTableModel>>
    get() = repository.bookmarks

}