package com.example.ljx.smarter;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class BargraphFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private View vBarGraph;
    private TextView title;
    private Spinner selection;
    private BarChart barGraph;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vBarGraph = inflater.inflate(R.layout.fragment_bar_graph, container, false);
        Utils.init(vBarGraph.getContext());
        title=(TextView)vBarGraph.findViewById(R.id.bar);
        barGraph=(BarChart)vBarGraph.findViewById(R.id.barchart);
        selection=(Spinner)vBarGraph.findViewById(R.id.choice);
        selection.setOnItemSelectedListener(this);
        return vBarGraph;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
        String view = adapter.getItemAtPosition(position).toString();
        if (view.equals("Hourly")) {
            new AsyncTask<Void, Void, String>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(Void... params) {
                    //Integer.parseInt(getDate().subString(8,10))-1<10 ? "0"+String.valueOf(Integer.parseInt(getDate().subString(8,10))-1):String.valueOf(Integer.parseInt(getDate().subString(8,10))-1)
                    String date1 = "2018-01-02";
                    String hourlyRecord = RestClient.finddetailedDailyUsage(LoginUserRetrivals.getResid(), date1);//retri.userid;
                    return hourlyRecord;
                }
                @Override
                protected void onPostExecute(String result){
                    String[] hourlyrecord = result.split("\"");
                    ArrayList<Double> hourlyUsage = new ArrayList<>();
                    for (int i = 19; i < hourlyrecord.length; i += 20) {
                        hourlyUsage.add(Double.parseDouble(hourlyrecord[i]));
                    }
                    drawhourlyBarChart(hourlyUsage);
                }

            }.execute();
        }
            if (view.equals("Daily")) {
                new AsyncTask<Void, Void, ArrayList<String>>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected ArrayList<String> doInBackground(Void... params) {
                        //Integer.parseInt(getDate().subString(8,10))-1<10 ? "0"+String.valueOf(Integer.parseInt(getDate().subString(8,10))-1):String.valueOf(Integer.parseInt(getDate().subString(8,10))-1)
                        String date1 = "2018-01-02";
                        String date2 = "2018-01-03";
                        String date3="2018-01-04";
                        String day1Record = RestClient.finddailyUsage(LoginUserRetrivals.getResid(),date1);
                        String day2Record=RestClient.finddailyUsage(LoginUserRetrivals.getResid(),date2);
                        String day3Record=RestClient.finddailyUsage(LoginUserRetrivals.getResid(),date3);
                        ArrayList<String> allRecord=new ArrayList<>();
                        allRecord.add(day1Record);
                        allRecord.add(day2Record);
                        allRecord.add(day3Record);
                        return allRecord;
                    }
                    @Override
                    protected void onPostExecute(ArrayList<String> result){
                        drawdailyBarChart(result);
                    }

                }.execute();


                    }

        }


    private void drawhourlyBarChart(ArrayList<Double> hourlyUsage) {
            ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();

            // set x-axis size
            int[] xAxis = new int[hourlyUsage.size()];
            // set y-axis size
            double[] yAxis1 = new double[hourlyUsage.size()];

            // create x,y-axis values

                for (int i = 0; i < hourlyUsage.size(); i++) {
                    xAxis[i] = i + 1;
                    yAxis1[i] = hourlyUsage.get(i);
                }


            //set x-axis values
            final String[] xValues = new String[xAxis.length];

            // set each node on chart
            for (int i = 0; i < xAxis.length; i++){
                //set x-axis values
                xValues[i] = "" + xAxis[i];
                //set node
                entries1.add(new BarEntry(xAxis[i], (float)yAxis1[i]));
            }

            BarData d  = null;
            if (xAxis.length > 0) {
                //implementing IAxisValueFormatter interface to show hour values not as float/decimal
                IAxisValueFormatter formatter = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        int intValue = (int)value;
                        if (xValues.length > intValue && intValue > 0)
                            return xValues[intValue];
                        else
                            return "";
                    }
                };

                // set x-axis values
                XAxis xAxisFromChart = barGraph.getXAxis();
                xAxisFromChart.setDrawAxisLine(true);
                xAxisFromChart.setValueFormatter(formatter);
                // minimum axis-step (interval) is 1,if no, the same value will be displayed multiple times
                xAxisFromChart.setGranularity(1);
                xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

                // initialize left
                BarDataSet set1 = new BarDataSet(entries1, "");
                set1.setColor(Color.rgb(60, 220, 78));
                set1.setValueTextColor(Color.rgb(60, 220, 78));
                set1.setValueTextSize(10f);
                set1.setAxisDependency(barGraph.getAxisLeft().getAxisDependency().LEFT);


                float groupSpace = 0.06f;
                float barSpace = 0.02f;
                float barWidth = 0.15f;
                d = new BarData(set1);
                d.setBarWidth(barWidth);
            }

            // set dataset
        barGraph.setData(d);
        barGraph.setNoDataText("No enough data to generate Line chart. Please try tomorrow for daily view, or next hour for houly view.");
        barGraph.notifyDataSetChanged();
        barGraph.getDescription().setEnabled(false);
        barGraph.postInvalidate();
        }

    private void drawdailyBarChart(ArrayList<String> dailyUsage) {
        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();

        // set x-axis size
        String[] xAxis = new String[dailyUsage.size()];
        // set y-axis size
        double[] yAxis1 = new double[dailyUsage.size()];

        // create x,y-axis values
        xAxis[0]="2018-01-02";
        xAxis[1]="2018-01-03";
        xAxis[2]="2018-01-04";
        String day1=dailyUsage.get(0).substring(50,54);
        yAxis1[0] = Double.parseDouble(day1);
        String day2=dailyUsage.get(1).substring(49,53);
        yAxis1[1]=Double.parseDouble(day2);
        String day3=dailyUsage.get(2).substring(49,53);
        yAxis1[2]=Double.parseDouble(day3);



        //set x-axis values
        final String[] xValues = new String[xAxis.length];

        // set each node on chart
        for (int i = 0; i < xAxis.length; i++){
            //set x-axis values
            xValues[i] = xAxis[i];
            int newxAxis[]=new int[3];
            newxAxis[i]=i;
            //set node
            entries1.add(new BarEntry(newxAxis[i], (float)yAxis1[i]));
        }

        BarData d  = null;
        if (xAxis.length > 0) {
            //implementing IAxisValueFormatter interface to show hour values not as float/decimal
            IAxisValueFormatter formatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    int intValue = (int)value;
                    if (xValues.length >= intValue && intValue >= 0)
                        return xValues[intValue];
                    else
                        return "";
                }
            };
            ;
        //    xAxis.setLabelsToSkip(0);
            // set x-axis values
            XAxis xAxisFromChart = barGraph.getXAxis();
            xAxisFromChart.setDrawAxisLine(true);
            xAxisFromChart.setValueFormatter(formatter);
            // minimum axis-step (interval) is 1,if no, the same value will be displayed multiple times
            xAxisFromChart.setGranularity(1);
            xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

            // initialize left
            BarDataSet set1 = new BarDataSet(entries1, "Total usage");
            set1.setColor(Color.rgb(60, 220, 78));
            set1.setValueTextColor(Color.rgb(60, 220, 78));
            set1.setValueTextSize(10f);
            set1.setAxisDependency(barGraph.getAxisLeft().getAxisDependency().LEFT);



            float groupSpace = 0.06f;
            float barSpace = 0.02f;
            float barWidth = 0.15f;
            d = new BarData(set1);
            d.setBarWidth(barWidth);
        }

        // set dataset
        barGraph.setData(d);
        barGraph.setNoDataText("No enough data to generate Line chart. Please try tomorrow for daily view, or next hour for houly view.");
        barGraph.notifyDataSetChanged();
        barGraph.postInvalidate();
        barGraph.getDescription().setEnabled(false);
    }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }


    }
