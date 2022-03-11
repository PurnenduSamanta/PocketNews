package com.purnendu.PocketNews

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.purnendu.PocketNews.Retrofit.Api
import com.purnendu.PocketNews.Retrofit.ResponseNewsModel
import com.purnendu.PocketNews.RoomDb.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(
    private val api: Api,
    private val database: NewsDatabase,
    private val applicationContext: Context
) {

    private val newsDao = database.newsDao()
    val trendingNewsData: LiveData<List<TrendingNewsTableModel>>
        get() = newsDao.getTrendingNewsList()
    val entertainmentNewsData: LiveData<List<EntertainmentNewsTableModel>>
        get() = newsDao.getEntertainmentNewsList()
    val sportsNewsData: LiveData<List<SportsNewsTableModel>>
        get() = newsDao.getSportsNewsList()
    val businessNewsData: LiveData<List<BusinessNewsTableModel>>
        get() = newsDao.getBusinessNewsList()
    val techNewsData: LiveData<List<TechNewsTableModel>>
        get() = newsDao.getTechNewsList()
    val healthNewsData: LiveData<List<HealthNewsTableModel>>
        get() = newsDao.getHealthNewsList()
    val scienceNewsData: LiveData<List<ScienceNewsTableModel>>
        get() = newsDao.getScienceNewsList()
    val bookmarks: LiveData<List<BookmarksTableModel>>
        get() = newsDao.getBookmarksList()

    private val mutableSearchNewsData = MutableLiveData<ArrayList<ResponseNewsModel.Article>>()
    val searchNewsData: LiveData<ArrayList<ResponseNewsModel.Article>>
        get() = mutableSearchNewsData


    suspend fun getLiveNewsData(countryCode: String, category: String, KEY: String) {
        if (Utility.checkConnection(applicationContext)) {
            CoroutineScope(Dispatchers.IO).launch {
                doNetworkCallAndUpdateDatabase(countryCode, category, KEY)
            }
        }
    }


    private suspend fun doNetworkCallAndUpdateDatabase(
        countryCode: String,
        category: String,
        KEY: String
    ) {
        val response = api.getCategorisedNews(countryCode, category, KEY)
        response.enqueue(object : Callback<ResponseNewsModel> {
            override fun onResponse(
                call: Call<ResponseNewsModel>,
                response: Response<ResponseNewsModel>
            ) {

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val newsList = response.body()?.articles
                        CoroutineScope(Dispatchers.IO).launch {
                            if (!isUpToDate(
                                    category,
                                    newsList?.get((newsList.size) - 1)?.title!!
                                )
                            ) {
                                for (index in newsList.indices)
                                    insertNewsInDatabase(
                                        category,
                                        newsList[index].title!!,
                                        newsList[index].description!!,
                                        newsList[index].url!!,
                                        newsList[index].urlToImage!!,
                                        newsList[index].publishedAt!!
                                    )
                            }

                        }
                    }
                }

            }

            override fun onFailure(call: Call<ResponseNewsModel>, t: Throwable) {
            }

        })

    }

    private suspend fun isUpToDate(category: String, title: String): Boolean {
        val dao = newsDao
        when (category) {
            NewsCategories.GENERAL.categoryName -> if (dao.getLastTrendingNewsTitle() == title)
                return true
            NewsCategories.TECHNOLOGY.categoryName ->
                if (dao.getLastTechNewsTitle() == title)
                    return true
            NewsCategories.SPORTS.categoryName
            -> if (dao.getLastSportsNewsTitle() == title)
                return true
            NewsCategories.SCIENCE.categoryName
            -> if (dao.getLastScienceNewsTitle() == title)
                return true
            NewsCategories.HEALTH.categoryName
            -> if (dao.getLastHealthNewsTitle() == title)
                return true
            NewsCategories.ENTERTAINMENT.categoryName
            -> if (dao.getLastEntertainmentNewsTitle() == title)
                return true
            NewsCategories.BUSINESS.categoryName
            -> if (dao.getLastBusinessNewsTitle() == title)
                return true
        }
        return false
    }

    private suspend fun insertNewsInDatabase(
        category: String,
        title: String,
        description: String,
        newsUrl: String,
        imageUrl: String,
        date: String
    ) {
        val newsDao = newsDao
        val formattedDate = Utility.getProperDateInFormat(date)
        when (category) {
            NewsCategories.GENERAL.categoryName ->
                newsDao.insertTrendingNews(
                    TrendingNewsTableModel(
                        id = 0,
                        title = title,
                        description = description,
                        newsUrl = newsUrl,
                        imageUrl = imageUrl,
                        date = formattedDate
                    )
                )
            NewsCategories.TECHNOLOGY.categoryName -> newsDao.insertTechNews(
                TechNewsTableModel(
                    id = 0,
                    title = title,
                    description = description,
                    newsUrl = newsUrl,
                    imageUrl = imageUrl,
                    date = formattedDate
                )
            )
            NewsCategories.SPORTS.categoryName -> newsDao.insertSportsNews(
                SportsNewsTableModel(
                    id = 0,
                    title = title,
                    description = description,
                    newsUrl = newsUrl,
                    imageUrl = imageUrl,
                    date = formattedDate
                )
            )
            NewsCategories.SCIENCE.categoryName -> newsDao.insertScienceNews(
                ScienceNewsTableModel(
                    id = 0,
                    title = title,
                    description = description,
                    newsUrl = newsUrl,
                    imageUrl = imageUrl,
                    date = formattedDate
                )
            )
            NewsCategories.HEALTH.categoryName -> newsDao.insertHealthNews(
                HealthNewsTableModel(
                    id = 0,
                    title = title,
                    description = description,
                    newsUrl = newsUrl,
                    imageUrl = imageUrl,
                    date = formattedDate
                )
            )
            NewsCategories.ENTERTAINMENT.categoryName -> newsDao.insertEntertainmentNews(
                EntertainmentNewsTableModel(
                    id = 0,
                    title = title,
                    description = description,
                    newsUrl = newsUrl,
                    imageUrl = imageUrl,
                    date = formattedDate
                )
            )
            NewsCategories.BUSINESS.categoryName -> newsDao.insertBusinessNews(
                BusinessNewsTableModel(
                    id = 0,
                    title = title,
                    description = description,
                    newsUrl = newsUrl,
                    imageUrl = imageUrl,
                    date = formattedDate
                )
            )
        }
    }

    fun search(KeyWord: String, KEY: String) {

        if (!Utility.checkConnection(applicationContext))
            return
        val response = api.getEverythingFromSearch(KeyWord, KEY)
        response.enqueue(object : Callback<ResponseNewsModel> {
            override fun onResponse(
                call: Call<ResponseNewsModel>,
                response: Response<ResponseNewsModel>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val list = response.body()?.articles
                        if (list != null) {
                            val newList = mutableListOf<ResponseNewsModel.Article>()
                            for (e in list) {
                                val formattedDate = Utility.getProperDateInFormat(e.publishedAt)
                                newList.add(
                                    ResponseNewsModel.Article(
                                        e.title,
                                        e.description,
                                        e.urlToImage,
                                        e.url,
                                        formattedDate
                                    )
                                )
                            }
                            mutableSearchNewsData.postValue(newList as ArrayList<ResponseNewsModel.Article>)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ResponseNewsModel>, t: Throwable) {

            }
        })
    }

    suspend fun insertBookmark(title: String, newsUrl: String): Int {
        val dao = newsDao
        return if (dao.isBookmarkPresent(newsUrl) == 0) {
            database.newsDao()
                .insertBookmarkNews(BookmarksTableModel(id = 0, title = title, newsUrl = newsUrl))
            1
        } else
            -1
    }

    suspend fun clearAllNews() {
        val dao = newsDao
        dao.deleteTrendingNewsData()
        dao.deleteTechNewsData()
        dao.deleteSportsNewsData()
        dao.deleteScienceNewsData()
        dao.deleteBusinessNewsData()
        dao.deleteEntertainmentNewsData()
        dao.deleteHealthNewsData()
    }

    suspend fun clearBookmark() {
        newsDao.deleteBookmarksData()
    }


    suspend fun deleteOldNews() {
        val dao = database.newsDao()
        val limit = 100
        val deletingNewsNo = 20
        if (dao.trendingNewsSize() > limit)
            dao.deleteOldNewsFromTrending(deletingNewsNo)

        if (dao.entertainmentNewsSize() > limit)
            dao.deleteOldNewsFromEntertainment(deletingNewsNo)

        if (dao.businessNewsSize() > limit)
            dao.deleteOldNewsFromBusiness(deletingNewsNo)

        if (dao.healthNewsSize() > limit)
            dao.deleteOldNewsFromHealth(deletingNewsNo)

        if (dao.scienceNewsSize() > limit)
            dao.deleteOldNewsFromScience(deletingNewsNo)

        if (dao.sportsNewsSize() > limit)
            dao.deleteOldNewsFromSports(deletingNewsNo)

        if (dao.techNewsSize() > limit)
            dao.deleteOldNewsFromTech(deletingNewsNo)
    }

    suspend fun setCountryCode(CountryCode: String): Boolean {
        val preference = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString("country", CountryCode)
        val check = editor.commit()
        return if (check) {
            CoroutineScope(Dispatchers.IO).launch {
                clearAllNews()
                clearBookmark()
            }.join()
            true
        } else {
            false
        }
    }

    fun setNightMode(state: Boolean): Boolean {
        val editor: SharedPreferences.Editor =
            applicationContext.getSharedPreferences("switch", Context.MODE_PRIVATE).edit()
        editor.putBoolean("nightMode", state)
        return editor.commit()
    }

    fun setJavaScriptStatus(state: Boolean): Boolean {
        val editor: SharedPreferences.Editor =
            applicationContext.getSharedPreferences("javaScriptSwitch", Context.MODE_PRIVATE).edit()
        editor.putBoolean("js", state)
        return editor.commit()
    }


}