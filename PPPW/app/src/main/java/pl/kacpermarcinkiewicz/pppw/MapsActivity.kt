package pl.kacpermarcinkiewicz.pppw

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val zste = LatLng(49.97307239745034, 19.836487258575385)
        val tesco = LatLng(49.97331729856471, 19.830607184950175)
        val lewiatan = LatLng(49.97468931541608, 19.83366504433204)

        val zoomLevel = 15.0f //This goes up to 21

        if (MainActivity.place == "1"){
            mMap.addMarker(MarkerOptions().position(zste).title("Szkoła"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zste, zoomLevel))
        }
        else if (MainActivity.place == "2"){
            mMap.addMarker(MarkerOptions().position(tesco).title("Tesco"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tesco, zoomLevel))
        }
        else if (MainActivity.place == "3"){
            mMap.addMarker(MarkerOptions().position(lewiatan).title("Lewiatan"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lewiatan, zoomLevel))
        }
        else if (MainActivity.place == "all"){
            mMap.addMarker(MarkerOptions().position(zste).title("Szkoła"))
            mMap.addMarker(MarkerOptions().position(tesco).title("Tesco"))
            mMap.addMarker(MarkerOptions().position(lewiatan).title("Lewiatan"))

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zste, zoomLevel))
        }
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
    }

}
