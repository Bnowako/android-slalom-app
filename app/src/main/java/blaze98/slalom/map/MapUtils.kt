package blaze98.slalom.map

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions

class MapUtils(
    private val map: GoogleMap, private val latOffset: Double = 0.001, private val lonOffset: Double = 0.0014
) {


    private fun placeRectangle(latLng: LatLng): Polygon {
        return map.addPolygon(
            PolygonOptions()
                .clickable(true)
                .add(
                    LatLng(latLng.latitude + latOffset, latLng.longitude - lonOffset),
                    LatLng(latLng.latitude + latOffset, latLng.longitude + lonOffset),
                    LatLng(latLng.latitude - latOffset, latLng.longitude + lonOffset),
                    LatLng(latLng.latitude - latOffset, latLng.longitude - lonOffset),
                    LatLng(latLng.latitude + latOffset, latLng.longitude - lonOffset)
                )
        )
    }

    fun placePolygons(locations: MutableList<LatLng>): List<Polygon> {
        return locations.map {
            placeRectangle(it)
        }
    }

    fun placeMarkers(locations: MutableList<LatLng>) {
        locations.forEach {
            addMarker(it)
        }
    }

    private fun addMarker(location: LatLng) {
        map.addMarker(
            MarkerOptions()
                .position(location)
                .title("Monster :o")
        )
    }

}