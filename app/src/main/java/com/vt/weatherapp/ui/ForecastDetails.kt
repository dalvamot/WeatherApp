package com.vt.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vt.weatherapp.R
import com.vt.weatherapp.databinding.FragmentForecastDetailsBinding
import com.vt.weatherapp.model.Forecast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForecastDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForecastDetails : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentForecastDetailsBinding
    private lateinit var forecast: Forecast



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)
        forecast = arguments?.getSerializable(CityForecastFragment.FORECAST) as Forecast


        binding.tempTv.text = "Temperature: " + String.format("%.0f", toFarenheit(forecast.main.temp))
        binding.minTv.text = "MIN: " +String.format("%.0f", toFarenheit(forecast.main.tempMin))
        binding.maxTv.text = "MAX: " + String.format("%.0f", toFarenheit(forecast.main.tempMax))
        binding.humidityTv.text = "Humidity: " + forecast.main.humidity.toString()
        binding.weatherTv.text = "Weather: " + forecast.weather[0].main
        binding.descriptionTv.text = "Description: " + forecast.weather[0].description
        binding.pressureTv.text = "Pressure: " + forecast.main.pressure
        binding.feelsLikeTv.text = "Feels like: " + String.format("%.0f", toFarenheit(forecast.main.feelsLike))
        // Inflate the layout for this fragment
        return binding.root
    }

    fun toFarenheit(kelvin: Double): Double{
        return ( kelvin-273.15)*9/5+32
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForecastDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}