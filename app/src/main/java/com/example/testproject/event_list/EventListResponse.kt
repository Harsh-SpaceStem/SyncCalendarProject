package com.example.testproject.event_list

data class EventListResponse(
    val accessRole: String,
    val defaultReminders: List<Any>,
    val etag: String,
    val items: List<EventListItem>,
    val kind: String,
    val nextSyncToken: String,
    val summary: String,
    val timeZone: String,
    val updated: String
)