package com.example.lr1

import java.math.BigDecimal

class ConverterDistance : Converter() {
    override fun convertFromTo(fromUnit: String, toUnit: String, valueFrom: String): String {

        val convertFrom: BigDecimal = valueFrom.toBigDecimal()
        lateinit var convertTo: BigDecimal

        when (fromUnit) {
            "km" -> {
                when (toUnit) {
                    "km" -> convertTo = convertFrom
                    "m" -> convertTo = convertFrom.multiply( 1000.toBigDecimal())
                    "сm" -> convertTo = convertFrom.multiply( 100000.toBigDecimal())
                    "mi" -> convertTo = convertFrom.multiply( 0.6215.toBigDecimal())
                    "ft" -> convertTo = convertFrom.multiply( 3280.84.toBigDecimal())
                    "inch" -> convertTo = convertFrom.multiply( 39370.1.toBigDecimal())
                }
            }
            "m" -> {
                when (toUnit) {
                    "km" -> convertTo = convertFrom.multiply( 0.001.toBigDecimal())
                    "m" -> convertTo = convertFrom
                    "сm" -> convertTo = convertFrom.multiply( 100.toBigDecimal())
                    "mi" -> convertTo = convertFrom.multiply( 0.000622.toBigDecimal())
                    "ft" -> convertTo = convertFrom.multiply( 3.28084.toBigDecimal())
                    "inch" -> convertTo = convertFrom.multiply( 39.3701.toBigDecimal())
                }
            }
            "сm" -> {
                when (toUnit) {
                    "km" -> convertTo = convertFrom.multiply( 0.00001.toBigDecimal())
                    "m" -> convertTo = convertFrom.multiply( 0.01.toBigDecimal())
                    "сm" -> convertTo = convertFrom
                    "mi" -> convertTo = convertFrom.multiply( 0.000006215.toBigDecimal())
                    "ft" -> convertTo = convertFrom.multiply( 0.03281.toBigDecimal())
                    "inch" -> convertTo = convertFrom.multiply( 0.3937.toBigDecimal())
                }
            }
            "mi" -> {
                when (toUnit) {
                    "km" -> convertTo = convertFrom.multiply( 1.609.toBigDecimal())
                    "m" -> convertTo = convertFrom.multiply( 1609.toBigDecimal())
                    "сm" -> convertTo = convertFrom.multiply( 160900.toBigDecimal())
                    "mi" -> convertTo = convertFrom
                    "ft" -> convertTo = convertFrom.multiply( 5280.toBigDecimal())
                    "inch" -> convertTo = convertFrom.multiply( 63360.toBigDecimal())
                }
            }
            "ft" -> {
                when (toUnit) {
                    "km" -> convertTo = convertFrom.multiply( 0.000304801.toBigDecimal())
                    "m" -> convertTo = convertFrom.multiply( 0.3048.toBigDecimal())
                    "cm" -> convertTo = convertFrom.multiply( 30.48.toBigDecimal())
                    "mi" -> convertTo = convertFrom.multiply( 0.000189394.toBigDecimal())
                    "ft" -> convertTo = convertFrom
                    "inch" -> convertTo = convertFrom.multiply( 12.toBigDecimal())
                }
            }
            "inch" -> {
                when (toUnit) {
                    "km" -> convertTo = convertFrom.multiply( 0.0000254.toBigDecimal())
                    "m" -> convertTo = convertFrom.multiply( 0.0254.toBigDecimal())
                    "cm" -> convertTo = convertFrom.multiply( 2.54.toBigDecimal())
                    "mi" -> convertTo = convertFrom.multiply( 0.0000157828.toBigDecimal())
                    "ft" -> convertTo = convertFrom.multiply(0.083333.toBigDecimal())
                    "inch" -> convertTo = convertFrom
                }
            }
            else -> convertTo = (0.0).toBigDecimal()
        }
        return convertTo.toString()
    }
}