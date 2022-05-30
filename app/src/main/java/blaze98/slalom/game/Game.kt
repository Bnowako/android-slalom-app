package blaze98.slalom.game

import android.content.Context
import android.location.Location
import android.os.Handler
import android.os.Looper
import blaze98.slalom.R
import blaze98.slalom.map.MapUtils
import blaze98.slalom.monster.MonsterFabric
import blaze98.slalom.monster.MonsterFabric.Companion.getMonsterLocation
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
    private var monstersAwake: Boolean = true
    private var iteration: Int = 0
    private lateinit var lastLocation: Location
    val mainHandler = Handler(Looper.getMainLooper())
    private lateinit var finishArea: Polygon


    fun init(location: Location, context: Context) {
        lastLocation = location
        val monsters = MonsterFabric.getNMonstersLocations(10, location)
        mapUtils = MapUtils(mMap, context)
        val polygons = mapUtils.placePolygons(monsters)
        allMonsters.addAll(polygons)

        val userLatLng = LatLng(location.latitude, location.longitude)
        finishArea = mapUtils.placeFinishArea(userLatLng)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLng))
        mainHandler.post(object : Runnable {
            override fun run() {
                iteration += 1
                if(iteration % 2 == 0) addMonster(lastLocation)
                monstersAwake = !monstersAwake
                val style = if (monstersAwake) R.raw.dark_map else R.raw.light_map
                mapUtils.changeMapStyle(style)
                mainHandler.postDelayed(this, 5000)
            }
        })
    }

    fun isUserAlive(currLocation: Location): Boolean {
        lastLocation = currLocation
        if(monstersAwake) {
            var userAlive = true
            val latLng = LatLng(currLocation.latitude, currLocation.longitude)
            allMonsters.forEach {
                if (PolyUtil.containsLocation(latLng, it.points, true)) {
                    userAlive = false
                    return@forEach
                }
            }
            return userAlive
        }
        return true
    }

    fun addMonster(currLocation: Location) {
        val monsterLocation = getMonsterLocation(currLocation)
        val monsterPolygon = mapUtils.placeRectangle(monsterLocation)
        allMonsters.add(monsterPolygon)
    }
}