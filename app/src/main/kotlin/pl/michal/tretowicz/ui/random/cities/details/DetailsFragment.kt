package pl.michal.tretowicz.ui.random.cities.details

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pl.michal.tretowicz.R
import pl.michal.tretowicz.ui.base.BaseFragment
import pl.michal.tretowicz.util.BackgroundGeocoder
import pl.michal.tretowicz.util.extension.FragmentArgumentDelegate
import pl.michal.tretowicz.util.extension.WeatherCityFragment


class DetailsFragment : BaseFragment(), OnMapReadyCallback {

    private var cityName by FragmentArgumentDelegate<String>()

    companion object {
        fun newInstance(cityName: String) = DetailsFragment().apply {
            this.cityName = cityName
        }
    }

    private lateinit var mMap: GoogleMap

    override fun getLayoutResId() = R.layout.fragment_details

    private val addressResultReceiver: ResultReceiver = object : ResultReceiver(Handler(Looper.getMainLooper())) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            val address = resultData.getParcelable<LatLng>(getString(R.string.address_result))
            if (address != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(address, 10f))
                mMap.addMarker(MarkerOptions().position(address).title(cityName))
            }
        }
    }

    override fun attachView() {
        super.attachView()
        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val intent = Intent(context, BackgroundGeocoder::class.java)
        intent.putExtra(getString(R.string.location_key), cityName)
        intent.putExtra(getString(R.string.result_receiver_key), addressResultReceiver)
        activity?.startService(intent)
    }

}