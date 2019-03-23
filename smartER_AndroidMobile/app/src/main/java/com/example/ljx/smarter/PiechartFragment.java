package com.example.ljx.smarter;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;

import java.util.ArrayList;

public class PiechartFragment extends Fragment implements View.OnClickListener{
    private View vPieChart;
    private DatePicker date;
    private Button submitb;
    private PieChart chart;
    private TextView feedback;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vPieChart = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        date=(DatePicker)vPieChart.findViewById(R.id.date_choice);
        chart=(PieChart)vPieChart.findViewById(R.id.piechart);
        feedback=(TextView)vPieChart.findViewById(R.id.d_feedback);
        submitb=(Button)vPieChart.findViewById(R.id.submit);
        submitb.setOnClickListener(this);
        return vPieChart;
    }

    @Override
    public void onClick(View v) {
        new AsyncTask<Void,Void,String>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(Void ...params) {
                String dateChoice="";
                if(date.getMonth()<9 && date.getDayOfMonth()<10) {
                    dateChoice = String.valueOf(date.getYear()) + "-" + "0" + String.valueOf(date.getMonth() + 1)
                            + "-" +"0" + String.valueOf(date.getDayOfMonth()) ;
                }
                if(date.getMonth()<9 && date.getDayOfMonth()>=10){
                    dateChoice = String.valueOf(date.getYear()) + "-" +"0" +String.valueOf(date.getMonth() + 1) + "-"
                            + String.valueOf(date.getDayOfMonth()) ;
                }
                if(date.getMonth()>=9 && date.getDayOfMonth()<10){
                    dateChoice = String.valueOf(date.getYear()) + "-" +String.valueOf(date.getMonth() + 1) + "-"
                            +"0" + String.valueOf(date.getDayOfMonth()) ;
                }
                if(date.getMonth()>=9 && date.getDayOfMonth()>=10){
                    dateChoice = String.valueOf(date.getYear()) + "-" +String.valueOf(date.getMonth() + 1) + "-"
                            +String.valueOf(date.getDayOfMonth()) ;
                }
                String tempRecord=RestClient.findAllUsage();
                String result="";
                if(tempRecord.contains(dateChoice)){
                    result=RestClient.finddailyAppliance(LoginUserRetrivals.getResid(),dateChoice);//2018-01-02
                }
                else{
                    result="No such date, please choose another date";
                }
                return result;

            }
            @Override
            protected void onPostExecute(String result){
                if(result.contains("fridge")) {
                    Double fridge_sum = Double.parseDouble(result.substring(24, 27));
                    Double aircon_sum = Double.parseDouble(result.substring(40, 44));
                    Double washingmachine_sum = Double.parseDouble(result.substring(64, 68));
                    //      String label1=result.substring(14,20);
                    //     String label2=result.substring(30,36);
                    //      String label3=result.substring(46,60);
                    date.setVisibility(View.INVISIBLE);
                    submitb.setVisibility(View.INVISIBLE);
                    chart.setVisibility(View.VISIBLE);
                    drawPieChart(fridge_sum, aircon_sum, washingmachine_sum);
                }
                else{
                    feedback.setText(result);
                }
            }
        }.execute();

    }

    private void drawPieChart(Double fridge_sum,Double aircon_sum,Double washingmashine_sum) {
     //   mTfLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setCenterTextTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.ITALIC));
        //chart.setCenterText(generateCenterSpannableText());
        chart.setDrawHoleEnabled(false);
   //     chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
    //    chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        // set data
        setData(fridge_sum,aircon_sum,washingmashine_sum,100);

        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

    private void setData(Double fridge_sum,Double aircon_sum,Double washingmashine_sum, float range)  {
        float mult = range;
        ArrayList<PieEntry> entries = new ArrayList<>();
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        double[] appUsage = new double[3];
        appUsage[0] = fridge_sum;
        appUsage[1] = aircon_sum;
        appUsage[2] = washingmashine_sum;
        double total = appUsage[0]+appUsage[1]+appUsage[2];

        for (int i = 0; i < appUsage.length ; i++) {
            // set label for partition
            String label = "";
            switch (i) {
                case 0:
                    label = "Fridge";
                    break;
                case 1:
                    label = "Air Conditioner";
                    break;
                case 2:
                    label = "Washing machine";
                    break;
                default:
                    break;
            }
            entries.add(new PieEntry((float)(mult * (appUsage[i] / total)), label, appUsage[i]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        //dataSet.setSelectionShift(0f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.ITALIC));
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);
        chart.invalidate();
    }
}

