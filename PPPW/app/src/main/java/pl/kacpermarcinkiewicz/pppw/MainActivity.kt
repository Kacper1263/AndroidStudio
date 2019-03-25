package pl.kacpermarcinkiewicz.pppw

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var place = "1"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar

        actionBar!!.title = ""

        checkPerm()

        place_1.setOnClickListener{
            place = "1"
            runMap()
        }
        place_2.setOnClickListener{
            place = "2"
            runMap()
        }
        place_3.setOnClickListener{
            place = "3"
            runMap()
        }
        map_btn.setOnClickListener {
            place = "all"
            runMap()
        }
        near_btn.setOnClickListener {
            place = "near"
            runMap()
        }

    }

    fun checkPerm() {
        //Toast.makeText(applicationContext,"Aplikacja wymaga uprawnień lokalizacji do pełnej funkcjonalności",Toast.LENGTH_LONG)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                MapsActivity.LOCATION_PERMISSION_REQUEST_CODE
            )

            return
        }
    }

    fun runMap(){
        var aktywnoscMapy = Intent(applicationContext, MapsActivity::class.java)
        startActivity(aktywnoscMapy)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_locations, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.goto_place1){
            place = "1"
            runMap()
        }
        if (item?.itemId == R.id.goto_place2){
            place = "2"
            runMap()
        }
        if (item?.itemId == R.id.goto_place3){
            place = "3"
            runMap()
        }
        if (item?.itemId == R.id.goto_placeAll){
            place = "all"
            runMap()
        }

        return super.onOptionsItemSelected(item)
    }
}
