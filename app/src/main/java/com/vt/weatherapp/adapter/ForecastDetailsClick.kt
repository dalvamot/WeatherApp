package com.vt.weatherapp.adapter

import com.vt.weatherapp.model.Forecast

interface ForecastDetailsClick {
    fun moveToForecastDetails(cityName: String, forecast: Forecast)
}