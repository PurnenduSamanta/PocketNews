package com.purnendu.PocketNews

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.purnendu.PocketNews.Retrofit.ApiServices
import com.purnendu.PocketNews.Retrofit.ResponseHandle
import com.purnendu.PocketNews.Retrofit.ResponseNewsModel
import com.purnendu.PocketNews.RoomDb.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(
    private val apiServices: ApiServices,
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

    private val mutableSearchNewsData =
        MutableLiveData<ResponseHandle<ArrayList<ResponseNewsModel.Article>>>()
    val searchNewsData: LiveData<ResponseHandle<ArrayList<ResponseNewsModel.Article>>>
        get() = mutableSearchNewsData



    //Updating Database
    suspend fun getLiveNewsData(
        countryCode: String,
        category: String,
        KEY: String
    ) {

        if (!Utility.checkConnection(applicationContext))
         return

        val response = apiServices.getCategorisedNews(countryCode, category, KEY)
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

    //checking if last news is equal to or not to last news of recent list response
    private suspend fun isUpToDate(category: String, title: String): Boolean {
        val dao = newsDao
        when (category) {
            NewsCategories.GENERAL.categoryName ->
                if (dao.getLastTrendingNewsTitle() == title)
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

    //Insert news in db
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


    //Search functionalities
    fun search(KeyWord: String, KEY: String) {

        mutableSearchNewsData.postValue(ResponseHandle.Loading())
        if (!Utility.checkConnection(applicationContext))
        {
            mutableSearchNewsData.postValue(ResponseHandle.Error("No Connection"))
            return
        }

        val response = apiServices.getEverythingFromSearch(KeyWord, KEY)
        response.enqueue(object : Callback<ResponseNewsModel> {
            override fun onResponse(
                call: Call<ResponseNewsModel>,
                response: Response<ResponseNewsModel>
            ) {
                if (!response.isSuccessful)
                    return
                if (response.body() == null)
                    return
                val list = response.body()?.articles ?: return
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
                mutableSearchNewsData.postValue(ResponseHandle.Success(newList as ArrayList<ResponseNewsModel.Article>))
            }

            override fun onFailure(call: Call<ResponseNewsModel>, t: Throwable) {
                mutableSearchNewsData.postValue(ResponseHandle.Error(t.message.toString()))
            }
        })
    }

    //Inserting news to Bookmarks
    suspend fun insertBookmark(title: String, newsUrl: String): Int {
        val dao = newsDao
        return if (dao.isBookmarkPresent(newsUrl) == 0) {
            database.newsDao()
                .insertBookmarkNews(BookmarksTableModel(id = 0, title = title, newsUrl = newsUrl))
            1
        } else
            -1
    }

    //Clear all news from db
    suspend fun clearAllNews() {
        val dao = newsDao
        dao.apply {
            deleteTrendingNewsData()
            deleteTechNewsData()
            deleteSportsNewsData()
            deleteScienceNewsData()
            deleteBusinessNewsData()
            deleteEntertainmentNewsData()
            deleteHealthNewsData()
        }
    }

    //Delete all bookmarks from db
    suspend fun clearBookmark() {
        newsDao.deleteBookmarksData()
    }


    //This function will delete last 20 news if news number will be more than 100
    suspend fun deleteOldNews() {
        val dao = database.newsDao()
        val limit = 100
        val deletingNewsNo = 20
        dao.apply {

            if (trendingNewsSize() > limit)
                deleteOldNewsFromTrending(deletingNewsNo)

            if (entertainmentNewsSize() > limit)
                deleteOldNewsFromEntertainment(deletingNewsNo)

            if (businessNewsSize() > limit)
                deleteOldNewsFromBusiness(deletingNewsNo)

            if (healthNewsSize() > limit)
                deleteOldNewsFromHealth(deletingNewsNo)

            if (scienceNewsSize() > limit)
                deleteOldNewsFromScience(deletingNewsNo)

            if (sportsNewsSize() > limit)
                deleteOldNewsFromSports(deletingNewsNo)

            if (techNewsSize() > limit)
                deleteOldNewsFromTech(deletingNewsNo)
        }
    }

    //Change country code and clear Bookmarks and DB
    suspend fun setCountryCode(countryCode: String): Boolean {
        val check: Boolean = Utility.setCountryCode(applicationContext, countryCode)
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

    //Set night mode status
    fun setNightMode(state: Boolean): Boolean = Utility.setNightMode(applicationContext, state)

    //Set night mode status
    fun setJavaScriptStatus(state: Boolean): Boolean =
        Utility.setJavaScriptStatus(applicationContext, state)


}