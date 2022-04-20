package com.purnendu.PocketNews.Ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purnendu.PocketNews.Repository
import com.purnendu.PocketNews.RoomDb.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentViewModel(
    private val repository: Repository,
    private val countryCode: String,
    private val category: String,
    private val KEY: String
) : ViewModel() {


    companion object {
        var launchCount: Int = 0
    }

    init {
        if (launchCount == 0)
            launchCount++
        else if (launchCount > 0) {
            launchCount++
            getLiveNews()
        }


    }

    val trendingNewsData: LiveData<List<TrendingNewsTableModel>>
        get() = repository.trendingNewsData

    val techNewsData: LiveData<List<TechNewsTableModel>>
        get() = repository.techNewsData

    val sportsNewsData: LiveData<List<SportsNewsTableModel>>
        get() = repository.sportsNewsData

    val scienceNewsData: LiveData<List<ScienceNewsTableModel>>
        get() = repository.scienceNewsData

    val healthNewsData: LiveData<List<HealthNewsTableModel>>
        get() = repository.healthNewsData

    val entertainmentNewsData: LiveData<List<EntertainmentNewsTableModel>>
        get() = repository.entertainmentNewsData

    val businessNewsData: LiveData<List<BusinessNewsTableModel>>
        get() = repository.businessNewsData


    private fun getLiveNews() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLiveNewsData(
                countryCode,
                category,
                KEY
            )
        }
    }

}