package com.example.ljx.smarter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class RegisterActivity extends AppCompatActivity {


    private Double total;
    protected ERDBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText username_input=(EditText) findViewById(R.id.username_input);
        final EditText password_input=(EditText) findViewById(R.id.password_input);
        final EditText password_rpt=(EditText)findViewById(R.id.rpt_password_input);
        final EditText first_name_input=(EditText)findViewById(R.id.first_name_input);
        final EditText surname_input=(EditText)findViewById(R.id.surname_input);
        final DatePicker dob_input=(DatePicker) findViewById(R.id.dob_input);
        final EditText address_input=(EditText)findViewById(R.id.address_input);
        final EditText postcode_input=(EditText)findViewById(R.id.postcode_input);
        final EditText email_input=(EditText)findViewById(R.id.email_input);
        final EditText mobile_input=(EditText)findViewById(R.id.mobile_input);
        final Spinner noofresidents_input=(Spinner)findViewById(R.id.noofresidents_input);
        final Spinner energyprovoder_input=(Spinner)findViewById(R.id.energyprovoder_input);
        final TextView feedback=(TextView)findViewById(R.id.r_feedback);
     //   String temppasswd=password_input.getText().toString();
      //  String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(temppasswd);
        //registration date
   //     final String resid="6";
        final String registrationDate=genRegistrationDate();
        Button btn = (Button) findViewById(R.id.register);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void,Void,String>(){
                    @Override
                    protected String doInBackground(Void ...params){
                        String result="";
                        String dob="";
                        if(dob_input.getMonth()<9 && dob_input.getDayOfMonth()<10) {
                            dob = String.valueOf(dob_input.getYear()) + "-" + "0" + String.valueOf(dob_input.getMonth() + 1)
                                    + "-" +"0" + String.valueOf(dob_input.getDayOfMonth()) + "T00:00:00+11:00";
                        }
                        if(dob_input.getMonth()<9 && dob_input.getDayOfMonth()>=10){
                            dob = String.valueOf(dob_input.getYear()) + "-" +"0" +String.valueOf(dob_input.getMonth() + 1) + "-"
                                    + String.valueOf(dob_input.getDayOfMonth()) + "T00:00:00+11:00";
                        }
                        if(dob_input.getMonth()>=9 && dob_input.getDayOfMonth()<10){
                            dob = String.valueOf(dob_input.getYear()) + "-" +String.valueOf(dob_input.getMonth() + 1) + "-"
                            +"0" + String.valueOf(dob_input.getDayOfMonth()) + "T00:00:00+11:00";
                        }
                        if(dob_input.getMonth()>=9 && dob_input.getDayOfMonth()>=10){
                            dob = String.valueOf(dob_input.getYear()) + "-" +String.valueOf(dob_input.getMonth() + 1) + "-"
                                    +String.valueOf(dob_input.getDayOfMonth()) + "T00:00:00+11:00";
                        }
                        final String noofresidents=noofresidents_input.getSelectedItem().toString();
                        final String energy=energyprovoder_input.getSelectedItem().toString();

                        final String firstname = first_name_input.getText().toString();
                        final String surname = surname_input.getText().toString();
                        final String address = address_input.getText().toString();
                        final String postcode=postcode_input.getText().toString();
                        final String email=email_input.getText().toString();
                        final String mobile=mobile_input.getText().toString();
                        final String username=username_input.getText().toString();
                        final String password=password_input.getText().toString();
                        final String rptPassword=password_rpt.getText().toString();
                        final String encrptedpasswd=encryptPwd(password);//
                        String alluser=RestClient.findAllUser();
                        String[] user=alluser.split(",");
                        String userTemp=user[user.length-2];
                        String lastResid=userTemp.substring(userTemp.length()-1);
                        int newResid=Integer.parseInt(lastResid)+1;
                        String resid=String.valueOf(newResid);
                        Resident resident=new Resident(resid,firstname,surname,dob,address,postcode,email,mobile,noofresidents,energy);
                        Credential credential=new Credential(username,password,resid,registrationDate);
                        String allCredential=RestClient.findAllCredential();
                        if(validateInput(resident,credential).equals("")
                                &&!alluser.contains(resident.getEmail())
                                &&!allCredential.contains(credential.getUsername())
                                &&password.equals(rptPassword)) {
                            Credential newCredential=new Credential(username,encrptedpasswd,resid,registrationDate);
                            RestClient.createResident(resident);
                            RestClient.createCredential(newCredential);
                            result = "User created" + firstname + address + postcode;
                        }
                        else{
                            if(alluser.contains(resident.getEmail())){
                                result=result+"\n"+"Please use a different email.";
                            }
                            if(allCredential.contains(credential.getUsername())){
                                result=result+"\n"+"Please use a different Username.";
                            }
                            if(!validateInput(resident,credential).equals("")){
                                result=result+"\n"+validateInput(resident,credential);
                            }
                            if(!password.equals(rptPassword)){
                                result=result+"\n"+"Password and repeat password should be exactly same";
                            }
                        }
                        return result;
                    }
                    @Override
                    protected void onPostExecute(String response){
                        if(response.contains("User created")) {
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                        else{
                            feedback.setText(response);
                        }
                    }
                }.execute();
            }
        });

        Button btn1=(Button)findViewById(R.id.r_login);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
    private String genRegistrationDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Calendar cal = Calendar.getInstance();
        String tempDate=dateFormat.format(cal.getTime());
        String date=tempDate.substring(0,11)+"00:00:00+11:00";
        return date;
    }


    public String onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {

        int month = monthOfYear + 1;
        String formattedMonth = "" + month;
        String formattedDayOfMonth = "" + dayOfMonth;
        String result="";

        if(month < 10){

            formattedMonth = "0" + month;
        }
        if(dayOfMonth < 10){

            formattedDayOfMonth = "0" + dayOfMonth;
        }
        result=formattedDayOfMonth + "/" + formattedMonth + "/" + year+"00:00:00+11:00";
        return result;
    }

    public String validateInput(Resident resident, Credential credential){
        String hint="";

        if(resident.getFirstname().contains("<")
                ||resident.getFirstname().contains("$")
                ||resident.getFirstname().contains("%")
                ||resident.getFirstname().contains("|")
                ||resident.getFirstname().contains(";")
                ||resident.getFirstname().contains("`")
                ||resident.getFirstname().contains("&")
                ||resident.getFirstname().contains(">")) {
            hint=hint+"\n"+"First name only allow letters";
        }
        if(resident.getFirstname().length()>=64 ||resident.getFirstname().length()<1){
            hint=hint+"\n"+"First name shoule be less than 64 characters and at least 1 character";
        }
        if(resident.getSurname().contains("<")
                ||resident.getSurname().contains("$")
                ||resident.getSurname().contains("%")
                ||resident.getSurname().contains("|")
                ||resident.getSurname().contains(";")
                ||resident.getSurname().contains("`")
                ||resident.getSurname().contains("&")
                ||resident.getSurname().contains(">")) {
            hint=hint+"\n"+"Surname only allow letters";
        }
        if(resident.getSurname().length()>=64 ||resident.getSurname().length()<1){
            hint=hint+"\n"+"Surname should be less than 64 characters and at least 1 letter";
        }
        if(resident.getAddress().contains("<")
                ||resident.getAddress().contains("$")
                ||resident.getAddress().contains("%")
                ||resident.getAddress().contains("|")
                ||resident.getAddress().contains(";")
                ||resident.getAddress().contains("`")
                ||resident.getAddress().contains("&")
                ||resident.getAddress().contains(">")){
            hint=hint+"\n"+"Address only allow letters and numbers";
        }
        if(resident.getAddress().length()>=200 ||resident.getAddress().length()<1){
            hint=hint+"\n"+"Address cannot be over 200 charaters and at least 1 letter";
        }
        if(!resident.getPostcode().matches("^[0-9]*$")){
            hint=hint+"\n"+"post code only allow numbers";
        }
        if(resident.getPostcode().length()>16){
            hint=hint+"\n"+"post code should be less than 16 digits";
        }
        if(!resident.getEmail().contains("@")) {
            if (resident.getAddress().contains("<")
                    || resident.getAddress().contains("$")
                    || resident.getAddress().contains("%")
                    || resident.getAddress().contains("|")
                    || resident.getAddress().contains(";")
                    || resident.getAddress().contains("`")
                    || resident.getAddress().contains("&")
                    || resident.getAddress().contains(">")) {
                hint = hint+"\n"+"Email only should be like alice@example.com";
            }
        }
        if(resident.getEmail().length()>256){
            hint =hint+"\n"+ "Email only should be less than 200 characters";
        }
        if(!resident.getMobile().matches("^[0-9]*$")){
            hint=hint+"\n"+"Mobile number only allow numbers";
        }
        if(resident.getMobile().length()>17 ||resident.getMobile().length()<1 ){
            hint=hint+"\n"+"Mobile number should be less than 17 digits and at least 1 digits";
        }
        if (credential.getUsername().contains("<")
                || credential.getUsername().contains("$")
                || credential.getUsername().contains("%")
                || credential.getUsername().contains("|")
                || credential.getUsername().contains(";")
                || credential.getUsername().contains("`")
                || credential.getUsername().contains("&")
                || credential.getUsername().contains(">")){
            hint=hint+"\n"+"User name only allow numbers and letters";
        }
        if(credential.getUsername().length()>64 ||credential.getUsername().length()<1){
            hint=hint+"\n"+"User name should be less than 64 characters with at least 1 character";
        }
        if(credential.getPassword().length()>1000
                ||credential.getPassword().length()<7
                ||!credential.getPassword().matches("^(?=.*[a-zA-Z])(?=.*[0-9])")) {
            hint=hint+"\n"+"Password should be less than 64 characters with at least 7 character";
        }
        return hint;
    }

    public static String encryptPwd(String pwd) {
        String result = "";

        try{
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(pwd.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            result = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}

