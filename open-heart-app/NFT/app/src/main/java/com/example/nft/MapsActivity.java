package com.example.nft;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nft.api.Session;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.nft.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.security.Permission;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    ImageView back;
    Button save;
    LatLng rawLoc;
    Session session;
    private FusedLocationProviderClient fusedLocationClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    double latitude, longitude;

    ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {

                } else {
                    finish();
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        back = findViewById(R.id.btn_back_maps);
        save = findViewById(R.id.btn_loc_maps);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = getCompletedAddress(rawLoc);
                session = new Session(MapsActivity.this);
                session.setLocation(location);
                finish();
            }
        });

        if (ContextCompat.checkSelfPermission(
                MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getlocation();

        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);



        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("Your Position"));
        rawLoc = latLng;


    }

    public void getlocation() {
        mLocationRequest = LocationRequest.create()
                .setInterval(500)
                .setFastestInterval(10000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(0);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                } catch (ApiException e) {
                    if (e.getStatusCode() == GeofenceStatusCodes.RESOLUTION_REQUIRED){
                        ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                        try {
                            resolvableApiException.startResolutionForResult(MapsActivity.this, 101);

                        } catch (IntentSender.SendIntentException sendIntentException){
                            sendIntentException.printStackTrace();
                        }
                    }
                    if (e.getStatusCode() == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE){
                        Toast.makeText(MapsActivity.this, "GPS not Available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                System.out.println("get" + locationResult.getLastLocation().getLatitude() +
                        " | " + locationResult.getLastLocation().getLongitude());
                latitude = locationResult.getLastLocation().getLatitude();
                longitude = locationResult.getLastLocation().getLongitude();
                LatLng position = new LatLng(latitude,longitude);

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,14.0f));


            }
        }, Looper.myLooper());
    }


    public String getCompletedAddress(LatLng latLng){
        String ret = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses= geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if(addresses != null){
                Address returnAddress = addresses.get(0);
                StringBuilder stringBuilder = new StringBuilder("");
                for(int i=0; i<= returnAddress.getMaxAddressLineIndex();i++){
                    stringBuilder.append(returnAddress.getAddressLine(i)).append("\n");
                }
                ret = stringBuilder.toString();
            }
        }catch(Exception ex){
            Log.w("CurrentLocation", "Can't get adrress");
        }
        return ret;
    }

    public void refreshActivity() {
        Intent i = new Intent(this, EditProfile.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101) {
            if (resultCode == RESULT_OK){
                Toast.makeText(this, "GPS is Enable", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED){
                finish();
                Toast.makeText(this, "Enable GPS to Use This Feature", Toast.LENGTH_SHORT).show();
            }
        }
    }
}