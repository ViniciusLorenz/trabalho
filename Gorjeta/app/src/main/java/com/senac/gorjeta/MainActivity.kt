package com.senac.gorjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.senac.gorjeta.ui.theme.GorjetaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GorjetaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculator()
                }
            }
        }
    }
}

@Composable
fun TipCalculator() {
    var billAmount by remember { mutableStateOf("") }
    var customTipPercentage by remember { mutableStateOf(18f) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = billAmount,
            onValueChange = { billAmount = it },
            label = { Text("Valor da Conta") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)
        )

        Text("Gorjeta Personalizada: ${customTipPercentage.toInt()}%")
        Slider(
            value = customTipPercentage,
            onValueChange = { customTipPercentage = it },
            valueRange = 0f..30f,
            steps = 29
        )

        val tip15 = calculateTip(billAmount, 15)
        val customTip = calculateTip(billAmount, customTipPercentage.toInt())


        val billAmountDouble = billAmount.toDoubleOrNull() ?: 0.0
        val totalWithTip15 = billAmountDouble + (tip15.toDoubleOrNull() ?: 0.0)
        val totalWithCustomTip = billAmountDouble + (customTip.toDoubleOrNull() ?: 0.0)

        Text("Total com Gorjeta 15%: R$ ${String.format("%.2f", totalWithTip15)}")
        Text("Total com Gorjeta Personalizada: R$ ${String.format("%.2f", totalWithCustomTip)}")
    }
}

private fun calculateTip(billAmount: String, tipPercentage: Int): String {
    return try {
        val amount = billAmount.toDouble()
        String.format("%.2f", (amount * tipPercentage / 100))
    } catch (e: NumberFormatException) {
        "0.00"
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview() {
    GorjetaTheme {
        TipCalculator()
    }
}
