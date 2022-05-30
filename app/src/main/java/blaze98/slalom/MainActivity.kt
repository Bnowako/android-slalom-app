package blaze98.slalom

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import blaze98.slalom.game.Game
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var locationManager: LocationManager
    private lateinit var mMap: GoogleMap
    private lateinit var game: Game;
    private var initialized = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLocalization()
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


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

    private fun prepareLocationListener() = LocationListener {
        //todo change to check if game is initialized
        if(initialized) {
            println("IS USER ALIVE?")
            println(game.isUserAlive(it))
            println("----")
        }
    }
}