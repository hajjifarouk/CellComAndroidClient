package tn.com.cellcom.cellcomevertek.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tn.com.cellcom.cellcomevertek.Adapter.EventsAdapter;
import tn.com.cellcom.cellcomevertek.OneShopPage;
import tn.com.cellcom.cellcomevertek.R;
import tn.com.cellcom.cellcomevertek.entities.Program;
import tn.com.cellcom.cellcomevertek.entities.Shop;
import tn.com.cellcom.cellcomevertek.entities.User;
import tn.com.cellcom.cellcomevertek.entities.Visit;
import tn.com.cellcom.cellcomevertek.network.ProgramRetrofit;
import tn.com.cellcom.cellcomevertek.network.UserRetrofit;

/**
 * Created by farouk on 08/07/2017.
 */

public class DayProgramFragment extends /*AppCompatActivity*/ android.support.v4.app.Fragment implements View.OnClickListener {

    ListView listViewTimeline;
    private CompositeSubscription mSubscriptions;
    private EventsAdapter mAdapter;
    String stremail;
    String formattedDate;
    TextView txtsearchdate,txtnoprogram;
    ImageView imgsearch;
    Button btnsearch;

    String searchdate;

    DatePickerDialog returneDatePickerDialog;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();

        searchdate=PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getString("searchdate", "");

        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("searchdate", "no").commit();



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View parentView = inflater.inflate(R.layout.fragment_day_program, container, false);
        listViewTimeline = (ListView) parentView.findViewById(R.id.programlist);


        txtsearchdate=(TextView)parentView.findViewById(R.id.txtdatesearch);
        txtnoprogram=(TextView)parentView.findViewById(R.id.txtnoprogram);
        imgsearch=(ImageView)parentView.findViewById(R.id.imgsearch);
        imgsearch.setOnClickListener(this);
        btnsearch=(Button)parentView.findViewById(R.id.btnsearch);
        btnsearch.setOnClickListener(this);




        if (searchdate.equals("no") || searchdate.equals("")) {

            Calendar c = Calendar.getInstance();

            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = df.format(c.getTime());

            dateAndTimePickers();

            txtsearchdate.setText(formattedDate);
        }else {
            formattedDate=searchdate;
            txtsearchdate.setText(formattedDate);
            dateAndTimePickers();

        }
        stremail= PreferenceManager.getDefaultSharedPreferences(getContext()).getString("email", "");


        Log.e("farouk",stremail);
        getProfile(stremail);
        return parentView;
    }


    private void getProfile(String email) {

        mSubscriptions.add(UserRetrofit.getRetrofit(email).getProfile(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private  void handleResponse(User user){


        Log.e("farouk","***********"+user.getId()+"*********");
        Log.e("farouk","***********"+formattedDate+"*********");
        listViewTimeline.setVisibility(View.VISIBLE);
        txtnoprogram.setVisibility(View.GONE);
        getProgram(user.getId(),formattedDate);

    }

    private void handleError(Throwable error){

    }

    private void getProgram(String idUser,String date) {

        mSubscriptions.add(ProgramRetrofit.getRetrofit().getProgramByUserDate(date,idUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse1,this::handleError1));
    }

    private  void handleResponse1(Program user){




            mAdapter = new EventsAdapter(getContext(), user.getVisits());

            listViewTimeline.setAdapter(mAdapter);

        listViewTimeline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Visit visit=new Visit();
                visit=(Visit) listViewTimeline.getItemAtPosition(position);
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("code", visit.getCode()+"").commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("firstname", visit.getFirst_name()+"").commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("lastname", visit.getLast_name()+"").commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("adresse", visit.getAddress()+"").commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("city", visit.getCity()+"").commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("province", visit.getProvince()+"").commit();


                    PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("isthere", "no").commit();




                Intent intent = new Intent(getContext(), OneShopPage.class);
                startActivity(intent);

            }
        });



    }

    private void handleError1(Throwable error){
        listViewTimeline.setVisibility(View.GONE);
        txtnoprogram.setVisibility(View.VISIBLE);
        Log.e("farouk","********"+error.getMessage()+"********");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imgsearch :

                returneDatePickerDialog.show();
                break;
            case R.id.btnsearch :

                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("searchdate", txtsearchdate.getText().toString()).commit();

                Fragment fr = new DayProgramFragment();
                fr.setArguments(null);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_place1, fr);
                fragmentTransaction.detach(fr);
                fragmentTransaction.attach(fr);
                fragmentTransaction.commit();
                break;
        }

    }

    public void dateAndTimePickers() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        returneDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String dateString = year+"-0"+(month + 1) + "-" + day ;
                txtsearchdate.setText(dateString);
            }
        }, year, month, day);
        returneDatePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

    }
}
