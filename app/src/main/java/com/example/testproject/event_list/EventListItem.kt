package com.example.testproject.event_list

data class EventListItem(
    val created: String,
    val creator: Creator,
    val description: String,
    val end: End,
    val etag: String,
    val eventType: String,
    val htmlLink: String,
    val iCalUID: String,
    val id: String,
    val kind: String,
    val organizer: Organizer,
    val reminders: Reminders,
    val sequence: Int,
    val start: Start,
    val status: String,
    val summary: String,
    val transparency: String,
    val updated: String
)