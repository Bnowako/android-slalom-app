package blaze98.slalom

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationListener
import android.location.LocationManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import blaze98.slalom.game.Game
import blaze98.slalom.game.GameStatus
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_map_game.*

class MapGame : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var locationManager: LocationManager
    private lateinit var mMap: GoogleMap
    private lateinit var game: Game;
    private var initialized = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLocalization()
        setContentView(R.layout.activity_map_game)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        btnBack.setOnClickListener { this.finish() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        game = Game(mMap)
        val lastLocation = locationManager.getLastKnownLocation(
            locationManager.getBestProvider(Criteria(), true).toString()
        )
        game.init(lastLocation!!, this)
        initialized = true
    }

    private fun setupLocalization() {
        if (permissionsGranted()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 12)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 12)
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val provider = locationManager.getBestProvider(Criteria(), true)
            if (provider != null) {
                val listener = prepareLocationListener()
                locationManager.requestLocationUpdates(provider, 0L, 0f, listener)
            }
        }
    }

    private fun permissionsGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED

    @RequiresApi(Build.VERSION_CODES.O)
    private fun prepareLocationListener() = LocationListener {
        //todo change to check if game is initialized
        if(initialized) {
            println("IS USER ALIVE?")
            val gameStatus = game.validateGame(it)
            if(gameStatus == GameStatus.USER_WON) {
                var mediaPlayer = MediaPlayer.create(this, R.raw.won)
                mediaPlayer.start()
            }
            println(gameStatus)
            println("----")
        }
    }
}