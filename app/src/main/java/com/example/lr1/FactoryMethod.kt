package com.example.lr1

enum class Unit {
    Currency, Distance, Weight, Volume
}

class FactoryMethod {
    companion object {
        fun unit(u: Unit): Converter {
            return when (u) {
                Unit.Currency -> ConverterCurrency()
                Unit.Distance -> ConverterDistance()
                Unit.Weight -> ConverterWeight()
                Unit.Volume -> ConverterVolume()
            }
        }
    }

}