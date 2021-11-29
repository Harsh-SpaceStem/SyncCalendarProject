package com.example.testproject.events

data class EventRequest(
    val summary: String,
    val description: String,
    val start: EventTime,
    val end: EventTime,
    val colorId: Int,
    val location: String,
    val htmlLink: String,
    val locked: Boolean,
    val guestsCanInviteOthers: Boolean,
    val guestsCanModify: Boolean,
    val guestsCanSeeOtherGuests: Boolean,
    val transparency: String,
    val recurrence: List<String>,
    val reminders: Reminders,
    val attachments: List<Attachments>,
)