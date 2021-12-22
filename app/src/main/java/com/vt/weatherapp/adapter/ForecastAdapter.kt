package com.vt.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vt.weatherapp.R
import com.vt.weatherapp.model.City
import com.vt.weatherapp.model.CityForecast
import com.vt.weatherapp.model.Forecast
import java.util.*

class ForecastAdapter(
    private val forecastDetailsClick: ForecastDetailsClick,
    private var city: City? = null,
    private val forecastList: MutableList<Forecast> = mutableListOf()
) : RecyclerView.Adapter<ForecastViewHolder>() {

    fun updateForecast(cityForecast: CityForecast){
        city = cityForecast.city
        forecastList.clear()
        forecastList.addAll(cityForecast.forecast)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val forecastView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.forecast_items, parent, false)
        return ForecastViewHolder(forecastView)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = forecastList[position]

        holder.weather.text = forecast.weather[0].main
        holder.forecastDate.text = forecast.dtTxt
        holder.temperature.text = forecast.main.temp.toString()

        holder.city.text = String.format(Locale.getDefault(), city?.name + ", " + city?.country)

        holder.itemView.setOnClickListener{
            forecastDetailsClick.moveToForecastDetails(city?.name?: "", forecast)
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}

class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val city: TextView = itemView.findViewById(R.id.city_forecast)
    val weather: TextView = itemView.findViewById(R.id.weather_forecast)
    val temperature: TextView = itemView.findViewById(R.id.forecast_temp)
    val forecastDate: TextView = itemView.findViewById(R.id.orecast_date)
}