package pl.kacpermarcinkiewicz.pppw

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import android.support.v4.app.ActivityOptionsCompat



class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var map: GoogleMap

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "GPSPermissionDenied"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var lastLocation: Location

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val actionBar = supportActionBar

        actionBar!!.title = ""

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.map_style_1
                )
            )

            if (!success) {
                Log.e("Mapa", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("Mapa", "Can't find style. Error: ", e)
        }

        val zste = LatLng(49.97307239745034, 19.836487258575385)
        val tesco = LatLng(49.97331729856471, 19.830607184950175)
        val lewiatan = LatLng(49.97468931541608, 19.83366504433204)

        val zoomLevel = 15.0f //This goes up to 21

        if (MainActivity.place == "1"){
            map.addMarker(MarkerOptions().position(zste).title("Szkoła"))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(zste, zoomLevel))
        }
        else if (MainActivity.place == "2"){
            map.addMarker(MarkerOptions().position(tesco).title("Tesco"))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(tesco, zoomLevel))
        }
        else if (MainActivity.place == "3"){
            map.addMarker(MarkerOptions().position(lewiatan).title("Lewiatan"))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(lewiatan, zoomLevel))
        }
        else if (MainActivity.place == "all"){
            map.addMarker(MarkerOptions().position(zste).title("Szkoła"))
            map.addMarker(MarkerOptions().position(tesco).title("Tesco"))
            map.addMarker(MarkerOptions().position(lewiatan).title("Lewiatan"))

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(zste, zoomLevel))
        }
        else if (MainActivity.place == "near"){
            map.addMarker(MarkerOptions().position(zste).title("Szkoła"))
            map.addMarker(MarkerOptions().position(tesco).title("Tesco"))
            map.addMarker(MarkerOptions().position(lewiatan).title("Lewiatan"))

            moveCameraToCurrent()
        }

        setUpMap()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_locations, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.goto_place1){
            MainActivity.place = "1"
            runMap()
        }
        else if (item?.itemId == R.id.goto_place2){
            MainActivity.place = "2"
            runMap()
        }
        else if (item?.itemId == R.id.goto_place3){
            MainActivity.place = "3"
            runMap()
        }
        else if (item?.itemId == R.id.goto_placeAll){
            MainActivity.place = "all"
            runMap()
        }

        return super.onOptionsItemSelected(item)
    }

    fun runMap(){
        var aktywnoscMapy = Intent(applicationContext, MapsActivity::class.java)
        startActivity(aktywnoscMapy)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }


    private fun setUpMap() {
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
           if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               if(sharedPref.getString(PREF_NAME, "") == "denied"){
                   Toast.makeText(applicationContext, "Brak uprawnień do odczytania lokalizacji!", Toast.LENGTH_LONG).show()
                   return
               }
               else ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQUEST_CODE)

                return
            }

        // lokalizacja usera
        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // ostatnia znana lokalizacja
            if (location != null) {
                lastLocation = location
                //val currentLatLng = LatLng(location.latitude, location.longitude)
            }
        }

    }

    fun moveCameraToCurrent(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQUEST_CODE)

            return
        }
        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13f))
            }
        }
    }

    override fun finish(){
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }


}
