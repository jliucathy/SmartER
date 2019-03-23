package com.example.ljx.smarter;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class LinegraphFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private View vLineGraph;
    private TextView title;
    private Spinner selection;
    private LineChart lineGraph;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vLineGraph = inflater.inflate(R.layout.fragment_line_graph, container, false);
        title=(TextView)vLineGraph.findViewById(R.id.linechart);
        selection=(Spinner)vLineGraph.findViewById(R.id.choice);
        lineGraph=(LineChart) vLineGraph.findViewById(R.id.lineg);
        selection.setOnItemSelectedListener(this);
        return vLineGraph;
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
                protected void onPostExecute(String result) {
                    String[] hourlyrecord = result.split("\"");
                    ArrayList<String> hourlyUsage = new ArrayList<>();
                    for (int i = 19; i < hourlyrecord.length; i += 20) {
                        hourlyUsage.add(hourlyrecord[i]);
                    }
                    for(int i=11;i<hourlyrecord.length;i+=20){
                        hourlyUsage.add(hourlyrecord[i]);
                    }
                    drawhourlyLineChart(hourlyUsage);
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
                protected void onPostExecute(ArrayList<String> result) {
                    drawdailyLineChart(result);
                }
            }.execute();
        }
    }

    private void drawhourlyLineChart(ArrayList<String> usage) {
        List<Entry> entries1 = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        // set x-axis size
        int[] xAxis = new int[usage.size()/2];
        // set y-axis size
        double[] yAxis1 = new double[usage.size()/2];
        double[] yAxis2 = new double[usage.size()/2];

        // create x,y-axis values
        for (int i = 0; i < usage.size(); i++) {
            if(i<usage.size()/2) {
                xAxis[i] = i ;
                yAxis1[i] = Double.parseDouble(usage.get(i));
            }
            if(i>=usage.size()/2) {
                yAxis2[i-24] = Double.parseDouble(usage.get(i));
            }
        }

        //set x-axis values
        final String[] xValues = new String[xAxis.length];

        // set each node on chart
        for (int i = 0; i < xAxis.length; i++){
            //set x-axis values
            xValues[i] = "" + xAxis[i];
            //set node
            entries1.add(new Entry(xAxis[i], (float)yAxis1[i]));
            entries2.add(new Entry(xAxis[i], (float)yAxis2[i]));
        }

        LineData lineData = null;
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

            // set x-axis values
            XAxis xAxisFromChart = lineGraph.getXAxis();
            xAxisFromChart.setDrawAxisLine(true);
            xAxisFromChart.setValueFormatter(formatter);
            // minimum axis-step (interval) is 1,if no, the same value will be displayed multiple times
            xAxisFromChart.setGranularity(1);
            xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

            // initialize left
            LineDataSet dataSet1 = new LineDataSet(entries1, "Usage");
            dataSet1.setColor(Color.rgb(240, 238, 70));
            dataSet1.setLineWidth(2.5f);
            dataSet1.setCircleColor(Color.rgb(240, 238, 70));
            dataSet1.setCircleRadius(5f);
            dataSet1.setFillColor(Color.rgb(240, 238, 70));
            dataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet1.setDrawValues(true);
            dataSet1.setValueTextSize(10f);
            dataSet1.setValueTextColor(Color.rgb(240, 238, 70));

            // initialize right
            LineDataSet dataSet2 = new LineDataSet(entries2, "Temperature");
            dataSet1.setColor(Color.rgb(100, 129, 35));
            dataSet1.setLineWidth(2.5f);
            dataSet1.setCircleColor(Color.rgb(120, 128, 35));
            dataSet1.setCircleRadius(5f);
            dataSet1.setFillColor(Color.rgb(120, 129, 35));
            dataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet1.setDrawValues(true);
            dataSet1.setValueTextSize(10f);
            dataSet1.setValueTextColor(Color.rgb(120, 129, 35));

            dataSet1.setAxisDependency(lineGraph.getAxisLeft().getAxisDependency());
            dataSet2.setAxisDependency(lineGraph.getAxisRight().getAxisDependency());
            // add left and right Y-axis dataset
            lineData = new LineData(dataSet1, dataSet2);
        }
        // set dataset
        lineGraph.setData(lineData);
        lineGraph.setNoDataText("No enough data to generate Line chart. Please try tomorrow for daily view, or next hour for houly view.");
        lineGraph.notifyDataSetChanged();
        lineGraph.postInvalidate();
    }

    private void drawdailyLineChart(ArrayList<String> dailyUsage) {
        List<Entry> entries1 = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        // set x-axis size
        String[] xAxis = new String[dailyUsage.size()];
        // set y-axis size
        double[] yAxis1 = new double[dailyUsage.size()];
        double[] yAxis2 = new double[dailyUsage.size()];

        xAxis[0]="2018-01-02";
        xAxis[1]="2018-01-03";
        xAxis[2]="2018-01-04";
        // create x,y-axis values
        String day1=dailyUsage.get(0).substring(50,54);
        yAxis1[0] = Double.parseDouble(day1);
        String day2=dailyUsage.get(1).substring(49,53);
        yAxis1[1]=Double.parseDouble(day2);
        String day3=dailyUsage.get(2).substring(49,53);
        yAxis1[2]=Double.parseDouble(day3);
        String temp1=dailyUsage.get(0).substring(29,33);
        yAxis2[0] = Double.parseDouble(temp1);
        String temp2=dailyUsage.get(0).substring(29,33);
        yAxis2[1] = Double.parseDouble(temp1);
        String temp3=dailyUsage.get(0).substring(29,33);
        yAxis2[2] = Double.parseDouble(temp1);

        //set x-axis values
        final String[] xValues = new String[xAxis.length];

        // set each node on chart
        for (int i = 0; i < xAxis.length; i++){
            //set x-axis values
            xValues[i] = "" + xAxis[i];
            int newxAxis[]=new int[3];
            newxAxis[i]=i;
            //set node
            entries1.add(new Entry(newxAxis[i], (float)yAxis1[i]));
            entries2.add(new Entry(newxAxis[i], (float)yAxis2[i]));
        }

        LineData lineData = null;
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

            // set x-axis values
            XAxis xAxisFromChart = lineGraph.getXAxis();
            xAxisFromChart.setDrawAxisLine(true);
            xAxisFromChart.setValueFormatter(formatter);
            // minimum axis-step (interval) is 1,if no, the same value will be displayed multiple times
            xAxisFromChart.setGranularity(1);
            xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

            // initialize left
            LineDataSet dataSet1 = new LineDataSet(entries1, "Usage");
            dataSet1.setColor(Color.rgb(240, 238, 70));
            dataSet1.setLineWidth(2.5f);
            dataSet1.setCircleColor(Color.rgb(240, 238, 70));
            dataSet1.setCircleRadius(5f);
            dataSet1.setFillColor(Color.rgb(240, 238, 70));
            dataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet1.setDrawValues(true);
            dataSet1.setValueTextSize(10f);
            dataSet1.setValueTextColor(Color.rgb(240, 238, 70));

            // initialize right
            LineDataSet dataSet2 = new LineDataSet(entries2, "Temperature");
            dataSet1.setColor(Color.rgb(100, 129, 35));
            dataSet1.setLineWidth(2.5f);
            dataSet1.setCircleColor(Color.rgb(120, 128, 35));
            dataSet1.setCircleRadius(5f);
            dataSet1.setFillColor(Color.rgb(120, 129, 35));
            dataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet1.setDrawValues(true);
            dataSet1.setValueTextSize(10f);
            dataSet1.setValueTextColor(Color.rgb(120, 129, 35));

            dataSet1.setAxisDependency(lineGraph.getAxisLeft().getAxisDependency());
            dataSet2.setAxisDependency(lineGraph.getAxisRight().getAxisDependency());
            // add left and right Y-axis dataset
            lineData = new LineData(dataSet1, dataSet2);
        }
        // set dataset
        lineGraph.setData(lineData);
        lineGraph.setNoDataText("No enough data to generate Line chart. Please try tomorrow for daily view, or next hour for houly view.");
        lineGraph.notifyDataSetChanged();
        lineGraph.postInvalidate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
