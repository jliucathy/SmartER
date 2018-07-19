package com.example.ljx.smarter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private EditText user;
    private EditText passwd;
    private CheckBox check;
    private TextView feedbk;
    public static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=(EditText)findViewById(R.id.username_input);
        passwd=(EditText)findViewById(R.id.password_input);
        feedbk=(TextView)findViewById(R.id.l_feedback);
        Button btn1 = (Button) findViewById(R.id.login);
        btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        new AsyncTask<Void, Void, String>() {
                                            @Override
                                            protected String doInBackground(Void... params) {
                                                String pass = "";
                                                String userName = user.getText().toString();
                                                String passwdMD5 = RegisterActivity.encryptPwd(passwd.getText().toString());
                                                String allusers = RestClient.findAllUser();
                                                try {
                                                    if (allusers.contains(userName) && userName.trim().length() != 0) {
                                                        String creInfo = RestClient.findEptPasswd(userName);
                                                               String encrptyPasswd=creInfo.substring(14, 46);
                                                        if (passwdMD5.equals(encrptyPasswd)) {
                                                            pass = "passed";
                                                            String[] splitEpt = creInfo.split(":");
                                                            String resid = splitEpt[18].substring(0, 1);
                                                            LoginUserRetrivals.setResid(resid);
                                                            String firstnm = RestClient.findresident(resid);
                                                            String[] resInfo = firstnm.split(":");
                                                            int indexOfco = resInfo[8].indexOf(",");
                                                            String firstName = resInfo[8].substring(1, indexOfco - 1);
                                                            int indexOfpost = resInfo[11].indexOf(",");
                                                            String postcode = resInfo[11].substring(1, indexOfpost - 1);
                                                            LoginUserRetrivals.setLogFirstname(firstName);
                                                            LoginUserRetrivals.setPostcode(postcode);
                                                        }
                                                    }
                                                }
                                                catch (Exception e){
                                                    pass="";
                                                }
                                                return pass;
                                            }
                                            @Override
                                            protected void onPostExecute(String response) {
                                                if (response.equals("passed")) {
                                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(i);
                                                } else {
                                                    feedbk.setText("Username or password is not right");
                                                    count+=1;
                                                }
                                            }

                                        }.execute();
                                    }
                                });
        if(count>=100000000){
            btn1.setEnabled(false);
        }

        Button btn2 = (Button) findViewById(R.id.register_log);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }



}
