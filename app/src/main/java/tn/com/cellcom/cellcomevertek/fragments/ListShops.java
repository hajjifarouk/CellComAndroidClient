package tn.com.cellcom.cellcomevertek.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tn.com.cellcom.cellcomevertek.Adapter.ShopsAdapter;
import tn.com.cellcom.cellcomevertek.OneShopPage;
import tn.com.cellcom.cellcomevertek.R;
import tn.com.cellcom.cellcomevertek.entities.Shop;
import tn.com.cellcom.cellcomevertek.network.ShopRetrofit;

/**
 * Created by farouk on 06/07/2017.
 */

public class ListShops extends /*AppCompatActivity*/ android.support.v4.app.Fragment {
    ListView listViewTimeline;
    private CompositeSubscription mSubscriptions;
    private ShopsAdapter mAdapter;
    List<Shop> shops1;


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
        View parentView = inflater.inflate(R.layout.fragment_list_shops,container,false);
        listViewTimeline = (ListView) parentView.findViewById(R.id.shoplist);

        listViewTimeline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Shop shop=new Shop();
                shop=(Shop) listViewTimeline.getItemAtPosition(position);
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("code", shop.getCode()+"").commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("firstname", shop.getFirst_name()+"").commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("lastname", shop.getLast_name()+"").commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("adresse", shop.getAddress()+"").commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("city", shop.getCity()+"").commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("province", shop.getProvince()+"").commit();

                if(shop.getPlace()!=null){
                    PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("lat", shop.getPlace().getLatitude()+"").commit();
                    PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("lang", shop.getPlace().getLongitude()+"").commit();
                    PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("isthere", "yes").commit();

                }else {
                    PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("isthere", "no").commit();

                }


                Intent intent = new Intent(getContext(), OneShopPage.class);
                startActivity(intent);

            }
        });


        if (isNetworkAvailable()){
            getShops();
        }

        return parentView;
    }

    private void getShops(){
        mSubscriptions.add(ShopRetrofit.getRetrofit().getAllShop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(List<Shop> shops) {
        for (Shop a :
                shops) {
            System.out.println(a.toString());
        }

        shops1 = shops;
        if (shops1 != null){
        mAdapter = new ShopsAdapter(getContext(), shops1);

        listViewTimeline.setAdapter(mAdapter);
    }

    }
    private void handleError(Throwable error) {

    }
}
