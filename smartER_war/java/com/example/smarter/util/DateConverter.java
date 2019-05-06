package com.example.smarter.util;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateConverter {

    public DateConverter(){

    }

    public static Date convertDateStr(String datestr) throws Exception{
        Date dateObj;
        try {
            dateObj = new SimpleDateFormat("yyyy-MM-dd").parse(datestr);
        }
        catch (Exception e){
            throw e;
        }
        return dateObj;
    }
    
    public static String convertDate(Date date) {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String datestr = df.format(date);
        return datestr;
    }
}
