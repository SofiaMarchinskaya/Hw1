package com.sofiamarchinskaya.hw1

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sofiamarchinskaya.hw1.models.NoteRepositoryImpl
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class BackupWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    private val repository: NoteRepository = NoteRepositoryImpl()

    override suspend fun doWork(): Result = coroutineScope {
        Log.d(TAG, "Сохранено заметок: ${repository.count()}")
        return@coroutineScope Result.success()
    }

    companion object {
        const val WORK_NAME = "BACK_UP_WORK"
        private const val TAG = "NOTE_TAG"
    }
}