package com.purnendu.PocketNews.Ui.ViewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.purnendu.PocketNews.NewsCategories

class MainViewModel : ViewModel() {

    var swipe: String = NewsCategories.GENERAL.categoryName
    var fragment: Fragment? = null

    fun changeSwipe(category: String) {
        swipe = category
    }

    fun changeFragment(categoryFragment: Fragment?) {
        fragment = categoryFragment
    }

}