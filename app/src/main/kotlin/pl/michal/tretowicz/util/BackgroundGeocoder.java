package pl.michal.tretowicz.util;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import pl.michal.tretowicz.R;

public class BackgroundGeocoder extends IntentService {

    public BackgroundGeocoder() {
        super(BackgroundGeocoder.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver resultReceiver = intent.getParcelableExtra(getString(R.string.result_receiver_key));
        String location = intent.getStringExtra(getString(R.string.location_key));
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 1);
            if (!addresses.isEmpty()) {
                Bundle bundle = new Bundle();
                try {
                    bundle.putParcelable(getString(R.string.address_result), new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude()));
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
                resultReceiver.send(1, bundle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}