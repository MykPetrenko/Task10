package com.example.task5_


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {
  private var forecastday: Forecastday? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.details_fragment, container, false)
    val dateTextView = view.findViewById<TextView>(R.id.details_date_textview)
    val temperatureTextView = view.findViewById<TextView>(R.id.details_temperature_textview)
    val humidityTextView = view.findViewById<TextView>(R.id.details_humidity_textview)
    val precipitationTextView = view.findViewById<TextView>(R.id.details_precipitation_textview)

    forecastday?.let {
      dateTextView.text = it.date
      temperatureTextView.text = "${it.day.avgtemp_c} °C"
      humidityTextView.text = "${it.day.avghumidity}%"
      precipitationTextView.text = "${it.day.totalprecip_mm} mm"
    }

    return view
  }

  fun setForecastday(forecastday: Forecastday) {
    this.forecastday = forecastday
  }
  fun updateUI(forecastday: Forecastday) {
    view?.apply {
      findViewById<TextView>(R.id.details_date_textview)?.text = forecastday.date
      findViewById<TextView>(R.id.details_temperature_textview)?.text = "${forecastday.day.avgtemp_c} °C"
      findViewById<TextView>(R.id.details_humidity_textview)?.text = "${forecastday.day.avghumidity}%"
      findViewById<TextView>(R.id.details_precipitation_textview)?.text = "${forecastday.day.totalprecip_mm} mm"
    }
  }

}


