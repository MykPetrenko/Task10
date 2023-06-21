package com.example.task5_

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText


class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val forecastButton = findViewById<Button>(R.id.forecast_button)
    val cityEditText = findViewById<EditText>(R.id.city_name)
    val daysEditText= findViewById<EditText>(R.id.days_number)



    daysEditText.addTextChangedListener(object: TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        val input = s.toString().toIntOrNull()
        forecastButton.isEnabled = input != null && input in 1..10
      }
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })

    forecastButton.setOnClickListener {
      val intent = Intent(this, MainActivity2::class.java)
      val cityName = cityEditText.text.toString()
      val daysNumber = daysEditText.text.toString().toInt()
      intent.putExtra("cityName", cityName)
      intent.putExtra("days", daysNumber)
      startActivity(intent)
    }
  }
}

