package com.example.ljx.smarter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.BatchUpdateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.example.ljx.smarter.LoginUserRetrivals;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //TextView textView;
    private  TextView welcome;
    private TextView firstname;
    private TextView date;
    private TextView time;
    private TextView temperature;
    private TextView posinega;
    private ImageView image1;
    private ImageView image2;
    private TextView textView;
    private Button post;
    private Boolean test=Boolean.TRUE;
    //   String temppostcode;
   private String temptemperature;
   //  ImageView image1;
   //   ImageView image2;
    private Double total;
    protected ERDBManager dbManager;
    //private boolean isStartup = true;

    private String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Calendar cal = Calendar.getInstance();
        String tempDate=dateFormat.format(cal.getTime());
        String tempdate=tempDate.substring(0,10);
        return tempdate;
    }

    private String getHour(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Calendar cal = Calendar.getInstance();
        String temp1=dateFormat.format(cal.getTime());
        String temptime=temp1.substring(11,13);
        return temptime;
    }

    private Boolean checkWeekDays(){
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        Calendar cal = Calendar.getInstance();
        String temp1=dateFormat.format(cal.getTime());
        String temptime=temp1.substring(0,3);
        if(temptime.equals("Sun")||temptime.equals("Sat")){
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRepeatingAsyncTask();
        welcome=(TextView)findViewById(R.id.welcome);
        firstname=(TextView)findViewById(R.id.firstname);
        date=(TextView)findViewById(R.id.date);
        time=(TextView)findViewById(R.id.hour);
        temperature=(TextView)findViewById(R.id.temperature);
        textView=(TextView)findViewById(R.id.test);
        post=(Button)findViewById(R.id.post);
        dbManager = new ERDBManager(this);
        //  textView= (TextView) this.findViewById(R.idsa.textView); //remove later
        welcome.setText("Welcome");
        firstname.setText(LoginUserRetrivals.getLogFirstname()); //LoginUserRetrivals.getRegisterUser()  LoginUserRetrivals.getLogFirstname()
        date.setText(getDate());
        time.setText("H:"+getHour());

        posinega=(TextView)findViewById(R.id.posineg);
        image1=(ImageView)findViewById(R.id.imageView1);
        image2=(ImageView)findViewById(R.id.imageView2);

        //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // fab.setOnClickListener(new View.OnClickListener() {
        //       @Override
        //       public void onClick(View view) {
        //           Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                   .setAction("Action", null).show();
        //       }
        //   });
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
        }
        //   if (id == R.id.action_register) {
        //      return true;
        //  }

        return super.onOptionsItemSelected(item);
    }

    private void setInvidible(){
        welcome.setVisibility(View.INVISIBLE);
        firstname.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        temperature.setVisibility(View.INVISIBLE);
        posinega.setVisibility(View.INVISIBLE);
        image1.setVisibility(View.INVISIBLE);
        image2.setVisibility(View.INVISIBLE);
    }

    private void setVidible(){
        welcome.setVisibility(View.VISIBLE);
        firstname.setVisibility(View.VISIBLE);
        date.setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
        temperature.setVisibility(View.VISIBLE);
        int temphour=Integer.parseInt(getHour());
        if(temphour>=9
                && temphour<=21
                && checkWeekDays()==Boolean.TRUE){
            posinega.setVisibility(View.VISIBLE);
            if(total>=1.5){
                posinega.setText("Too much electricity!");
                image1.setVisibility(View.INVISIBLE);
                image2.setVisibility(View.VISIBLE);
            }
            if(total<1.5){
                posinega.setText("Energy Saver!");
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.INVISIBLE);  }
        }
        else {
            posinega.setVisibility(View.INVISIBLE);
            image1.setVisibility(View.INVISIBLE);
            image2.setVisibility(View.INVISIBLE);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment nextFragment = null;
        if (id == R.id.map) {
            test=Boolean.FALSE;
            nextFragment = new MapFragment();
            setInvidible();
        } else if (id == R.id.pie) {
            test=Boolean.FALSE;
            nextFragment=new PiechartFragment();
            setInvidible();
        } else if (id == R.id.bar) {
            test=Boolean.FALSE;
            nextFragment=new BargraphFragment();
            setInvidible();
        } else if (id == R.id.line) {
            test=Boolean.FALSE;
            nextFragment=new LinegraphFragment();
            setInvidible();
        } else if (id == R.id.home) {
            test=Boolean.TRUE;
            nextFragment=new MainFragment();
            setVidible();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                nextFragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setRepeatingAsyncTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            Temperature insertData = new Temperature();
                            insertData.execute();
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 60*60*1000);  // interval of one minute
    }

    private class Temperature extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return TemperatureAPI.getTemperature("3181","AU");//get postcode from sqlite after login get resid
        }
        @Override protected void onPostExecute(String result) {
            temptemperature=TemperatureAPI.getLocaltemp(result);
            temperature.setText(temptemperature+"°C");
            try {
                dbManager.open();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
            dbManager.InsertUsage(temptemperature,LoginUserRetrivals.getResid());
            final String lastusage=dbManager.readUsage();
            String[] tempUsage=lastusage.split(":");
            final String aircon = tempUsage[3].substring(29, 32);//
            final String fridge = tempUsage[3].substring(38,41);//
            final String wm = tempUsage[3].substring(55, 58);//
            total=Double.parseDouble(aircon)+Double.parseDouble(fridge)+Double.parseDouble(wm);
            if(test==Boolean.TRUE) {
                setVidible();
            }
            else{
                setInvidible();
            }
            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... params) {
                            final String lastusage=dbManager.readUsage();
                            String[] tempUsage=lastusage.split(":");
                            String resid = tempUsage[0].substring(63, 64);
                            String dateid=getDate();
                            String dateInput = dateid+"T00:00:00+10:00";
                            String time=getHour();
                            String usageid = LoginUserRetrivals.getResid() + dateid.substring(0,4)+dateid.substring(5,7)+dateid.substring(8,10) + time;
                      //     loginuserretrival.resid()
                            EleusageSimulator usage = new EleusageSimulator(usageid, LoginUserRetrivals.getResid(), dateInput, time, temptemperature,
                                    aircon, fridge, wm);
                            RestClient.createEleusage(usage);
                            return "Usage created";
                        }
                        @Override
                        protected void onPostExecute(String response) {
                            if (dbManager.getUsageCount() >= 2) {
                                dbManager.deleteAll();
                                post.setVisibility(View.INVISIBLE);
                            }
                            dbManager.close();
                        }

                    }.execute();

                }
            });
        }
    }
}


