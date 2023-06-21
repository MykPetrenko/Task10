package com.example.task5_

import WeatherAdapter
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = "https://api.weatherapi.com/"

class MainActivity2 : AppCompatActivity(), WeatherAdapter.OnItemClickListener {
  private lateinit var recyclerView: RecyclerView
  private lateinit var layoutManager: RecyclerView.LayoutManager
  private lateinit var weatherAdapter: WeatherAdapter
  private var forecastday: List<Forecastday> = emptyList()
  private var detailsFragment: DetailsFragment? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      setContentView(R.layout.recyclerview_layout_land)
      detailsFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as? DetailsFragment
      if (detailsFragment == null) {
        detailsFragment = DetailsFragment()
        supportFragmentManager.beginTransaction()
          .add(R.id.fragment_container, detailsFragment!!)
          .commit()
      }
    } else {
      setContentView(R.layout.recyclerview_layout)
      val goBackButton = findViewById<Button>(R.id.query_another_city_button)
      goBackButton?.setOnClickListener {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
      }
    }

    val city = intent.getStringExtra("cityName")
    Log.d("TAG", "THIS IS THE CITY YOU TYPED IN: $city")
    val days = intent.getIntExtra("days", 1)

    layoutManager = LinearLayoutManager(this)
    recyclerView = findViewById(R.id.recycler_view)
    recyclerView.layoutManager = layoutManager

    weatherAdapter = WeatherAdapter(forecastday, this)
    recyclerView.adapter = weatherAdapter

    getWeatherResponse(city, days + 1)
  }

  private fun getWeatherResponse(city: String?, days: Int?) {
    val retrofitBuilder = Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl(baseUrl)
      .build()
      .create(GetWeatherData::class.java)

    val retrofitData = retrofitBuilder.getData(city, days)

    retrofitData.enqueue(object : Callback<weatherResponse> {
      override fun onResponse(
        call: Call<weatherResponse>,
        response: Response<weatherResponse>
      ) {
        if (response.isSuccessful) {
          val weatherResponse = response.body()
          weatherResponse?.let {
            forecastday = it.forecast.forecastday
            weatherAdapter.updateData(forecastday)
          }
        }
      }

      override fun onFailure(call: Call<weatherResponse>, t: Throwable) {
        Log.d("TAG", "onFailure: " + t.message)
      }
    })
  }

  override fun onItemClick(forecastday: Forecastday) {
    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      detailsFragment?.let {
        it.setForecastday(forecastday)
        it.updateUI(forecastday)
      }
    }
//    else {
//      // Handle item click in portrait orientation (if needed)
//      println("A RECYCLEVIEW ELEMENT GOT CLICKED ON!!!")
//    }
  }
}
