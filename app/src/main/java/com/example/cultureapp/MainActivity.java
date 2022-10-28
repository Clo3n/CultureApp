package com.example.cultureapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_UPDATE = 30;
    public static final int FAST_UPDATE = 5;
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed, tv_sensor, tv_updates, tv_address, tv_wayPointCount;

    Button btn_newWaypoint, btn_showWaypoint, btn_showMap;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch sw_locationupdates, sw_gps;

    boolean updateOn=false;

    // current location
    Location currentLocation;

    // list of saved locations
    List<Location> savedLocations;

    // Location request
    LocationRequest locationRequest;

    LocationCallback locationCallBack;

    // Google's API for location services.
    FusedLocationProviderClient fusedLocationProviderClient;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_speed = findViewById(R.id.tv_speed);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_address);
        tv_wayPointCount = findViewById(R.id.tv_wayPointCount);

        sw_gps = findViewById(R.id.sw_gps);
        sw_locationupdates = findViewById(R.id.sw_locationsupdates);

        btn_newWaypoint = findViewById(R.id.btn_newWayPoint);
        btn_showWaypoint = findViewById(R.id.btn_showWaypoint);
        btn_showMap = findViewById(R.id.btn_showMap);


        // set all properties of LocationRequest
        //noinspection deprecation
        locationRequest = new LocationRequest();

        //noinspection deprecation
        locationRequest.setInterval(1000 * DEFAULT_UPDATE);

        //noinspection deprecation
        locationRequest.setFastestInterval(1000 * FAST_UPDATE);

        //noinspection deprecation
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        // event when the update interval is met
        locationCallBack = new LocationCallback() {

            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                // save the location
                Location location = locationResult.getLastLocation();
                assert location != null;
                updateUIValues(location);
            }
        };

        btn_newWaypoint.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //get the gps location

                // add the new location to the global list
                MyApplication myApplication = (MyApplication) getApplicationContext();
                savedLocations = myApplication.getMyLocations();
                savedLocations.add(currentLocation);
            }
        });

        btn_showWaypoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShowSavedLocationsList.class);
                startActivity(i);
            }
        });
        btn_showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });


        sw_gps.setOnClickListener(v -> {
            if (sw_gps.isChecked()) {
                // most accurate - use GPS
                //noinspection deprecation
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                tv_sensor.setText("Using GPS sensors");
            } else {
                //noinspection deprecation
                locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                tv_sensor.setText("Using Towers + WIFI");
            }
        });

        sw_locationupdates.setOnClickListener(view -> {
            if (sw_locationupdates.isChecked()) {
                // turn on location tracking
                startLocationUpdates();
            } else {
                // turn off location tracking
                stopLocationUpdates();
                tv_lat.setText("Not tracking location");
                tv_lon.setText("Not tracking location");
                tv_speed.setText("Not tracking location");
                tv_address.setText("Not tracking location");
                tv_accuracy.setText("Not tracking location");
                tv_altitude.setText("Not tracking location");
                tv_sensor.setText("Not tracking location");

                fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
            }
        });

        updateGPS();
    } // End OnCreate

    @SuppressLint("SetTextI18n")
    private void startLocationUpdates() {
        tv_updates.setText("Location is being tracked");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        updateGPS();
    }

    @SuppressLint("SetTextI18n")
    private void stopLocationUpdates() {
        tv_updates.setText("Location is not being tracked");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateGPS();
            } else {
                Toast.makeText(this, "This app requires permission to be granted in order to work properly", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void updateGPS() {
        // get permission to track GPS
        // get current location
        // update UI

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // permission granted

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, (OnSuccessListener) (location) ->{
                // we got permission. Put the values of location into UI
                updateUIValues((Location) location);
                currentLocation = (Location) location;
            });
        } else {
            // permissions not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateUIValues(Location location) {
        // update all of the text
        tv_lat.setText(String.valueOf(location.getLatitude()));
        tv_lon.setText(String.valueOf(location.getLongitude()));
        tv_accuracy.setText(String.valueOf(location.getAccuracy()));

        if (location.hasAltitude()) {
            tv_altitude.setText(String.valueOf(location.getAltitude()));
        } else {
            tv_altitude.setText("Not available");
        }
        if (location.hasSpeed()) {
            tv_altitude.setText(String.valueOf(location.getSpeed()));
        } else {
            tv_altitude.setText("Not available");

        }
        Geocoder geocoder = new Geocoder(MainActivity.this);

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            tv_address.setText(addresses.get(0).getAddressLine(0));
        }
        catch (Exception e){
            tv_address.setText("Unable to get street address");
        }

        MyApplication myApplication = (MyApplication) getApplicationContext();
        savedLocations = myApplication.getMyLocations();

        // show the number of waypoints saved
        tv_wayPointCount.setText(Integer.toString(savedLocations.size()));
    }
}