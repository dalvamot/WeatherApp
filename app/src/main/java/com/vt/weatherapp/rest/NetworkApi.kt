package com.vt.weatherapp.rest

import com.vt.weatherapp.model.CityForecast
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET(WEATHER_FORECAST)
    fun getForecast(
        @Query("q")cityName: String,
        @Query("appid")apiKey: String = API_KEY
    ): Single<CityForecast>



    companion object{
        private const val API_KEY = "65d00499677e59496ca2f318eb68c049"
        private const val WEATHER_FORECAST= "data/2.5/forecast"
        const val BASE_URL = "https://api.openweathermap.org/"
    }
}