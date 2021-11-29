package com.example.testproject.events

data class Reminders(
    val useDefault: Boolean,
    val overrides: List<RemindersOverRides>
)