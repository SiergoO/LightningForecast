package com.sdamashchuk.model

sealed class WeatherType(
    val description: String
) {
    object ClearSky : WeatherType(
        description = "Clear sky"
    )
    object MainlyClear : WeatherType(
        description = "Mainly clear"
    )
    object PartlyCloudy : WeatherType(
        description = "Partly cloudy"
    )
    object Overcast : WeatherType(
        description = "Overcast"
    )
    object Foggy : WeatherType(
        description = "Foggy"
    )
    object DepositingRimeFog : WeatherType(
        description = "Depositing rime fog"
    )
    object LightDrizzle : WeatherType(
        description = "Light drizzle"
    )
    object ModerateDrizzle : WeatherType(
        description = "Moderate drizzle"
    )
    object DenseDrizzle : WeatherType(
        description = "Dense drizzle"
    )
    object LightFreezingDrizzle : WeatherType(
        description = "Slight freezing drizzle"
    )
    object DenseFreezingDrizzle : WeatherType(
        description = "Dense freezing drizzle"
    )
    object SlightRain : WeatherType(
        description = "Slight rain"
    )
    object ModerateRain : WeatherType(
        description = "Rainy"
    )
    object HeavyRain : WeatherType(
        description = "Heavy rain"
    )
    object HeavyFreezingRain: WeatherType(
        description = "Heavy freezing rain"
    )
    object SlightSnowFall: WeatherType(
        description = "Slight snow fall"
    )
    object ModerateSnowFall: WeatherType(
        description = "Moderate snow fall"
    )
    object HeavySnowFall: WeatherType(
        description = "Heavy snow fall"
    )
    object SnowGrains: WeatherType(
        description = "Snow grains"
    )
    object SlightRainShowers: WeatherType(
        description = "Slight rain showers"
    )
    object ModerateRainShowers: WeatherType(
        description = "Moderate rain showers"
    )
    object ViolentRainShowers: WeatherType(
        description = "Violent rain showers"
    )
    object SlightSnowShowers: WeatherType(
        description = "Light snow showers"
    )
    object HeavySnowShowers: WeatherType(
        description = "Heavy snow showers"
    )
    object ModerateThunderstorm: WeatherType(
        description = "Moderate thunderstorm"
    )
    object SlightHailThunderstorm: WeatherType(
        description = "Thunderstorm with slight hail"
    )
    object HeavyHailThunderstorm: WeatherType(
        description = "Thunderstorm with heavy hail"
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when(code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}
