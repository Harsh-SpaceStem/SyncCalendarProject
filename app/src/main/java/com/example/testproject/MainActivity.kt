package com.example.testproject

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testproject.databinding.ActivityMainBinding
import com.example.testproject.event_list.EventListItem
import com.example.testproject.event_list.EventListResponse
import com.example.testproject.events.*
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var eventAdapter: EventListAdapter
    private var events: List<EventListItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //getAccessToken()
        //insertNewCalendarUsingApi()
        binding.btn1.setOnClickListener {
            addNewEventToCalendar()
        }

        getAllEventsFromMyCalendar()
    }

    /*private fun insertNewCalendarUsingApi() {
        RetrofitBuilder.focusApiServices.insertCalendarType(
            InsertCalendarRequest("Creator Test"),
            getString(R.string.calender_api), getString(R.string.accessToken)
        ).enqueue(object : Callback<InsertCalendarResponse> {
            override fun onResponse(
                call: Call<InsertCalendarResponse>,
                response: Response<InsertCalendarResponse>
            ) {
                //binding.authToken.text = response.body().toString()
                Log.e("TAG", "onResponse: $response")
                Log.e("TAG", "onResponse - Body: ${response.body()}")
            }

            override fun onFailure(call: Call<InsertCalendarResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
    }*/

    /*private fun getAccessToken() {
        RetrofitBuilder.focusApiServices.getAccessToken().enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.d("TAG", "onResponse: $response")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }

        })
    }*/


    private fun addNewEventToCalendar() {
        val eventRequest = EventRequest(
            summary = "Creator Test Event",
            description = "This is the Test Event Created By Creator Calendar Tag",
            start = EventTime(
                dateTime = "2021-11-30T15:00:00",
                timeZone = "Asia/Kolkata",
            ),
            end = EventTime(
                dateTime = "2021-11-30T15:30:00",
                timeZone = "Asia/Kolkata",
            ),
            colorId = 7,
            location = "Mondeal Heights, A-Wing, A-105-107, 1st Floor, Sarkhej - Gandhinagar Hwy, Ahmedabad, Gujarat 380015",
            htmlLink = "https://www.linkedin.com/",
            locked = false,
            guestsCanInviteOthers = false,
            guestsCanModify = false,
            guestsCanSeeOtherGuests = false,
            transparency = "opaque",
            recurrence = listOf(
                "RRULE:FREQ=DAILY;UNTIL=20211202;INTERVAL=1"
            ),
            reminders = Reminders(useDefault = false, listOf(RemindersOverRides("popup", 5))),
            attachments = listOf(
                Attachments(
                    fileUrl = "https://docs.google.com/presentation/d/1ggAbhiJgeSZB1dE0ArTDq9PHnStaKLnpf8tkME9SBmY/edit?usp=sharing",
                    title = "My FileName Here",
                    fileId = 1,
                    mimeType = "application/vnd.google-apps.presentation",
                    iconLink = ""
                )
            )
        )
        RetrofitBuilder.focusApiServices.insertNewEvent(
            accessToken = getString(R.string.accessToken),
            calId = getString(R.string.creatorTestId),
            eventRequest = eventRequest
        ).enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                Snackbar.make(binding.root, response.body()!!.status, Snackbar.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }


        })
    }

    private fun getAllEventsFromMyCalendar() {
        RetrofitBuilder.focusApiServices.getEventsList(
            calId = getString(R.string.creatorTestId),
            accessToken = getString(R.string.accessToken)
        ).enqueue(object : Callback<EventListResponse> {
            override fun onResponse(
                call: Call<EventListResponse>,
                response: Response<EventListResponse>
            ) {
                if (response.body() != null) {
                    events = response.body()!!.items
                    if (events.isNotEmpty()) {
                        binding.rvEventsList.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.rvEventsList.setHasFixedSize(true)
                        eventAdapter = EventListAdapter(this@MainActivity, events)
                        binding.rvEventsList.adapter = eventAdapter
                    }
                    val getTimes = events[0].start.dateTime
                    Log.e("TAG", "convertTime: $getTimes we got from server")
                    val timeInMillis = convertTime(getTimes)
                    val timeInReadable = convertTimetoDate(timeInMillis)
                }
            }

            override fun onFailure(call: Call<EventListResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun convertTimetoDate(timeInMillis: Long): String {
        val date = Date()
        date.time = timeInMillis
        val times = SimpleDateFormat("hh aa", Locale.getDefault()).format(date)
        val dates = SimpleDateFormat("EE dd", Locale.getDefault()).format(date)
        Log.e("TAG", "convertTime: $times in Readable Fommat")
        Log.e("TAG", "convertTime: $dates in Readable Fommat")

        return times
    }

    private fun convertTime(getTimes: String): Long {
        var times: Long = 0
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone("GMT")
        val date: Date
        try {
            date = df.parse(getTimes)
            times = date.time
        } catch (e: ParseException) {
            println("Exception is:" + e.message)
            e.printStackTrace()
        }
        Log.e("TAG", "convertTime: $times in Millis")
        return times
    }

}