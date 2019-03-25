package pl.kacpermarcinkiewicz.pppw

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var place = "1"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
