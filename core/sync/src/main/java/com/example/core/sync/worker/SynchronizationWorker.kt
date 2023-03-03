package com.example.core.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.silverbullet.core.data.channels.ChannelsRepository
import com.silverbullet.core.data.messages.MessagesRepository
import com.silverbullet.core.data.preferences.Preferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SynchronizationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val preferences: Preferences,
    private val channelsRepository: ChannelsRepository,
    private val messagesRepository: MessagesRepository
) : CoroutineWorker(appContext, workerParams) {

    // TODO: Implement real synchronization
    override suspend fun doWork(): Result {
        if (!preferences.isLoggedIn()) {
            return Result.failure()
        }
        channelsRepository.synchronize()
        messagesRepository.synchronize()
        return Result.success()
    }

    companion object {

        const val SyncWorkName = "s_chat_db_sync_with_network_work"

        fun buildWorkRequest(): OneTimeWorkRequest {
            val constraints = Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            return OneTimeWorkRequestBuilder<SynchronizationWorker>()
                .setConstraints(constraints)
                .build()
        }
    }
}