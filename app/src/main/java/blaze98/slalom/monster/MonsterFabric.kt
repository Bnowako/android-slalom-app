package blaze98.slalom.monster

import android.location.Location
import blaze98.slalom.Bounds
import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

class MonsterFabric {

    companion object {
        fun getMonsterLocation(bounds: Bounds): LatLng {
            val lat = Random.nextDouble(bounds.minLat, bounds.maxLat)
            val lon = Random.nextDouble(bounds.minLon, bounds.maxLon)
            return LatLng(lat, lon)
        }

        fun getMonsterLocation(
            userLocation: Location,
            latOffset: Double,
            lonOffset: Double
        ): LatLng {
            val bounds = getBoundsForLocation(userLocation, latOffset, lonOffset)
            val lat = Random.nextDouble(bounds.minLat, bounds.maxLat)
            val lon = Random.nextDouble(bounds.minLon, bounds.maxLon)
            return LatLng(lat, lon)
        }

        fun getNMonstersLocations(n: Int, userLocation: Location): MutableList<LatLng> {
            val monstersLocations = mutableListOf<LatLng>()
            val bounds = getBoundsForLocation(userLocation, 0.01, 0.013)
            for (i in 0..n) {
                monstersLocations.add(getMonsterLocation(bounds))
            }
            return monstersLocations
        }

        fun getNMonstersLocations(
            n: Int,
            userLocation: Location,
            latOffset: Double,
            lonOffset: Double
        ): MutableList<LatLng> {
            val monstersLocations = mutableListOf<LatLng>()
            val bounds = getBoundsForLocation(userLocation, latOffset, lonOffset)
            for (i in 0..n) {
                monstersLocations.add(getMonsterLocation(bounds))
            }
            return monstersLocations
        }

        private fun getBoundsForLocation(
            userLocation: Location,
            latOffset: Double,
            lonOffset: Double
        ): Bounds {
            val lat = userLocation!!.latitude
            val lon = userLocation!!.longitude
            val bounds = Bounds(lat - latOffset, lat + latOffset, lon - lonOffset, lon + lonOffset)
            return bounds
        }
    }
}