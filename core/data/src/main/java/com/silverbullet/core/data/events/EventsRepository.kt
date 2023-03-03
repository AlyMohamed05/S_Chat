package com.silverbullet.core.data.events

interface EventsRepository {

    suspend fun startEventHandler()

    suspend fun endEventHandler()
}