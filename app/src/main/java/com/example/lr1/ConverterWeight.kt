package com.example.lr1

import java.math.BigDecimal
import java.math.RoundingMode

class ConverterWeight : Converter() {
    override fun convertFromTo(fromUnit: String, toUnit: String, valueFrom: String): String {

        val convertFrom: BigDecimal = valueFrom.toBigDecimal()
        lateinit var convertTo: BigDecimal

        when (fromUnit) {
            "kg" -> {
                when (toUnit) {
                    "kg" -> convertTo = convertFrom
                    "g" -> convertTo = convertFrom.multiply( 1000.toBigDecimal())
                    "t" -> convertTo = convertFrom.multiply( 0.001.toBigDecimal())
                    "lb" -> convertTo = convertFrom.multiply( 2.20462.toBigDecimal())
                }
            }
            "g" -> {
                when (toUnit) {
                    "kg" -> convertTo = convertFrom.multiply( 0.001.toBigDecimal())
                    "g" -> convertTo = convertFrom
                    "t" -> convertTo = convertFrom.multiply( 0.000001.toBigDecimal())
                    "lb" -> convertTo = convertFrom.multiply( 0.00220459.toBigDecimal())
                }
            }
            "t" -> {
                when (toUnit) {
                    "kg" -> convertTo = convertFrom.multiply( 1000.toBigDecimal())
                    "g" -> convertTo = convertFrom.multiply( 1000000.toBigDecimal())
                    "t" -> convertTo = convertFrom
                    "lb" -> convertTo = convertFrom.multiply( 2204.62.toBigDecimal())
                }
            }
            "lb" -> {
                when (toUnit) {
                    "kg" -> convertTo = convertFrom.multiply( 0.453593.toBigDecimal())
                    "g" -> convertTo = convertFrom.multiply( 453.6.toBigDecimal())
                    "t" -> convertTo = convertFrom.multiply( 0.000453593.toBigDecimal())
                    "lb" -> convertTo = convertFrom
                }
            }
            else -> convertTo = (0.0).toBigDecimal()
        }
        return convertTo.setScale(15, RoundingMode.HALF_EVEN).toString()
    }
}