package com.purnendu.PocketNews

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsDeletingWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    private val repository = (this.applicationContext as PocketNewsApplication).repository

    override fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteOldNews()
        }
        return Result.success()
    }

}