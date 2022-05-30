package blaze98.slalom.map

import android.content.Context
import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import kotlin.random.Random

class MapUtils(
    private val map: GoogleMap, private val context: Context, private val latOffset: Double = 0.001, private val lonOffset: Double = 0.0014
) {

    fun placeFinishArea(userLatLng: LatLng): Polygon {
        val randomCoefficient1 = if (Random.nextBoolean()) -1 else 1
        val randomCoefficient2 = if (Random.nextBoolean()) -1 else 1
        val finishArea = LatLng(userLatLng.latitude + 0.012 * randomCoefficient1, userLatLng.longitude + 0.015 * randomCoefficient2)
        return map.addPolygon(
            PolygonOptions()
                .clickable(true)
                .add(
                    LatLng(finishArea.latitude + latOffset, finishArea.longitude - lonOffset),
                    LatLng(finishArea.latitude + latOffset, finishArea.longitude + lonOffset),
                    LatLng(finishArea.latitude - latOffset, finishArea.longitude + lonOffset),
                    LatLng(finishArea.latitude - latOffset, finishArea.longitude - lonOffset),
                    LatLng(finishArea.latitude + latOffset, finishArea.longitude - lonOffset)
                )
                .strokeColor(Color.parseColor("#fc0303"))
                .strokeWidth(2f)
                .fillColor(Color.argb(20, 50, 255, 100))
        )
    }

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
                .strokeColor(Color.parseColor("#fc0303"))
                .strokeWidth(2f)
                .fillColor(Color.argb(20, 50, 0, 255))
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

    fun changeMapStyle(style: Int) {
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, style))
    }

}