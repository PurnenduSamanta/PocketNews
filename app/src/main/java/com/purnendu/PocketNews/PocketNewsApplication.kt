package com.purnendu.PocketNews

import android.app.Application
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.purnendu.PocketNews.Retrofit.Api
import com.purnendu.PocketNews.Retrofit.Client
import com.purnendu.PocketNews.RoomDb.NewsDatabase.Companion.getDataBase
import java.util.concurrent.TimeUnit


class PocketNewsApplication: Application() {


    lateinit var repository: Repository
    override fun onCreate() {
        super.onCreate()

        //Creating repository Object
        val database = getDataBase(applicationContext)
        val api = Client.getRetrofitInstance().create(Api::class.java)
        repository = Repository(api, database, applicationContext)

        //SetUp workManager for deleting old news
        setWorkManager()

    }

    private fun setWorkManager() {
       val  workManager:WorkManager = WorkManager.getInstance(this)
       val workRequest = PeriodicWorkRequest.Builder(NewsDeletingWorker::class.java, 3, TimeUnit.DAYS).build()
        workManager.enqueue(workRequest)
    }

}