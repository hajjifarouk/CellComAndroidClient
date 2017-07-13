package tn.com.cellcom.cellcomevertek.fragments;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tn.com.cellcom.cellcomevertek.R;
import tn.com.cellcom.cellcomevertek.entities.Response;
import tn.com.cellcom.cellcomevertek.entities.User;
import tn.com.cellcom.cellcomevertek.network.UserRetrofit;
import tn.com.cellcom.cellcomevertek.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment{


    public static final String TAG = ProfileFragment.class.getSimpleName();

    private TextView mTvFirstName;
    private TextView mTvLastName;
    private TextView mTvEmail;
    private TextView mTvDate;
    private Button mBtChangePassword;
    private Button mBtLogout;

    private ProgressBar mProgressbar;

    private SharedPreferences mSharedPreferences;
    private String mToken;
    private String mEmail;

    private CompositeSubscription mSubscriptions;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mSubscriptions = new CompositeSubscription();
        initViews(view);
        initSharedPreferences();
        loadProfile();
        return view;
    }

    private void initViews(View v) {

        mTvFirstName = (TextView) v.findViewById(R.id.tv_first_name);
        mTvLastName = (TextView) v.findViewById(R.id.tv_last_name);
        mTvEmail = (TextView) v.findViewById(R.id.tv_email);
        mTvDate = (TextView) v.findViewById(R.id.tv_date);
        mBtChangePassword = (Button) v.findViewById(R.id.btn_change_password);
        mBtLogout = (Button) v.findViewById(R.id.btn_logout);
        mProgressbar = (ProgressBar) v.findViewById(R.id.progress);


        mBtChangePassword.setOnClickListener(view -> showDialog());
        mBtLogout.setOnClickListener(view -> {
            logout();
        });
    }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        mToken = mSharedPreferences.getString(Constants.TOKEN,"");
        mEmail = mSharedPreferences.getString(Constants.EMAIL,"");
    }

    private void logout() {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.TOKEN,"");
        editor.apply();
        getActivity().finish();
    }

    private void showDialog(){

        ChangePasswordDialog fragment = new ChangePasswordDialog();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.EMAIL, mEmail);
        bundle.putString(Constants.TOKEN,mToken);
        fragment.setArguments(bundle);
        fragment.show(getFragmentManager(),ChangePasswordDialog.TAG);
    }

    private void loadProfile() {
        mSubscriptions.add(UserRetrofit.getRetrofit(mToken).getProfile(mEmail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(User user) {

        mProgressbar.setVisibility(View.GONE);
        mTvFirstName.setText(user.getFirst_name());
        mTvEmail.setText(user.getEmail());
        mTvDate.setText(user.getCreated_at());
    }

    private void handleError(Throwable error) {

        mProgressbar.setVisibility(View.GONE);
        if (error instanceof HttpException) {
            Gson gson = new GsonBuilder().create();
            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{

            showSnackBarMessage("Network Error !");
        }
    }

    private void showSnackBarMessage(String message) {

        Snackbar.make(getView().findViewById(R.id.activity_profile),message,Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }



}
