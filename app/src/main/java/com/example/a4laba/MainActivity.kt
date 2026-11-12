package com.example.a4laba

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.math.pow

// Модель результата вычислений
data class CalculationResult(
    val sum: Double,
    val lastTerm: Double,
    val iterations: Int
)

// Вычисление ряда
fun calculateSeries(x: Double, epsilon: Double): CalculationResult? {
    if (x == 0.0) return null

    var sum = 0.0
    var n = 0
    var term: Double

    do {
        val sign = if (n % 2 == 0) 1.0 else -1.0
        val denominator = (2.0 * n + 1.0) * x.pow(2 * n + 1)
        term = sign / denominator
        sum += term
        n++
    } while (abs(term) >= epsilon)

    return CalculationResult(sum, term, n)
}

@Composable
fun AppScreen() {
    var xInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<CalculationResult?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    val epsilon = 0.0001

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Вариант 2", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(12.dp))

        // Формула
        Text(
            "S = 1/x − 1/(3x³) + 1/(5x⁵) − 1/(7x⁷) + 1/(9x⁹) + …",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = xInput,
            onValueChange = { xInput = it },
            label = { Text("x") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val x = xInput.toDoubleOrNull()
                when {
                    x == null -> error = "Ошибка ввода"
                    x == 0.0 -> error = "x ≠ 0"
                    else -> {
                        error = null
                        result = calculateSeries(x, epsilon)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Вычислить")
        }
    }
}