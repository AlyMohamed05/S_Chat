package com.silverbullet.schat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.core.sync.worker.SynchronizationWorker
import com.silverbullet.core.data.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val preferences: Preferences,
    @ApplicationContext context: Context
) : ViewModel() {

    var isLoading = true
        private set

    init {
        val workManger = WorkManager.getInstance(context)
        workManger.enqueueUniqueWork(
            SynchronizationWorker.SyncWorkName,
            ExistingWorkPolicy.KEEP,
            SynchronizationWorker.buildWorkRequest()
        )
    }

    fun isUserLoggedIn(): Boolean {
        return preferences.isLoggedIn()
    }

    fun onFinishLoading() {
        isLoading = false
    }
}