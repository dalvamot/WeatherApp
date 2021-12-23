package com.vt.weatherapp.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vt.weatherapp.R
import com.vt.weatherapp.adapter.ForecastAdapter
import com.vt.weatherapp.adapter.ForecastDetailsClick
import com.vt.weatherapp.databinding.FragmentCityForecastBinding
import com.vt.weatherapp.model.CityForecast
import com.vt.weatherapp.model.Forecast
import com.vt.weatherapp.rest.Retrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CityForecastFragment : Fragment(), ForecastDetailsClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentCityForecastBinding
    private var cityName: String? = null
    private lateinit var forecastAdapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        forecastAdapter = ForecastAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityForecastBinding.inflate(inflater, container, false)
        cityName = arguments?.getString(CITY_NAME)

        binding.forecastRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = forecastAdapter
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        cityName?.let {
            Retrofit.getNetworkApi().getForecast(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { forecast -> handleSuccess(forecast)},
                    { throwable -> handleError(throwable)}
                )
        } ?: Toast.makeText(requireContext(), "Please enter a valid city", Toast.LENGTH_LONG).show()


    }

    private fun handleError(throwable: Throwable) {
        Toast.makeText(requireContext(), throwable.localizedMessage, Toast.LENGTH_LONG).show()

    }

    private fun handleSuccess(forecast: CityForecast) {
        forecastAdapter.updateForecast(forecast)
    }

    companion object {
        const val FORECAST = "FORECAST"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityForecastFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun moveToForecastDetails(cityName: String, forecast: Forecast) {
        val bundle = bundleOf(FORECAST to forecast)
        findNavController().navigate(R.id.ForecastDetailsFragment, bundle)
    }
}