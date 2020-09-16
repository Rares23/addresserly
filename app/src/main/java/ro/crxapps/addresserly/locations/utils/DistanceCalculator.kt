package ro.crxapps.addresserly.locations.utils

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class DistanceCalculator {
    fun calculateDistance(firstLat: Double, firstLng: Double, secondLat: Double, secondLng: Double): Double {
        val earthRadius = 6371 // Radius of the earth in km
        val dLat = deg2rad(secondLat-firstLat)
        val dLon = deg2rad(secondLng-firstLng)
        val a =
            sin(dLat/2) * sin(dLat/2) +
                    cos(deg2rad(firstLat)) * cos(deg2rad(secondLat)) *
                    sin(dLon/2) * sin(dLon/2)
        val c = 2 * atan2(sqrt(a), sqrt(1-a))
        return earthRadius * c
    }

    private fun deg2rad(deg: Double): Double {
        return deg * (Math.PI/180)
    }
}