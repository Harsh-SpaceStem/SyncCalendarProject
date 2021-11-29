package com.example.testproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.databinding.ActivityMainBinding
import com.example.testproject.events.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //getAccessToken()
        //insertNewCalendarUsingApi()
        addNewEventToCalendar()
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
        RetrofitBuilder.focusApiServices.insertNewEvent(
            accessToken = getString(R.string.accessToken),
            calId = getString(R.string.creatorTestId),
            eventRequest = EventRequest(
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
        ).enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                val resp = response.body()
                if (resp != null) {
                    val eventData = StringBuilder()
                    eventData.append("\n Event Title : ")
                    eventData.append(resp.summary)
                    eventData.append("\n Event Description : ")
                    eventData.append(resp.description)
                    eventData.append("\n Event Start Time : ")
                    eventData.append(resp.start.dateTime)
                    eventData.append("\n Event End Time : ")
                    eventData.append(resp.end.dateTime)
                    eventData.append("\n Event Location : ")
                    eventData.append(resp.location)
                    binding.btn1.text = eventData
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }


        })
    }
}