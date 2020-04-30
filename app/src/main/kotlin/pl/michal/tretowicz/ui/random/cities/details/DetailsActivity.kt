package pl.michal.tretowicz.ui.random.cities.details

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_details.*
import pl.michal.tretowicz.R
import pl.michal.tretowicz.util.BackgroundGeocoder


class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TEXT = "text"
        const val EXTRA_COLOR = "color"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)

        toolbar.setBackgroundColor(intent.getIntExtra(EXTRA_COLOR, 0))

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val cityName = intent.getStringExtra(EXTRA_TEXT).orEmpty()
        supportActionBar!!.title = cityName

        toolbar.setNavigationOnClickListener {
            finish()
        }

        supportFragmentManager.beginTransaction().replace(R.id.frame, DetailsFragment.newInstance(cityName)).commit()
    }

}
