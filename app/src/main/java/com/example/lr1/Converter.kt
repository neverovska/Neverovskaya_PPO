package com.example.lr1

import java.io.Serializable

abstract class Converter : Serializable {
    abstract fun convertFromTo(fromUnit: String, toUnit: String, valueFrom: String) : String
}