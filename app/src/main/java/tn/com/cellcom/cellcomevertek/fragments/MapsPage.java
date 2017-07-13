package tn.com.cellcom.cellcomevertek.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tn.com.cellcom.cellcomevertek.R;
import tn.com.cellcom.cellcomevertek.entities.Shop;
import tn.com.cellcom.cellcomevertek.network.ShopRetrofit;

/**
 * Created by farouk on 06/07/2017.
 */

public class MapsPage extends /*AppCompatActivity*/ android.support.v4.app.Fragment  {


    MapView mMapView;
    private GoogleMap googleMap;
    private CompositeSubscription mSubscriptions;
    List<Shop> shops1;
    double lat,lng;


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSubscriptions = new CompositeSubscription();


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View parentView = inflater.inflate(R.layout.fragment_maps,container,false);

        mMapView = (MapView) parentView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                LocationManager locationManager = (LocationManager) getContext().getSystemService(getActivity().getApplicationContext().LOCATION_SERVICE);
                String provider = locationManager.getBestProvider(criteria, false);

                Location location = locationManager.getLastKnownLocation(provider);
                if(location!=null){
                    lat =  location.getLatitude();
                    lng = location.getLongitude();
                }else {
                    lat=36.8065;
                    lng=10.1815;
                }



                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(lat, lng);

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                if(isNetworkAvailable()){
                    getShops();
                }


            }
        });



        return parentView;
    }

    private void getShops(){
        mSubscriptions.add(ShopRetrofit.getRetrofit().getAllShop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(List<Shop> shops) {
        for (Shop a:
                shops) {
            System.out.println(a.toString());
            Log.e("farouk",a.toString());
        }
        shops1=shops;
        for (int j = 0; j < shops1.size(); j++) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(shops1.get(j).getPlace().getLatitude(),shops1.get(j).getPlace().getLongitude()))
                    .title(shops1.get(j).getCode()+""));
        }
        Toast.makeText(getContext(),shops1.get(0).getPlace().getLatitude()+"",Toast.LENGTH_LONG).show();

    }
    private void handleError(Throwable error) {

        Log.e("farouk","***"+error);
    }




}
