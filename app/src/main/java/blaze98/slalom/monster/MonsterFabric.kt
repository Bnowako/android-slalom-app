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

        fun getMonsterLocation(userLocation: Location): LatLng {
            val bounds = getBoundsForLocation(userLocation)
            val lat = Random.nextDouble(bounds.minLat, bounds.maxLat)
            val lon = Random.nextDouble(bounds.minLon, bounds.maxLon)
            return LatLng(lat, lon)
        }

        fun getNMonstersLocations(n: Int,userLocation: Location): MutableList<LatLng> {
            val monstersLocations = mutableListOf<LatLng>()
            val bounds = getBoundsForLocation(userLocation)
            for(i in 0..n){
                monstersLocations.add(getMonsterLocation(bounds))
            }
            return monstersLocations
        }

        private fun getBoundsForLocation(userLocation: Location): Bounds {
            val lat = userLocation!!.latitude
            val lon = userLocation!!.longitude
            val bounds = Bounds(lat - 0.01, lat + 0.01, lon - 0.013, lon + 0.013)
            return bounds
        }
    }
}