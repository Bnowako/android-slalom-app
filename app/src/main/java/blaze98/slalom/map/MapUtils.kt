package blaze98.slalom.map

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapUtils(val map: GoogleMap) {


    fun placeMarkers(locations: MutableList<LatLng>) {
        locations.forEach {
            addMarker(it)
        }
    }

    fun addMarker(location: LatLng) {
        map.addMarker(
            MarkerOptions()
                .position(location)
                .title("Monster :o")
        )
    }

}