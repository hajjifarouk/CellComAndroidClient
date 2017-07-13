package tn.com.cellcom.cellcomevertek;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import tn.com.cellcom.cellcomevertek.fragments.ChangePasswordDialog;
import tn.com.cellcom.cellcomevertek.fragments.LoginFragment;
import tn.com.cellcom.cellcomevertek.fragments.ResetPasswordDialog;


public class LoginActivity extends AppCompatActivity implements ResetPasswordDialog.Listener, ChangePasswordDialog.Listener {
    public static final String TAG = LoginFragment.class.getSimpleName();
    private LoginFragment mLoginFragment;
    private ResetPasswordDialog mResetPasswordDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (savedInstanceState == null) {

            loadFragment();
        }
    }
    private void loadFragment(){

        if (mLoginFragment == null) {

            mLoginFragment = new LoginFragment();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragmentFrame,mLoginFragment,LoginFragment.TAG).commit();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String data = intent.getData().getLastPathSegment();
        Log.d(TAG, "onNewIntent: "+data);

        mResetPasswordDialog = (ResetPasswordDialog) getFragmentManager().findFragmentByTag(ResetPasswordDialog.TAG);

        if (mResetPasswordDialog != null)
            mResetPasswordDialog.setToken(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPasswordReset(String message) {
        showSnackBarMessage(message);
    }
    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.loginMainLayout),message,Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onPasswordChanged() {
        showSnackBarMessage("Password Changed Successfully !");
    }
}
