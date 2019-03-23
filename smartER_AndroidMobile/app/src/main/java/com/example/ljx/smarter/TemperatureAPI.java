package com.example.ljx.smarter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
/**
 * Created by jliu0030 on 24/4/18.
 */

public class TemperatureAPI {
    private static final String API_KEY = "55a8cdede37a6df7ef35ef2f5c8a0e97";
    public static String localtemp="";

    public static String getTemperature(String postcode,String country){
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/forecast?q="+postcode+","+country+"&appid="+API_KEY);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(1000000);
            connection.setConnectTimeout(1500000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream()); while (scanner.hasNextLine()) {
                textResult += scanner.nextLine(); }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

    public static String getLocaltemp(String result){
        Double temper=0.0;
        //String localtemp="";
        DecimalFormat df = new DecimalFormat("##0.0");
        try{
            JSONObject jsonObject = new JSONObject(result);

            JSONArray jsonArray = jsonObject.getJSONArray("list");

            if(jsonArray != null && jsonArray.length() > 0) {
                JSONObject listObj=jsonArray.getJSONObject(0);
                JSONObject mainObj = listObj.getJSONObject("main");
                temper=mainObj.getDouble("temp");
                //  localtemp =jsonArray.getJSONObject(0).getString("temp");
            }
            Double temptemp=temper-273.15;
            String tempString=df.format(temptemp);
            localtemp = tempString;

        }catch (Exception e){
            e.printStackTrace();
            //localtemp = "NO INFO FOUND";
        }
        return localtemp; }

    public static String getTemp(){
        return localtemp;
    }

}
