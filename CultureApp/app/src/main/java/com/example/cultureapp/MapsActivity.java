package com.example.cultureapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.cultureapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    public static double lonCurrentLocMap, latCurrentLocMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        MyApplication myApplication = (MyApplication) getApplicationContext();

        //Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        LatLng MuzejsFront = new LatLng(myApplication.Dots[0][0], myApplication.Dots[0][1]);
        mMap.addMarker(new MarkerOptions().position(MuzejsFront).title("Muzejs"));

        LatLng MuzejsBack = new LatLng(myApplication.Dots[1][0], myApplication.Dots[1][1]);
        mMap.addMarker(new MarkerOptions().position(MuzejsBack).title("Muzejs"));

        LatLng PilsKalns1 = new LatLng(myApplication.Dots[2][0], myApplication.Dots[2][1]);
        mMap.addMarker(new MarkerOptions().position(PilsKalns1).title("Pilskalns"));

        LatLng PilsKalns2 = new LatLng(myApplication.Dots[3][0], myApplication.Dots[3][1]);
        mMap.addMarker(new MarkerOptions().position(PilsKalns2).title("Pilskalns"));

        LatLng PilsKalns3 = new LatLng(myApplication.Dots[4][0], myApplication.Dots[4][1]);
        mMap.addMarker(new MarkerOptions().position(PilsKalns3).title("Pilskalns"));

        LatLng DurbesEzers = new LatLng(myApplication.Dots[5][0], myApplication.Dots[5][1]);
        mMap.addMarker(new MarkerOptions().position(DurbesEzers).title("Ezera Skatlaukums"));

        LatLng DurbesKaujaPiemineklis = new LatLng(myApplication.Dots[6][0], myApplication.Dots[6][1]);
        mMap.addMarker(new MarkerOptions().position(DurbesKaujaPiemineklis).title("Pieminekli"));

        LatLng Iebrauktuve = new LatLng(myApplication.Dots[7][0], myApplication.Dots[7][1]);
        mMap.addMarker(new MarkerOptions().position(Iebrauktuve).title("Vecais cels"));

        LatLng VecaSkola = new LatLng(myApplication.Dots[8][0], myApplication.Dots[8][1]);
        mMap.addMarker(new MarkerOptions().position(VecaSkola).title("Veca skola"));

        LatLng AKronvaldaMaja = new LatLng(myApplication.Dots[9][0], myApplication.Dots[9][1]);
        mMap.addMarker(new MarkerOptions().position(AKronvaldaMaja).title("test"));

        LatLng AKronvalds = new LatLng(myApplication.Dots[10][0], myApplication.Dots[10][1]);
        mMap.addMarker(new MarkerOptions().position(AKronvalds).title("Atis Kronvalds"));

        LatLng Baznica = new LatLng(myApplication.Dots[11][0], myApplication.Dots[11][1]);
        mMap.addMarker(new MarkerOptions().position(Baznica).title("Baznica"));

        LatLng GaraEka = new LatLng(myApplication.Dots[12][0], myApplication.Dots[12][1]);
        mMap.addMarker(new MarkerOptions().position(GaraEka).title("test"));

        LatLng ZivjuDikis = new LatLng(myApplication.Dots[13][0], myApplication.Dots[13][1]);
        mMap.addMarker(new MarkerOptions().position(ZivjuDikis).title("Durbes Dikis"));

        LatLng KulturasNams = new LatLng(myApplication.Dots[14][0], myApplication.Dots[14][1]);
        mMap.addMarker(new MarkerOptions().position(KulturasNams).title("Kutluras Nams"));

        //LatLng user = new LatLng(latCurrentLocMap, lonCurrentLocMap);
        //mMap.addMarker(new MarkerOptions().position(user).title("User"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(GaraEka));
    }
}