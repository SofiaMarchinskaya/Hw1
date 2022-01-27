package com.sofiamarchinskaya.hw1

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sofiamarchinskaya.hw1.models.NoteRepositoryImpl
import com.sofiamarchinskaya.hw1.models.framework.NoteRepository
import kotlinx.coroutines.runBlocking

class BackupWorker(
    context: Context,
    workerParameters: WorkerParameters
) :
    Worker(context, workerParameters) {
    private val repository: NoteRepository = NoteRepositoryImpl()
    override fun doWork(): Result = runBlocking {
        Log.d(TAG, "Сохранено заметок: ${repository.count()}")
        return@runBlocking Result.success()
    }

    companion object {
        const val WORK_NAME = "BACK_UP_WORK"
        private const val TAG = "NOTE_TAG"
    }
}