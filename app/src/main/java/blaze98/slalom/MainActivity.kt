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


class MainActivity : AppCompatActivity() {
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupLocalization()

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