package com.silverbullet.schat

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.silverbullet.core.data.events.EventsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class EventsService : Service(), CoroutineScope {

    @Inject
    lateinit var eventsRepository: EventsRepository

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private var currentJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        currentJob = launch {
            eventsRepository.startEventHandler()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        launch {
            eventsRepository.endEventHandler()
            currentJob?.cancel()
        }
    }
}