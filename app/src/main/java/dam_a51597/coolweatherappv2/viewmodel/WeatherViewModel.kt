package dam_a51597.coolweatherappv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam_a51597.coolweatherappv2.data.WeatherApiClient
import dam_a51597.coolweatherappv2.ui.WeatherUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState = _uiState.asStateFlow()

    fun updateLatitude(newLatitude: Float) {
        _uiState.value = _uiState.value.copy(latitude = newLatitude)
        println("Latitude updated to ${uiState.value.latitude}")
    }

    fun updateLongitude(newLongitude: Float) {
        _uiState.value = _uiState.value.copy(longitude = newLongitude)
        println("Longitude updated to ${uiState.value.longitude}")
    }

    fun fetchWeather() = viewModelScope.launch {
        println("Fetching weather for ${uiState.value.latitude}, ${uiState.value.longitude}")
        val weatherData = WeatherApiClient.getWeather(uiState.value.latitude, uiState.value.longitude)
        if (weatherData != null) {
            _uiState.value = _uiState.value.copy(
                temperature = weatherData.current_weather.temperature,
                windspeed = weatherData.current_weather.windspeed,
                winddirection = weatherData.current_weather.winddirection,
                weathercode = weatherData.current_weather.weathercode,
                seaLevelPressure = weatherData.hourly.pressure_msl.firstOrNull() ?: 0.0,
                time = weatherData.current_weather.time
            )
        }
    }
}