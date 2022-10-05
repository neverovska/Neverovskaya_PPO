package com.example.lr1

import com.example.lr1.Unit
import java.math.BigDecimal

class ConverterCurrency : Converter() {

    override fun convertFromTo(fromUnit: String, toUnit: String, valueFrom: String): String {

        val convertFrom: BigDecimal = valueFrom.toBigDecimal()
        lateinit var convertTo: BigDecimal

        when (fromUnit) {
            "BYN" -> {
                when (toUnit) {
                    "BYN" -> convertTo = convertFrom
                    "USD" -> convertTo = convertFrom.multiply( 0.3948.toBigDecimal())
                    "EUR" -> convertTo = convertFrom.multiply(0.3967.toBigDecimal())
                    "PLN" -> convertTo = convertFrom.multiply( 1.85967.toBigDecimal())
                }
            }
            "USD" -> {
                when (toUnit) {
                    "BYN" -> convertTo = convertFrom.multiply(2.5327.toBigDecimal())
                    "USD" -> convertTo = convertFrom
                    "EUR" -> convertTo = convertFrom.multiply( 0.99.toBigDecimal())
                    "PLN" -> convertTo = convertFrom.multiply( 4.77.toBigDecimal())
                }
            }
            "EUR" -> {
                when (toUnit) {
                    "BYN" -> convertTo = convertFrom.multiply( 2.5403.toBigDecimal())
                    "USD" -> convertTo = convertFrom.multiply(1.01.toBigDecimal())
                    "EUR" -> convertTo = convertFrom
                    "PLN" -> convertTo = convertFrom.multiply( 4.79.toBigDecimal())
                }
            }
            "PLN" -> {
                when (toUnit) {
                    "BYN" -> convertTo = convertFrom.multiply( 0.53773.toBigDecimal())
                    "USD" -> convertTo = convertFrom.multiply( 0.2096.toBigDecimal())
                    "EUR" -> convertTo = convertFrom.multiply( 0.2088.toBigDecimal())
                    "PLN" -> convertTo = convertFrom
                }
            }
            else -> convertTo = (0.0).toBigDecimal()
        }
        return convertTo.toString()
    }
}