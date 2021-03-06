package blaze98.slalom.game

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.location.Location
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import blaze98.slalom.R
import blaze98.slalom.data.DatabaseHelper
import blaze98.slalom.map.MapUtils
import blaze98.slalom.monster.MonsterFabric
import blaze98.slalom.monster.MonsterFabric.Companion.getMonsterLocation
import blaze98.slalom.monster.MonsterFabric.Companion.getNMonstersLocations
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polygon
import com.google.maps.android.PolyUtil
import java.time.LocalDateTime


class Game(
    var mMap: GoogleMap
) {
    private lateinit var mapUtils: MapUtils
    private var allMonsters: MutableList<Polygon> = mutableListOf()
    private var monstersAwake: Boolean = true
    private var iteration: Int = 0
    private lateinit var writeableDb: SQLiteDatabase
    private lateinit var lastLocation: Location
    val mainHandler = Handler(Looper.getMainLooper())
    private lateinit var finishArea: Polygon
    private var gameOn = false


    fun init(location: Location, context: Context) {
        gameOn = true
        writeableDb = DatabaseHelper(context).writableDatabase
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
                monstersAwake = !monstersAwake
                if (!monstersAwake) {
                    addMonsters(4, location)
                }
                val style = if (monstersAwake) R.raw.dark_map else R.raw.light_map
                mapUtils.changeMapStyle(style)
                mainHandler.postDelayed(this, 10000)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validateGame(currLocation: Location): GameStatus {
        if(!gameOn) return GameStatus.GAME_OFF
        lastLocation = currLocation
        val latLng = LatLng(lastLocation.latitude, lastLocation.longitude)

        if(checkIfUserWon(latLng)) {
            userWon()
            DatabaseHelper.insertHistoryRecord(writeableDb, "Blaze", GameStatus.USER_WON, LocalDateTime.now())
            return GameStatus.USER_WON
        }
        if (monstersAwake && !isUserAlive(latLng)) {
            userDead()
            DatabaseHelper.insertHistoryRecord(writeableDb, "Blaze", GameStatus.USER_DEAD, LocalDateTime.now())
            return GameStatus.USER_DEAD
        }
        return GameStatus.USER_ALIVE
    }

    private fun checkIfUserWon(latLng: LatLng): Boolean {
        if(PolyUtil.containsLocation(latLng, finishArea.points, true)) return true
        return false
    }

    private fun isUserAlive(latLng: LatLng): Boolean {
        var userAlive = true
        allMonsters.forEach {
            if (PolyUtil.containsLocation(latLng, it.points, true)) {
                userAlive = false
                return@forEach
            }
        }
        return userAlive
    }

    fun addMonster(currLocation: Location) {
        val monsterLocation = getMonsterLocation(currLocation, 0.007, 0.009)
        val monsterPolygon = mapUtils.placeRectangle(monsterLocation)
        allMonsters.add(monsterPolygon)
    }

    fun addMonsters(n: Int, currLocation: Location) {
        val monstersLocations = getNMonstersLocations(n ,currLocation, 0.007, 0.009)
        val monstersPolygons = mapUtils.placePolygons(monstersLocations)
        allMonsters.addAll(monstersPolygons)
    }

    private fun userDead() {
        gameOn = false
        mainHandler.removeCallbacksAndMessages(null)
        val style = R.raw.lost_map
        mapUtils.changeMapStyle(style)
    }

    private fun userWon() {
        gameOn = false
        mainHandler.removeCallbacksAndMessages(null)
        mapUtils.removePolygons(allMonsters)
        val style = R.raw.light_map
        mapUtils.changeMapStyle(style)
        allMonsters = mutableListOf()
    }
}