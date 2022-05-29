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
import blaze98.slalom.map.MapUtils
import blaze98.slalom.monster.MonsterFabric
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var locationManager: LocationManager
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupLocalization()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        val lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(Criteria(), true).toString())
        val monsters = MonsterFabric.getNMonstersLocations(10, lastLocation!!)
        val mapUtils = MapUtils(mMap)
        mapUtils.placeMarkers(monsters)

        val userLatLng = LatLng(lastLocation!!.latitude, lastLocation!!.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLng))

    }

    private fun setupLocalization() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 12)
            locationManager = getSystemService(Context.LOCATION_SERVICE)as LocationManager
            val provider = locationManager.getBestProvider(Criteria(), true)
            if(provider != null) {
                val listener = prepareLocationListener()
                locationManager.requestLocationUpdates(provider, 0L, 0f, listener)
            }
        }
    }

    private fun prepareLocationListener() = LocationListener {
        println(" ")
        println(it.latitude)
        println(it.longitude)
        println(" ")
    }
}