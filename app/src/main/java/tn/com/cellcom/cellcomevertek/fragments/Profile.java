package tn.com.cellcom.cellcomevertek.fragments;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tn.com.cellcom.cellcomevertek.R;
import tn.com.cellcom.cellcomevertek.entities.User;
import tn.com.cellcom.cellcomevertek.network.UserRetrofit;

public class Profile extends android.support.v4.app.Fragment {

    private CompositeSubscription mSubscriptions;
    String stremail;

    TextView name,lastname,email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSubscriptions = new CompositeSubscription();


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View parentView = inflater.inflate(R.layout.activity_profile, container, false);
        name=(TextView)parentView.findViewById(R.id.profilename);
        lastname=(TextView)parentView.findViewById(R.id.profilelastname);
        email=(TextView)parentView.findViewById(R.id.profileemail);

        stremail= PreferenceManager.getDefaultSharedPreferences(getContext()).getString("email", "");
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

        name.setText("First Name : "+user.getFirst_name());
        lastname.setText("Last Name : "+user.getLast_name());
        email.setText("Email : "+user.getEmail());
    }

    private void handleError(Throwable error){

    }
}
