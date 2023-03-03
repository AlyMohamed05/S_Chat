package com.silverbullet.core.network.events

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import javax.inject.Qualifier

val eventsSerializationModule = SerializersModule {
    polymorphic(Event::class) {
        subclass(AddedToChannelEvent::class)
        subclass(ReceivedMessage::class)
        subclass(OnlineStatus::class)
        subclass(UpdatedMessage::class)
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class EventsJsonSerializer