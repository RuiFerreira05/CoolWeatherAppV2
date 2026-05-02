package dam_a51597.coolweatherappv2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dam_a51597.coolweatherappv2.R

@Composable
fun WeatherInput(
    latitude: Float,
    longitude: Float,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onUpdateButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var latText by remember { mutableStateOf(latitude.toString()) }
    var lonText by remember { mutableStateOf(longitude.toString()) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = latText,
                onValueChange = {
                    // Filter to allow only valid decimal numbers (optionally signed)
                    if (it.isEmpty() || it == "-" || it.matches(Regex("^-?\\d*(\\.\\d*)?$"))) {
                        latText = it
                        onLatitudeChange(it)
                    }
                },
                label = { Text(stringResource(id = R.string.latitude)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = lonText,
                onValueChange = {
                    // Filter to allow only valid decimal numbers (optionally signed)
                    if (it.isEmpty() || it == "-" || it.matches(Regex("^-?\\d*(\\.\\d*)?$"))) {
                        lonText = it
                        onLongitudeChange(it)
                    }
                },
                label = { Text(stringResource(id = R.string.longitude)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.weight(1f)
            )
        }
        Button(
            onClick = onUpdateButtonClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.update_weather))
        }
    }
}

@Composable
fun InfoCard(
    text: String,
    modifier: Modifier = Modifier,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyLarge,
    fontWeight: FontWeight? = null,
    color: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Unspecified
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = style,
                fontWeight = fontWeight,
                color = color
            )
        }
    }
}

@Composable
fun WeatherDetails(
    temperature: Float,
    windSpeed: Float,
    windDirection: Int,
    weathercode: Int,
    seaLevelPressure: Double,
    time: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoCard(
            text = stringResource(id = R.string.time_format, time),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )
        InfoCard(
            text = stringResource(id = R.string.temperature_format, temperature),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )
        InfoCard(
            text = stringResource(id = R.string.weather_code_format, weathercode),
            modifier = Modifier.fillMaxWidth()
        )
        InfoCard(
            text = stringResource(id = R.string.wind_format, windSpeed, windDirection),
            modifier = Modifier.fillMaxWidth()
        )
        InfoCard(
            text = stringResource(id = R.string.pressure_format, seaLevelPressure),
            modifier = Modifier.fillMaxWidth()
        )
    }
}