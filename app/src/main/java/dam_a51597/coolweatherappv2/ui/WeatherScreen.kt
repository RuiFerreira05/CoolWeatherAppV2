package dam_a51597.coolweatherappv2.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dam_a51597.coolweatherappv2.data.WMO_WeatherCode
import dam_a51597.coolweatherappv2.data.getWeatherCodeMap
import dam_a51597.coolweatherappv2.viewmodel.WeatherViewModel

@Composable
fun WeatherUI(weatherViewModel: WeatherViewModel = viewModel()) {
    val weatherUIState by weatherViewModel.uiState.collectAsState()
    val latitude = weatherUIState.latitude
    val longitude = weatherUIState.longitude
    val temperature = weatherUIState.temperature
    val windSpeed = weatherUIState.windspeed
    val windDirection = weatherUIState.winddirection
    val weathercode = weatherUIState.weathercode
    val seaLevelPressure = weatherUIState.seaLevelPressure
    val time = weatherUIState.time

    val configuration = LocalConfiguration.current

    val day = time.split("T").lastOrNull()?.startsWith("0") ?: true
    val mapt = getWeatherCodeMap();
    val wImage = when (val wCode = mapt[weathercode]) {
        WMO_WeatherCode.CLEAR_SKY,
        WMO_WeatherCode.MAINLY_CLEAR,
        WMO_WeatherCode.PARTLY_CLOUDY -> if (day) wCode.image + "day" else wCode.image + "night"

        else -> wCode?.image
    }
    val context = LocalContext.current
    val wIcon = context.resources.getIdentifier(
        wImage, "drawable",
        context.packageName
    )

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeWeatherUI(
            wIcon,
            latitude,
            longitude,
            temperature,
            windSpeed,
            windDirection,
            weathercode,
            seaLevelPressure,
            time,
            onLatitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let {
                    weatherViewModel.updateLatitude(it)
                }
            },
            onLongitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let {
                    weatherViewModel.updateLongitude(it)
                }
            },
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            }
        )
    } else {
        PortraitWeatherUI(
            wIcon,
            latitude,
            longitude,
            temperature,
            windSpeed,
            windDirection,
            weathercode,
            seaLevelPressure,
            time,
            onLatitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let {
                    weatherViewModel.updateLatitude(it)
                }
            },
            onLongitudeChange = { newValue ->
                newValue.toFloatOrNull()?.let {
                    weatherViewModel.updateLongitude(it)
                }
            },
            onUpdateButtonClick = {
                weatherViewModel.fetchWeather()
            }
        )
    }
}

@Composable
fun PortraitWeatherUI(
    wIcon: Int,
    latitude: Float,
    longitude: Float,
    temperature: Float,
    windSpeed: Float,
    windDirection: Int,
    weathercode: Int,
    seaLevelPressure: Double,
    time: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onUpdateButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        WeatherInput(
            latitude = latitude,
            longitude = longitude,
            onLatitudeChange = onLatitudeChange,
            onLongitudeChange = onLongitudeChange,
            onUpdateButtonClick = onUpdateButtonClick,
            modifier = Modifier.fillMaxWidth()
        )

        if (wIcon != 0) {
            Image(
                painter = painterResource(id = wIcon),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(150.dp)
            )
        }

        WeatherDetails(
            temperature = temperature,
            windSpeed = windSpeed,
            windDirection = windDirection,
            weathercode = weathercode,
            seaLevelPressure = seaLevelPressure,
            time = time,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun LandscapeWeatherUI(
    wIcon: Int,
    latitude: Float,
    longitude: Float,
    temperature: Float,
    windSpeed: Float,
    windDirection: Int,
    weathercode: Int,
    seaLevelPressure: Double,
    time: String,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onUpdateButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            WeatherInput(
                latitude = latitude,
                longitude = longitude,
                onLatitudeChange = onLatitudeChange,
                onLongitudeChange = onLongitudeChange,
                onUpdateButtonClick = onUpdateButtonClick,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (wIcon != 0) {
            Image(
                painter = painterResource(id = wIcon),
                contentDescription = "Weather Icon",
                modifier = Modifier
                    .size(120.dp)
                    .weight(1f)
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            WeatherDetails(
                temperature = temperature,
                windSpeed = windSpeed,
                windDirection = windDirection,
                weathercode = weathercode,
                seaLevelPressure = seaLevelPressure,
                time = time,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}