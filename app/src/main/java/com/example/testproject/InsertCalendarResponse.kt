package com.example.testproject

data class InsertCalendarResponse(
    val conferenceProperties: ConferenceProperties,
    val etag: String,
    val id: String,
    val kind: String,
    val summary: String,
    val timeZone: String
)
