package com.example.vehiclemaintenanceapplication.Backend

import com.google.gson.annotations.SerializedName

data class CarDataGet (
    @SerializedName("datetime") val datetime : String,
    @SerializedName("speed") val speed : Double,
    @SerializedName("engine_rpm") val rpm : Double,
    @SerializedName("engine_runtime") val engineRuntime : Int,
    @SerializedName("distance_travelled") val dist : Double,
    @SerializedName("engine_coolant_temperature") val coolant : Double,
    @SerializedName("short_term_fuel_trim_1") val shortfueltrim1 : Int,
    @SerializedName("short_term_fuel_trim_2") val shortfueltrim2 : Int,
    @SerializedName("long_term_fuel_trim_1") val longfueltrim1 : Int,
    @SerializedName("long_term_fuel_trim_2") val longfueltrim2 : Int,
    @SerializedName("fuel_tank_level_input") val fuel_level : Int,
    @SerializedName("fuel_pressure") val fuelPressure : Int,
    @SerializedName("engine_oil_temperature") val oilTemp : Int,
    @SerializedName("o2_bank_1_voltage") val o2bank1 : Double,
    @SerializedName("o2_bank_2_voltage") val o2bank2 : Double,
    @SerializedName("gyroscope_x_axis") val gyrox : Double,
    @SerializedName("gyroscope_y_axis") val gyroy : Double,
    @SerializedName("gyroscope_z_axis") val gyroz : Double,
    @SerializedName("accelerometer_x_axis") val accelx : Double,
    @SerializedName("accelerometer_y_axis") val accely : Double,
    @SerializedName("accelerometer_z_axis") val accelz : Double



    )