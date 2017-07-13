package tn.com.cellcom.cellcomevertek.utils;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import tn.com.cellcom.cellcomevertek.MainActivity;

/**
 * Created by farouk on 07/07/2017.
 */

public class LocationService extends IntentService {

    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";

    public LocationService() {
        super("SimpleIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


    }
}
