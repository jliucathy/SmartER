package com.example.ljx.smarter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MapFragment extends Fragment implements View.OnClickListener {
    private View vMap;
    private final String addressTest="56 Kambrook Rd,Caulfield North,VIC 3161,AU";
    private Context context;
 //   private double lat=0.0;
 //   private double lon=0.0;
    private TextView location;
    private MapView mMapView;
    private GoogleMap googleMap;
    private Spinner choice;
    private Button select;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMap = inflater.inflate(R.layout.fragment_map, container, false);
        context=getActivity().getApplicationContext();
        location=(TextView)vMap.findViewById(R.id.location);
        choice=(Spinner)vMap.findViewById(R.id.choice);
        select=(Button)vMap.findViewById(R.id.update);
        mMapView = (MapView) vMap.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
      //  new GeocodeAddress(location,choice).execute();
        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setRetainInstance(true);
        select.setOnClickListener(this);
        return vMap;
    }


    @Override
    public void onClick(View v) {
        final String userChoice = choice.getSelectedItem().toString();
        new AsyncTask<Void, Void, ArrayList<String>>(){
            @Override
            protected void onPreExecute () {
                super.onPreExecute();
            }
            @Override
        protected ArrayList<String> doInBackground (Void...params){
            final String hour = getCurrentTime();
            Geocoder geocoder = new Geocoder(context);
       //s     final String userChoice = choice.getSelectedItem().toString();
            try {
                //Integer.parseInt(getDate().subString(8,10))-1<10 ? "0"+String.valueOf(Integer.parseInt(getDate().subString(8,10))-1):String.valueOf(Integer.parseInt(getDate().subString(8,10))-1)
                String allUsage=RestClient.findAllhourlyUsage("2018-01-02",getCurrentTime());
                String[] allusage=allUsage.split("\"");
                int noofres=(allusage.length)/16;
                ArrayList<String> addressRecord=new ArrayList<>();
                ArrayList<String> postcodeRecord=new ArrayList<>();
                ArrayList<String> hourlyRecord=new ArrayList<>();
                ArrayList<String> resRecord=new ArrayList<>();
                for(int i=3;i<allusage.length;i+=16){
                    addressRecord.add(allusage[i]);
                }
                for(int i=7;i<allusage.length;i+=16){
                    postcodeRecord.add(allusage[i]);
                }
                for(int i=15;i<allusage.length;i+=16){
                    hourlyRecord.add(allusage[i]);
                }
                for(int i=11;i<allusage.length;i+=16){
                    resRecord.add(allusage[i].substring(27,28));
                }
                ArrayList<String> templatlonRecord=new ArrayList<>();
                templatlonRecord.add(String.valueOf(noofres));
                templatlonRecord.add(LoginUserRetrivals.getResid());//login userid
                for(int i=0;i<noofres;i++) {
                    String addresst = addressRecord.get(i) + ",VIC " + postcodeRecord.get(i) + ",AU";
                    List<Address> address = geocoder.getFromLocationName(addresst, 5);
                    Address location = address.get(0);
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    DecimalFormat df = new DecimalFormat("##0.0");
                    String temp = df.format(lat) + df.format(lon);
                    templatlonRecord.add(temp);
                }
                for(int i=0;i<noofres;i++) {
                    String dailyusage = RestClient.finddailyUsage(resRecord.get(i), "2018-01-02");
                    String hourlyusage = RestClient.findhourlyUsage(resRecord.get(i), "2018-01-02", hour);
                    String temp=dailyusage.substring(50, 55)+hourlyusage.substring(0, 3);
                    templatlonRecord.add(temp);
                }//loginretrieval userid
                return templatlonRecord;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute (final ArrayList<String> result){
        //    Log.i("TAG", result);
       //     location.setText(result);
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;
                    int noofres=Integer.parseInt(result.get(0));
                    Double[] latRecord=new Double[noofres];
                    Double[] lonRecord=new Double[noofres];
                    Double[] hourlyRecord=new Double[noofres];
                    Double[] dailyRecord=new Double[noofres];
                    for(int i=0;i<noofres;i++) {
                        Double latitude = Double.parseDouble(result.get(i + 2).substring(0, 5));
                        latRecord[i] = latitude;
                        Double longitude = Double.parseDouble(result.get(i + 2).substring(5, 10));
                        lonRecord[i] = longitude;
                        Double dailyUsage=Double.parseDouble(result.get(i+5).substring(0,5));
                        dailyRecord[i]=dailyUsage;
                        Double hourlyUsage=Double.parseDouble(result.get(i+5).substring(5,8));
                        hourlyRecord[i]=hourlyUsage;
                    }
                    int loginuser=Integer.parseInt(result.get(1));
                    for(int i=0;i<noofres;i++){
                        LatLng resid = new LatLng(latRecord[i], lonRecord[i]);
                        addResidentMarker(userChoice, googleMap, hourlyRecord[i], resid, dailyRecord[i]);
                        if(i+1==loginuser) {
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(resid).zoom(12).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    }
                    // For zooming automatically to the location of the marker

                }
            });

        }
    }.execute();
}

    private void addResidentMarker(String userChoice,GoogleMap mMap,Double hourlyusage,
                                   LatLng resid,Double dailyusage){
        //      String userChoice=choice.getSelectedItem().toString();
        if(userChoice.equals("Hourly")) {
            if (hourlyusage >= 1.5) {
                googleMap.addMarker(new MarkerOptions().position(resid).title("hourly")
                        .snippet(String.valueOf(hourlyusage)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
                if (hourlyusage < 1.5) {
                googleMap.addMarker(new MarkerOptions().position(resid).title("hourly")
                        .snippet(String.valueOf(hourlyusage)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }
        }
        if(userChoice.equals("Daily")){
            if (dailyusage >= 21) {
                googleMap.addMarker(new MarkerOptions().position(resid).title("daily")
                        .snippet(String.valueOf(dailyusage)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
                if (dailyusage < 21) {
                    googleMap.addMarker(new MarkerOptions().position(resid).title("daily")
                            .snippet(String.valueOf(dailyusage)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            }
    }


    private String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Calendar cal = Calendar.getInstance();
        String temp1 = dateFormat.format(cal.getTime());
        String currentTime = temp1.substring(11, 13);
        return currentTime;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    }



