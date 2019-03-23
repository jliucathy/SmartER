package com.example.ljx.smarter;

import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
/**
 * Created by jliu0030 on 24/4/18.
 */

public class RestClient {
    //private static ERDBManager erdbManager;

    private static final String BASE_URI="http://118.139.29.33:8080/smartER/webresources";
    //http://118.139.26.111
    public static String retrieveData(final String methodPath){
        URL url=null;
        HttpURLConnection conn = null;
        String textResult = "";//Making HTTP request
        try {
            // url = new URL(BASE_URI + methodPath);
            url = new URL(BASE_URI + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //conn.connect();

            conn.setReadTimeout(1000000); //set the timeout
            conn.setConnectTimeout(1500000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            //conn.getRequestMethod();
            InputStream co=conn.getInputStream();
            Scanner inStream = new Scanner(co); //read the input steream and store it as string
            while (inStream.hasNextLine())
            { textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace(); }
        finally {
            conn.disconnect(); }
        return textResult;
    }
    public static String findAllUser(){
        final String methodPath="/restvicres.resident/";
        String textResult=retrieveData(methodPath);
        return textResult;
    }

    public static String findEptPasswd(String username){
        final String methodPath="/restvicres.credential/findByUsername/"+username;
        String textResult=retrieveData(methodPath);
        return textResult;
    }


    public static String findAllCredential(){
        final String methodPath="/restvicres.credential/";
        String textResult=retrieveData(methodPath);
        return textResult;
    }

    public static String findAllUsage(){
        final String methodPath="/restvicres.eleusage/";
        String textResult=retrieveData(methodPath);
        return textResult;
    }


    public static String findresident(String resid){
        final String methodPath="/restvicres.resident/"+resid;
        String textResult=retrieveData(methodPath);
        return textResult;
    }

    public static String finddailyUsage(String resid, String datestr){
        //if datestr match re
        final String methodPath="/restvicres.eleusage/finddailyorhourly/"+resid+"/"+datestr+"/daily";
        String textResult=retrieveData(methodPath);
        return textResult;
    }

    public static String finddetailedDailyUsage(String resid, String datestr){
        //if datestr match re
        final String methodPath="/restvicres.eleusage/finddailyorhourly/"+resid+"/"+datestr+"/hourly";
        String textResult=retrieveData(methodPath);
        return textResult;
    }

    public static String findhourlyUsage(String resid, String datestr,String hour){
        //if datestr match re
        final String methodPath="/restvicres.eleusage/findbyIDANDDateANDtime/"+resid+"/"+datestr+"/"+hour;
        String textResult=retrieveData(methodPath);
        return textResult;
    }

    public static String finddailyAppliance(String resid, String datestr){
        final String methodPath="/restvicres.eleusage/finddailyappliance/"+resid+"/"+datestr;
        String textResult=retrieveData(methodPath);
        return textResult;
    }

    public static String findAllhourlyUsage(String date, String time){
        final String methodPath="/restvicres.eleusage/findbyDateANDtime/"+date+"/"+time;
        String textResult=retrieveData(methodPath);
        return textResult;
    }

    public static void postData(final String methodPath, String stringUsageJson){
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(BASE_URI + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(1000000);
            conn.setConnectTimeout(1500000);
//set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringUsageJson.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
//Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringUsageJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createResident(Resident resident){
        // erdbManager.insertUsage();
        final String methodPath="/restvicres.resident/";
        Gson gson = new Gson();
        String stringUsageJson = gson.toJson(resident);
        postData(methodPath,stringUsageJson);
    }

    public static void createCredential(Credential credential){
        // erdbManager.insertUsage();
        final String methodPath="/restvicres.credential/";
        Gson gson = new Gson();
        String stringUsageJson = gson.toJson(credential);
        postData(methodPath,stringUsageJson);
    }

    public static void createEleusage(EleusageSimulator eleusage){
        // erdbManager.insertUsage();
        final String methodPath="/restvicres.eleusage/";
        Gson gson = new Gson();
        String stringUsageJson = gson.toJson(eleusage);
        postData(methodPath,stringUsageJson);
    }
}

