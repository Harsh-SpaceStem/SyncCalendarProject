package com.example.testproject

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract.Events
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


class SyncEventActivity : AppCompatActivity() {

    private val events: ArrayList<com.example.testproject.Events> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_event)

        val button: Button = findViewById(R.id.btn1)
        recyclerView = findViewById(R.id.rv_event)

        button.setOnClickListener {
            addEvent()
        }

        listSelectedCalendars()
        setupRecyclerview()

    }

    private fun setupRecyclerview() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        eventAdapter = EventAdapter(this, events)
        recyclerView.adapter = eventAdapter
    }

    private fun listSelectedCalendars() {
        val projection = arrayOf(
            Events._ID,
            Events.TITLE,
            Events.DESCRIPTION,
            Events.DTSTART,
            Events.DTEND,
            Events.ALL_DAY,
            Events.EVENT_LOCATION
        )

        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val hours: Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = calendar.get(Calendar.MINUTE)
        val startMillis: Long = calendar.run {
            set(year, month, day - 5, hours, minutes)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(year, month, day + 5, hours, minutes)
            timeInMillis
        }

        val selection =
            "(( " + Events.DTSTART + " >= " + startMillis + " ) AND ( " + Events.DTSTART + " <= " + endMillis + " ) AND ( deleted != 1 ))"

        val cursor: Cursor? = contentResolver
            .query(Events.CONTENT_URI, projection, selection, null, null)

        if (cursor != null && cursor.count > 0 && cursor.moveToFirst()) {
            do {
                events.add(
                    Events(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                    )
                )
            } while (cursor.moveToNext())
            cursor.close()
        }
        Log.e("TAG", "listSelectedCalendars: $events")
    }

    private fun addWithoutIntent() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val hours: Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = calendar.get(Calendar.MINUTE)
        val startMillis: Long = calendar.run {
            set(year, month, day, hours, minutes)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run {
            set(year, month, day, hours, minutes + 30)
            timeInMillis
        }
        val event = ContentValues()
        event.put(Events.CALENDAR_ID, 1)

        event.put(Events.TITLE, "Demo Task")
        event.put(Events.DESCRIPTION, "Insert Event To Calendar")
        event.put(Events.EVENT_LOCATION, "office")

        event.put(Events.DTSTART, startMillis)
        event.put(Events.DTEND, endMillis)
        event.put(Events.ALL_DAY, 0) // 0 for false, 1 for true

        event.put(Events.HAS_ALARM, 1) // 0 for false, 1 for true


        val timeZone = TimeZone.getDefault().id
        event.put(Events.EVENT_TIMEZONE, timeZone)

        val baseUri: Uri =
            Uri.parse("content://com.android.calendar/events")

        contentResolver.insert(baseUri, event)
    }

    private fun addEvent() {
        if (checkPermission()) {
            addWithoutIntent()
        }
    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR),
                111
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111) {
            if (grantResults.isNotEmpty()) {
                addEvent()
            }
        }
    }

}