package com.example.a4laba

import kotlin.math.abs
import kotlin.math.pow

data class CalculationResult(
    val sum: Double,
    val lastTerm: Double,
    val iterations: Int
)

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