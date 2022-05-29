package blaze98.slalom.game

import android.location.Location
import blaze98.slalom.map.MapUtils
import blaze98.slalom.monster.MonsterFabric
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polygon
import com.google.maps.android.PolyUtil

class Game(
    var mMap: GoogleMap
) {
    private lateinit var mapUtils: MapUtils
    private var allMonsters: MutableList<Polygon> = mutableListOf()

    fun init(location: Location) {
        val monsters = MonsterFabric.getNMonstersLocations(10, location)
        mapUtils = MapUtils(mMap)
        val polygons = mapUtils.placePolygons(monsters)
        allMonsters.addAll(polygons)

        val userLatLng = LatLng(location.latitude, location.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLng))
    }

    fun isUserAlive(currLocation: Location): Boolean {
        var userAlive = true
        val latLng = LatLng(currLocation.latitude, currLocation.longitude)
        allMonsters.forEach {
            if(PolyUtil.containsLocation(latLng, it.points,true)) {
                userAlive = false
                return@forEach
            }
        }
        return userAlive
    }

}