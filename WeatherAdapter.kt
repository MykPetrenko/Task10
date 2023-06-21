import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task5_.Forecastday
import com.example.task5_.R
import com.squareup.picasso.Picasso

class WeatherAdapter(
  private var forecastDays: List<Forecastday>,
  private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

  // Интерфейс для клика
  interface OnItemClickListener {
    fun onItemClick(forecastday: Forecastday)
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val dateTextView: TextView = itemView.findViewById(R.id.date_textview)
    private val temperatureValueTextView: TextView =
      itemView.findViewById(R.id.temperature_value_textview)
    private val humidityValueTextView: TextView =
      itemView.findViewById(R.id.humidity_value_textview)
    private val precipitationValueTextView: TextView =
      itemView.findViewById(R.id.precipitation_value_textview)
    private val weatherIcon: ImageView = itemView.findViewById(R.id.weatherIcon)

    init {
      itemView.setOnClickListener {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
          val forecastday = forecastDays[position]
          itemClickListener.onItemClick(forecastday)
        }
      }
    }

    fun bind(forecastday: Forecastday) {
      dateTextView.text = forecastday.date
      temperatureValueTextView.text = "${forecastday.day.avgtemp_c} °C"
      humidityValueTextView.text = "${forecastday.day.avghumidity}%"
      precipitationValueTextView.text = "${forecastday.day.totalprecip_mm} mm"
      Picasso.get()
        .load("https:${forecastday.day.condition.icon}")
        .into(weatherIcon)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.recyclerview_item_layout, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val forecastday = forecastDays[position]
    holder.bind(forecastday)
  }

  override fun getItemCount(): Int = forecastDays.size

  fun updateData(newForecastday: List<Forecastday>) {
    forecastDays = newForecastday
    notifyDataSetChanged()
  }
}
