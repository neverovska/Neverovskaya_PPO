package com.example.lr1

import java.math.BigDecimal
import java.math.RoundingMode

class   ConverterVolume : Converter() {
    override fun convertFromTo(fromUnit: String, toUnit: String, valueFrom: String): String {

        val convertFrom: BigDecimal = valueFrom.toBigDecimal()
        lateinit var convertTo: BigDecimal

        when (fromUnit) {
            "pint" -> {
                when (toUnit) {
                    "pint" -> convertTo = convertFrom
                    "l" -> convertTo = convertFrom.multiply( 0.568263.toBigDecimal())
                    "ml" -> convertTo = convertFrom.multiply( 568.3.toBigDecimal())
                }
            }
            "l" -> {
                when (toUnit) {
                    "pint" -> convertTo = convertFrom.multiply( 1.75975.toBigDecimal())
                    "l" -> convertTo = convertFrom
                    "ml" -> convertTo = convertFrom.multiply( 1000.toBigDecimal())
                }
            }
            "ml" -> {
                when (toUnit) {
                    "pint" -> convertTo = convertFrom.multiply( 0.00175963.toBigDecimal())
                    "l" -> convertTo = convertFrom.multiply( 0.001.toBigDecimal())
                    "ml" -> convertTo = convertFrom
                }
            }
            else -> convertTo = (0.0).toBigDecimal()
        }
        return convertTo.setScale(15, RoundingMode.HALF_EVEN).toString()
    }
}

