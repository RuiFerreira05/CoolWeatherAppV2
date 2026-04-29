package dam_a51597.coolweatherappv2.ui

data class WeatherUIState(
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val temperature: Float = 0f,
    val windspeed: Float = 0f,
    val winddirection: Int = 0,
    val weathercode: Int = 0,
    val seaLevelPressure: Double = 0.0,
    val time: String = ""
)