package tn.com.cellcom.cellcomevertek.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tn.com.cellcom.cellcomevertek.R;
import tn.com.cellcom.cellcomevertek.entities.Answer;
import tn.com.cellcom.cellcomevertek.entities.Form;
import tn.com.cellcom.cellcomevertek.entities.Report;
import tn.com.cellcom.cellcomevertek.entities.Response;
import tn.com.cellcom.cellcomevertek.entities.User;
import tn.com.cellcom.cellcomevertek.network.FormRetrofit;
import tn.com.cellcom.cellcomevertek.network.ReportRetrofit;
import tn.com.cellcom.cellcomevertek.network.UserRetrofit;

/**
 * Created by farouk on 07/07/2017.
 */

public class FormFragment  extends /*AppCompatActivity*/ android.support.v4.app.Fragment implements View.OnClickListener {

    private CompositeSubscription mSubscriptions;

    View view1;
    String strid;
    Form form1;
    int btnid=155;
    String formattedDate;
    String stremail;
    User user1;
    ArrayList<Answer> answers;
    boolean noerror=true;
    LinearLayout linearLayout;

    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View parentView = inflater.inflate(R.layout.fragment_forms, container, false);



       strid=PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getString("formid", "");
        stremail= PreferenceManager.getDefaultSharedPreferences(getContext()).getString("email", "");

        getForm(strid);


        linearLayout = (LinearLayout) parentView.findViewById(R.id.llform);




        view1=parentView;
        return parentView;
    }

    private  void getForm(String id){
        mSubscriptions.add(FormRetrofit.getRetrofit(id).getFormById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse1,this::handleError1));
    }
    private void handleResponse1(Form form) {

        form1=form;
        for (int i=0;i<form1.getQuestions().size();i++){


            TextView textView = new TextView(getContext());
            textView.setText((i+1)+"- "+form1.getQuestions().get(i).getBody());
            textView.setTextSize(15);

            linearLayout.addView(textView);

            EditText edittxt=new EditText(getContext());
            edittxt.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            edittxt.setTextSize(12);



            edittxt.setId(i);
            linearLayout.addView(edittxt);

        }


        Button btn=new Button(getContext());
        btn.setText("submit");
        btn.setGravity(Gravity.CENTER_HORIZONTAL);
        btn.setId(R.id.btnsubmit);
        btn.setOnClickListener(this);
        btn.setBackgroundResource(R.drawable.my_button);
        linearLayout.addView(btn);

        getProfile(stremail);

    }
    private void handleError1(Throwable error) {


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnsubmit :
                Toast.makeText(getContext(),"id = "+form1.getId(),Toast.LENGTH_LONG).show();
                checkfields();
                if(noerror) {
                    Log.e("farouk", "********" + "hi" + "***********");

                    Report rapport = new Report();

                    answers=new ArrayList<Answer>();
                    for (int i = 0; i < form1.getQuestions().size(); i++) {
                        EditText edt = (EditText) view1.findViewById(i);
                        Answer a = new Answer(edt.getText().toString(), form1.getQuestions().get(i).getBody());
                        answers.add(a);
                    }
                    rapport.setAnswers(answers);
                    rapport.setForm(form1);

                    Calendar c = Calendar.getInstance();

                    System.out.println("Current time => " + c.getTime());

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    formattedDate = df.format(c.getTime());
                    rapport.setDate(formattedDate);
                    rapport.setUser(user1);
                    rapport.setRef(form1.getRef() + formattedDate);
                    addReport(rapport);
                    Log.e("farouk", "********" + rapport.getDate() + "***********");
                    Log.e("farouk", "********" + rapport.getUser().getEmail() + "***********");
                    Log.e("farouk", "********" + rapport.getForm().getTitle() + "***********");
                    Log.e("farouk", "********" + rapport.getAnswers().get(0).getAnswer() + "***********");

                }

                break;
        }
    }
    private void addReport(Report report) {

        mSubscriptions.add(ReportRetrofit.getRetrofit().addReport(report)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse22,this::handleError22));
    }

    private void handleResponse22(Report report) {
        Log.e("farouk22", "********" + report.getDate() + "***********");


    }

    private void handleError22(Throwable error) {
        Log.e("farouk22", "********" + error.getMessage() + "***********");
    }
    private void getProfile(String email) {

        mSubscriptions.add(UserRetrofit.getRetrofit(email).getProfile(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private  void handleResponse(User user){


        user1=user;



    }

    private void handleError(Throwable error){

    }

    public void checkfields(){
        for (int i=0;i<form1.getQuestions().size();i++){
            EditText edt=(EditText)view1.findViewById(i);
            if (edt.getText().toString().equals("")){
                Toast.makeText(getContext(),"please answer question N "+(i+1),Toast.LENGTH_LONG).show();
                noerror=false;
                return;
            }
        }
    }
}
