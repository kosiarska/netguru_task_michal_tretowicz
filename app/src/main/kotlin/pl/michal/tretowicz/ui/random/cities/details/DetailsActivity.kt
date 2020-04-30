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


class DetailsActivity : AppCompatActivity(), OnMapReadyCallback {


    companion object {
        const val EXTRA_TEXT = "text"
        const val EXTRA_COLOR = "color"
    }

    private lateinit var mMap: GoogleMap

    private val addressResultReceiver: ResultReceiver = object : ResultReceiver(Handler(Looper.getMainLooper())) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            val address = resultData.getParcelable<LatLng>(getString(R.string.address_result))
            if (address != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(address, 10f))
                val cityName = intent.getStringExtra(EXTRA_TEXT)

                mMap.addMarker(MarkerOptions().position(address).title(cityName))
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)

        toolbar.setBackgroundColor(intent.getIntExtra(EXTRA_COLOR, 0))

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val cityName = intent.getStringExtra(EXTRA_TEXT)
        supportActionBar!!.title = cityName

        toolbar.setNavigationOnClickListener {
            finish()
        }

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

        val cityName = intent.getStringExtra(EXTRA_TEXT)
        val intent = Intent(this, BackgroundGeocoder::class.java)
        intent.putExtra(getString(R.string.location_key), cityName)
        intent.putExtra(getString(R.string.result_receiver_key), addressResultReceiver)
        startService(intent)
    }
}
