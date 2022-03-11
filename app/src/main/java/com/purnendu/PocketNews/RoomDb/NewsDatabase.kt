package com.purnendu.PocketNews.RoomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TrendingNewsTableModel::class,
        EntertainmentNewsTableModel::class, SportsNewsTableModel::class, BusinessNewsTableModel::class,
        TechNewsTableModel::class, HealthNewsTableModel::class,
        ScienceNewsTableModel::class,BookmarksTableModel::class], version = 1,exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDataBase(context: Context): NewsDatabase {
            if (INSTANCE == null) {
                synchronized(this)
                {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDatabase::class.java,
                        "NewsDb"
                    ).build()
                }
            }
            return INSTANCE!!

        }
    }


}