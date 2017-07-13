package tn.com.cellcom.cellcomevertek;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tn.com.cellcom.cellcomevertek.entities.Place;
import tn.com.cellcom.cellcomevertek.entities.Shop;
import tn.com.cellcom.cellcomevertek.network.ShopRetrofit;

/**
 * Created by farouk on 06/07/2017.
 */

public class OneShopPage extends AppCompatActivity implements View.OnClickListener {
    private CompositeSubscription mSubscriptions;
    TextView code,firstname,lastname,adresse,city,province;

    String strcode,strfirstname,strlastname,stradresse,strcity,strprovince,strlat,strlang;

    Button btnset;

    boolean btncheckinpressed=false;

    boolean isthere;

    MapView mMapView;
    private GoogleMap googleMap;
    double lat,lng,lat3,lng3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_shop);

        code=(TextView)findViewById(R.id.onecode);
        lastname=(TextView)findViewById(R.id.onelastname);
        firstname=(TextView)findViewById(R.id.onefirstname);
        adresse=(TextView)findViewById(R.id.oneadresse);
        city=(TextView)findViewById(R.id.onecityname);
        province=(TextView)findViewById(R.id.oneprovincename);

        btnset=(Button)findViewById(R.id.btnset);
        btnset.setOnClickListener(this);

        strcode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("code", "defaultStringIfNothingFound");
        strfirstname=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("firstname", "defaultStringIfNothingFound");
        strlastname=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("lastname", "defaultStringIfNothingFound");
        stradresse=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("adresse", "defaultStringIfNothingFound");
        strcity=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("city", "defaultStringIfNothingFound");
        strprovince=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("province", "defaultStringIfNothingFound");

        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("isthere", "defaultStringIfNothingFound").equals("yes")){
            strlat=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("lat", "defaultStringIfNothingFound");
            strlang=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("lang", "defaultStringIfNothingFound");
            isthere=true;
        }else {
            isthere=false;
        }

        code.setText("Code : "+strcode);
        firstname.setText("First Name : "+strfirstname);
        lastname.setText("Last Name : "+strlastname);
        adresse.setText("Adresse : "+stradresse);
        city.setText("City : "+strcity);
        province.setText("Province : "+strprovince);

        mMapView = (MapView) findViewById(R.id.mapView1);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);


                Criteria criteria = new Criteria();

                if (isthere) {

                    double lat1=0.0,lag1=0.0,lat2=0.0,lag2=0.0;

                    LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);
                    String provider = locationManager.getBestProvider(criteria, false);

                    Location location = locationManager.getLastKnownLocation(provider);
                    if(location!=null) {
                        lat1 = location.getLatitude();
                        lag1 = location.getLongitude();
                    }

                    lat2 = Double.parseDouble(strlat);
                    lag2 = Double.parseDouble(strlang);
                    lat=(lat1+lat2)/2;
                    lng=(lag1+lag2)/2;


                    lat3=Double.parseDouble(strlat);
                    lng3= Double.parseDouble(strlang);

                    // For dropping a marker at a point on the Map
                    LatLng sydney = new LatLng(lat, lng);
                    LatLng sydney1 = new LatLng(lat3, lng3);
                    googleMap.addMarker(new MarkerOptions().position(sydney1).title(strcode).snippet(strfirstname));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(6).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }else {

                    btnset.setVisibility(View.VISIBLE);
                    LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);
                    String provider = locationManager.getBestProvider(criteria, false);

                    Location location = locationManager.getLastKnownLocation(provider);
                    if(location!=null){
                        lat =  location.getLatitude();
                        lng = location.getLongitude();
                        LatLng sydney = new LatLng(lat, lng);

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }

                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnset :


                        Toast.makeText(getApplicationContext(),"lat ="+lat+" lng ="+lng,Toast.LENGTH_LONG).show();



                break;



        }
    }


}
