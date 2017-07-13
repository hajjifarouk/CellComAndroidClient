package tn.com.cellcom.cellcomevertek.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tn.com.cellcom.cellcomevertek.R;
import tn.com.cellcom.cellcomevertek.entities.Shop;
import tn.com.cellcom.cellcomevertek.fragments.ListShops;
import tn.com.cellcom.cellcomevertek.fragments.MapsPage;
import tn.com.cellcom.cellcomevertek.network.ShopRetrofit;

public class Shops extends android.support.v4.app.Fragment {
    private CompositeSubscription mSubscriptions;
    ViewPager viewPager;
    private TabLayout tabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private int[] tabeIconUnSelected = {R.drawable.maps, R.drawable.list};
    private int[] tabeIconSelected = {R.drawable.maps, R.drawable.list};

    // 5949adc775e9dbe428c09d15
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View parentView = inflater.inflate(R.layout.activity_shops, container, false);
        mSubscriptions = new CompositeSubscription();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (ViewPager) parentView.findViewById(R.id.viewpager1);

        viewPager.setAdapter(mSectionsPagerAdapter);

//        setupViewPager(viewPager);
        tabLayout = (TabLayout) parentView.findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(tabeIconUnSelected[0]);

        tabLayout.getTabAt(1).setIcon(tabeIconSelected[1]);


        tabLayout.getTabAt(0).setText("Map");

        tabLayout.getTabAt(1).setText("Shops");



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabIcon(tab.getPosition());
                switch(tab.getPosition()){
                    case 0 :


                        break;
                    case 1 :
                    break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
        }
    }
    private void handleError(Throwable error) {

    }
    private  void getShop(String id){
        mSubscriptions.add(ShopRetrofit.getRetrofit().getShopById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse1,this::handleError1));
    }
    private void handleResponse1(Shop shop) {

    }
    private void handleError1(Throwable error) {


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    viewPager.setVisibility(View.VISIBLE);




                    return new MapsPage();
                case 1:

                    viewPager.setVisibility(View.VISIBLE);
                    return new ListShops();







                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

//            }
            return null;
        }
    }

    private void changeTabIcon(int index) {

        if (index == 0) {
            tabLayout.getTabAt(0).setIcon(tabeIconSelected[0]);
            tabLayout.getTabAt(1).setIcon(tabeIconUnSelected[1]);
        } else if (index == 1) {
            tabLayout.getTabAt(0).setIcon(tabeIconUnSelected[0]);
            tabLayout.getTabAt(1).setIcon(tabeIconSelected[1]);
        }

    }
}
