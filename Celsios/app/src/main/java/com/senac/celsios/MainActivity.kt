package com.senac.celsios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.senac.celsios.ui.theme.CelsiosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CelsiosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConverterApp()
                }
            }
        }
    }
}

@Composable
fun ConverterApp() {
    val temperature = remember { mutableStateOf("") }
    val result = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = temperature.value,
            onValueChange = { temperature.value = it },
            label = { Text("Digite a temperatura") },
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = { result.value = convertCelsiusToFahrenheit(temperature.value) },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Converter para Fahrenheit")
        }

        Button(
            onClick = { result.value = convertFahrenheitToCelsius(temperature.value) }
        ) {
            Text("Converter para Celsius")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Resultado: ${result.value}")
    }
}

fun convertCelsiusToFahrenheit(celsius: String): String {
    return try {
        val celsiusValue = celsius.toDouble()
        val fahrenheit = celsiusValue * 1.8 + 32
        "%.2f °F".format(fahrenheit)
    } catch (e: NumberFormatException) {
        "Entrada inválida"
    }
}

fun convertFahrenheitToCelsius(fahrenheit: String): String {
    return try {
        val fahrenheitValue = fahrenheit.toDouble()
        val celsius = (fahrenheitValue - 32) / 1.8
        "%.2f °C".format(celsius)
    } catch (e: NumberFormatException) {
        "Entrada inválida"
    }
}

@Preview(showBackground = true)
@Composable
fun ConverterPreview() {
    CelsiosTheme {
        ConverterApp()
    }
}
