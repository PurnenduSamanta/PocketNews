package com.purnendu.PocketNews.RoomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {


    //Inserting News
    @Insert
    suspend fun insertTrendingNews(trendingNewsTableModel: TrendingNewsTableModel)

    @Insert
    suspend fun insertEntertainmentNews(entertainmentNewsTableModel: EntertainmentNewsTableModel)

    @Insert
    suspend fun insertSportsNews(sportsNewsTableModel: SportsNewsTableModel)

    @Insert
    suspend fun insertBusinessNews(businessNewsTableModel: BusinessNewsTableModel)

    @Insert
    suspend fun insertTechNews(techNewsTableModel: TechNewsTableModel)

    @Insert
    suspend fun insertHealthNews(healthNewsTableModel: HealthNewsTableModel)

    @Insert
    suspend fun insertScienceNews(scienceNewsTableModel: ScienceNewsTableModel)

    @Insert
    suspend fun insertBookmarkNews(bookmarksNewsTableModel: BookmarksTableModel)


    //Get News
    @Query("Select * from TrendingNewsTable")
    fun getTrendingNewsList(): LiveData<List<TrendingNewsTableModel>>

    @Query("Select * from EntertainmentNewsTable")
    fun getEntertainmentNewsList(): LiveData<List<EntertainmentNewsTableModel>>

    @Query("Select * from SportsNewsTable")
    fun getSportsNewsList(): LiveData<List<SportsNewsTableModel>>

    @Query("Select * from BusinessNewsTable")
    fun getBusinessNewsList(): LiveData<List<BusinessNewsTableModel>>

    @Query("Select * from TechNewsTable ")
    fun getTechNewsList(): LiveData<List<TechNewsTableModel>>

    @Query("Select * from HealthNewsTable")
    fun getHealthNewsList(): LiveData<List<HealthNewsTableModel>>

    @Query("Select * from ScienceNewsTable")
    fun getScienceNewsList(): LiveData<List<ScienceNewsTableModel>>

    @Query("Select * from BookmarksTable")
    fun getBookmarksList(): LiveData<List<BookmarksTableModel>>


    //Deleting News Data from Table
    @Query("DELETE FROM TrendingNewsTable")
    suspend fun deleteTrendingNewsData()

    @Query("DELETE FROM EntertainmentNewsTable")
    suspend fun deleteEntertainmentNewsData()

    @Query("DELETE FROM SportsNewsTable")
    suspend fun deleteSportsNewsData()

    @Query("DELETE FROM BusinessNewsTable")
    suspend fun deleteBusinessNewsData()

    @Query("DELETE FROM TechNewsTable")
    suspend fun deleteTechNewsData()

    @Query("DELETE FROM HealthNewsTable")
    suspend fun deleteHealthNewsData()

    @Query("DELETE FROM ScienceNewsTable")
    suspend fun deleteScienceNewsData()

    @Query("DELETE FROM BookmarksTable")
    suspend fun deleteBookmarksData()


    //Deleting first 20 news
    @Query("delete from  TrendingNewsTable where newsUrl in (SELECT newsUrl FROM TrendingNewsTable LIMIT :limit)")
    suspend fun deleteOldNewsFromTrending(limit:Int)

    @Query("delete from  EntertainmentNewsTable where newsUrl in (SELECT newsUrl FROM EntertainmentNewsTable LIMIT :limit)")
    suspend fun deleteOldNewsFromEntertainment(limit:Int)

    @Query("delete from  SportsNewsTable where newsUrl in (SELECT newsUrl FROM SportsNewsTable LIMIT :limit)")
    suspend fun deleteOldNewsFromSports(limit:Int)

    @Query("delete from  BusinessNewsTable where newsUrl in (SELECT newsUrl FROM BusinessNewsTable LIMIT :limit)")
    suspend fun deleteOldNewsFromBusiness(limit:Int)

    @Query("delete from  TechNewsTable where newsUrl in (SELECT newsUrl FROM TechNewsTable LIMIT :limit)")
    suspend fun deleteOldNewsFromTech(limit:Int)

    @Query("delete from  HealthNewsTable where newsUrl in (SELECT newsUrl FROM HealthNewsTable LIMIT :limit)")
    suspend fun deleteOldNewsFromHealth(limit:Int)

    @Query("delete from  ScienceNewsTable where newsUrl in (SELECT newsUrl FROM ScienceNewsTable LIMIT :limit)")
    suspend fun deleteOldNewsFromScience(limit:Int)


    //Get Last News Title
    @Query("SELECT title from TrendingNewsTable where id=(SELECT MAX(id) from TrendingNewsTable)")
    suspend fun getLastTrendingNewsTitle(): String

    @Query("SELECT title from EntertainmentNewsTable where id=(SELECT MAX(id) from EntertainmentNewsTable)")
    suspend fun getLastEntertainmentNewsTitle(): String

    @Query("SELECT title from SportsNewsTable where id=(SELECT MAX(id) from SportsNewsTable)")
    suspend fun getLastSportsNewsTitle(): String

    @Query("SELECT title from BusinessNewsTable where id=(SELECT MAX(id) from BusinessNewsTable)")
    suspend fun getLastBusinessNewsTitle(): String

    @Query("SELECT title from TechNewsTable where id=(SELECT MAX(id) from TechNewsTable)")
    suspend fun getLastTechNewsTitle(): String

    @Query("SELECT title from HealthNewsTable where id=(SELECT MAX(id) from HealthNewsTable)")
    suspend fun getLastHealthNewsTitle(): String

    @Query("SELECT title from ScienceNewsTable where id=(SELECT MAX(id) from ScienceNewsTable)")
    suspend fun getLastScienceNewsTitle(): String


    //Checking Already bookmarked or not
    @Query("SELECT EXISTS(SELECT * FROM BookmarksTable WHERE newsUrl = :url)")
    suspend fun isBookmarkPresent(url: String): Int

    //Getting News Size
    @Query("SELECT COUNT(newsUrl) from TrendingNewsTable")
    suspend fun trendingNewsSize(): Int

    @Query("SELECT COUNT(newsUrl) from EntertainmentNewsTable")
    suspend fun entertainmentNewsSize(): Int

    @Query("SELECT COUNT(newsUrl) from SportsNewsTable")
    suspend fun sportsNewsSize(): Int

    @Query("SELECT COUNT(newsUrl) from BusinessNewsTable")
    suspend fun businessNewsSize(): Int

    @Query("SELECT COUNT(newsUrl) from TechNewsTable")
    suspend fun techNewsSize(): Int

    @Query("SELECT COUNT(newsUrl) from HealthNewsTable")
    suspend fun healthNewsSize(): Int

    @Query("SELECT COUNT(newsUrl) from ScienceNewsTable")
    suspend fun scienceNewsSize(): Int


}