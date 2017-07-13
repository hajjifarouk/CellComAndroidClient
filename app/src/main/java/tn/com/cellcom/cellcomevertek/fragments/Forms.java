package tn.com.cellcom.cellcomevertek.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tn.com.cellcom.cellcomevertek.R;
import tn.com.cellcom.cellcomevertek.entities.Form;
import tn.com.cellcom.cellcomevertek.network.FormRetrofit;

/**
 * Created by farouk on 07/07/2017.
 */

public class Forms extends android.support.v4.app.Fragment {
    private CompositeSubscription mSubscriptions;
    ViewPager viewPager;
    private TabLayout tabLayout;
    public View fragment;
    List<Form> forms1;
    View view;

    private Forms.SectionsPagerAdapter mSectionsPagerAdapter;


    private int[] tabeIconUnSelected = {R.drawable.form, R.drawable.form};
    private int[] tabeIconSelected = {R.drawable.form, R.drawable.form};

    int formsnum=0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();








    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View parentView = inflater.inflate(R.layout.activity_forms, container, false);
        view=parentView;
        getForms();
        return parentView;
    }

    private void getForms(){
        mSubscriptions.add(FormRetrofit.getRetrofit().getAllForm()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(List<Form> forms) {
        for (Form a:
                forms) {
            System.out.println(a.toString());
        }

        forms1=forms;

        formsnum=forms.size();

        ////tablayout and viewpager setting

        fragment=(View) view.findViewById(R.id.fragment_place);
        mSectionsPagerAdapter = new Forms.SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        viewPager = (ViewPager) view.findViewById(R.id.viewpager12);

        viewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs2);

        tabLayout.setupWithViewPager(viewPager);

        for(int i=0;i<formsnum;i++){
            tabLayout.getTabAt(i).setIcon(tabeIconUnSelected[0]);
            tabLayout.getTabAt(i).setText("forms "+(i+1));

        }


        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("formid", forms1.get(0).getId()).commit();

        Fragment fr = new FormFragment();
        fr.setArguments(null);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabIcon(tab.getPosition());


                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("formid", forms1.get(tab.getPosition()).getId()).commit();

                Fragment fr = new FormFragment();
                fr.setArguments(null);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_place, fr);
                fragmentTransaction.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
    private void handleError(Throwable error) {

        Log.e("farouk",error.toString());
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {




                default:




                    return new FormFragment();


            }
        }

        @Override
        public int getCount() {
            return formsnum;
        }

        @Override
        public CharSequence getPageTitle(int position) {

//            }
            return null;
        }
    }

    private void changeTabIcon(int index) {


            tabLayout.getTabAt(index).setIcon(tabeIconSelected[0]);
            tabLayout.getTabAt(index).setIcon(tabeIconUnSelected[1]);


    }

}
