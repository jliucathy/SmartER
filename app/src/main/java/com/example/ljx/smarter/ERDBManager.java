package com.example.ljx.smarter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.ljx.smarter.ERDBStructure.EleusageTable.COLUMN_USAGEID;
public class ERDBManager {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "usage.db";
    private final Context context;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ERDBStructure.EleusageTable.TABLE_NAME + " (" +
                    ERDBStructure.EleusageTable.COLUMN_USAGEID + TEXT_TYPE + COMMA_SEP +
                    ERDBStructure.EleusageTable.COLUMN_RESID + TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.EleusageTable.COLUMN_DATE+ TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.EleusageTable.COLUMN_TIME + TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.EleusageTable.COLUMN_TEMPERATURE + TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.EleusageTable.COLUMN_AIRCON + TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.EleusageTable.COLUMN_FRIDGE + TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.EleusageTable.COLUMN_WASHINGMACHINE + TEXT_TYPE +
                    " );";
    private static final String SQL_CREATE_RESIDENT =
            "CREATE TABLE " + ERDBStructure.ResidentTable.Table_Name + " (" +
                    ERDBStructure.ResidentTable.COLUMN_RESID + TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.ResidentTable.COLUMN_FIRSTNAME+ TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.ResidentTable.COLUMN_SURNAME + TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.ResidentTable.COLUMN_DOB + TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.ResidentTable.COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.ResidentTable.COLUMN_POSTCODE + TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.ResidentTable.COLUMN_EMAIL+ TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.ResidentTable.COLUMN_MOBILE+ TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.ResidentTable.COLUMN_NOOFRESIDENTS+ TEXT_TYPE + COMMA_SEP
                    + ERDBStructure.ResidentTable.COLUMN_ENERGYPROVIDER+ TEXT_TYPE +
                    " );";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ERDBStructure.EleusageTable.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES1 =
            "DROP TABLE IF EXISTS " + ERDBStructure.ResidentTable.Table_Name;
    private MySQLiteOpenHelper myDBHelper;
    private SQLiteDatabase db;

    public ERDBManager(Context ctx) {
        this.context = ctx;
        myDBHelper = new MySQLiteOpenHelper(context);
    }

    private static class MySQLiteOpenHelper extends SQLiteOpenHelper {
        public MySQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
            db.execSQL(SQL_CREATE_RESIDENT);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// This database is only a cache for online data, so its upgrade policy is // to simply to discard the data and start over db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }

    public ERDBManager open() throws SQLException {
        db = myDBHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        myDBHelper.close();
    }



    public long InsertUsage(String temperature, String resid){
        EleusageSimulator us = new EleusageSimulator();
        us.setResid(resid);  //get resid from resident sqlite
        String tempdate=us.genDate();
        String temphour=us.getTime();
        String tempusageid=us.genUsageid(temphour);
        String tempaircon=us.genAircon(temperature);
        us.genWashingmachine();
        ContentValues values = new ContentValues();
        values.put(ERDBStructure.EleusageTable.COLUMN_USAGEID, tempusageid);
        values.put(ERDBStructure.EleusageTable.COLUMN_RESID, "3");
        values.put(ERDBStructure.EleusageTable.COLUMN_DATE, tempdate);
        values.put(ERDBStructure.EleusageTable.COLUMN_TIME, temphour);
        values.put(ERDBStructure.EleusageTable.COLUMN_TEMPERATURE, temperature);
        values.put(ERDBStructure.EleusageTable.COLUMN_AIRCON, tempaircon);
        values.put(ERDBStructure.EleusageTable.COLUMN_WASHINGMACHINE, us.getWashingmachine());
        values.put(ERDBStructure.EleusageTable.COLUMN_FRIDGE, us.genFridge());
        return db.insert(ERDBStructure.EleusageTable.TABLE_NAME, null, values);
    }

    public int getUsageCount() {
        String countQuery = "SELECT  * FROM " + ERDBStructure.EleusageTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public String readUsage(){
        Cursor c = getAllUsage();
        String s="";
        if (c.moveToFirst()) {
            do {
                if(c.getString(3).length()<=1 && c.getString(4).length()<=3){
                s+="Usageid" + c.getString(0)
                        + "Resid" + c.getString(1)
                        + "date" + c.getString(2)
                        +"time"+"0"+c.getString(3)
                        +"temperature"+"0"+c.getString(4)
                        +"aircon"+c.getString(5)
                        +"fridge"+c.getString(6)
                        +"washingmachine"+c.getString(7)+"\n";
                }
                if(c.getString(3).length()>1 && c.getString(4).length()<=3){
                    s+="Usageid" + c.getString(0)
                            + "Resid" + c.getString(1)
                            + "date" + c.getString(2)
                            +"time"+c.getString(3)
                            +"temperature"+"0"+c.getString(4)
                            +"aircon"+c.getString(5)
                            +"fridge"+c.getString(6)
                            +"washingmachine"+c.getString(7)+"\n";
                }
                if(c.getString(3).length()<=1 && c.getString(4).length()>3){
                    s+="Usageid" + c.getString(0)
                            + "Resid" + c.getString(1)
                            + "date" + c.getString(2)
                            +"time"+"0"+c.getString(3)
                            +"temperature"+c.getString(4)
                            +"aircon"+c.getString(5)
                            +"fridge"+c.getString(6)
                            +"washingmachine"+c.getString(7)+"\n";
                }
                if(c.getString(3).length()>1 && c.getString(4).length()>3){
                    s+="Usageid" + c.getString(0)
                            + "Resid" + c.getString(1)
                            + "date" + c.getString(2)
                            +"time"+c.getString(3)
                            +"temperature"+c.getString(4)
                            +"aircon"+c.getString(5)
                            +"fridge"+c.getString(6)
                            +"washingmachine"+c.getString(7)+"\n";
                }
            } while (c.moveToNext());
        }
        return s;
    }

    public String readUser(){
        Cursor c = getAllUsage();
        String s="";
        if (c.moveToFirst()) {
            do {
                s+="id: " + c.getString(0) + "\t" + "Name: " + c.getString(1)
                        + "\t" + "DOB: " + c.getString(2)+ "\n"; } while (c.moveToNext());
        }
        return s;
    }

    public Cursor getAllUsage() {
        return db.query(ERDBStructure.EleusageTable.TABLE_NAME,
                columnsUsage, null, null, null, null, null); }
    private String[] columnsUsage = {
            ERDBStructure.EleusageTable.COLUMN_USAGEID,
            ERDBStructure.EleusageTable.COLUMN_RESID,
            ERDBStructure.EleusageTable.COLUMN_DATE,
            ERDBStructure.EleusageTable.COLUMN_TIME,
            ERDBStructure.EleusageTable.COLUMN_TEMPERATURE,
            ERDBStructure.EleusageTable.COLUMN_AIRCON,
            ERDBStructure.EleusageTable.COLUMN_FRIDGE,
            ERDBStructure.EleusageTable.COLUMN_WASHINGMACHINE
    };

    public Cursor getAllUser() {
        return db.query(ERDBStructure.ResidentTable.Table_Name,
                columnsUser, null, null, null, null, null); }
    private String[] columnsUser = {
            ERDBStructure.ResidentTable.COLUMN_RESID,
            ERDBStructure.ResidentTable.COLUMN_FIRSTNAME,
            ERDBStructure.ResidentTable.COLUMN_SURNAME,
            ERDBStructure.ResidentTable.COLUMN_DOB,
            ERDBStructure.ResidentTable.COLUMN_ADDRESS,
            ERDBStructure.ResidentTable.COLUMN_POSTCODE,
            ERDBStructure.ResidentTable.COLUMN_EMAIL,
            ERDBStructure.ResidentTable.COLUMN_MOBILE,
            ERDBStructure.ResidentTable.COLUMN_NOOFRESIDENTS,
            ERDBStructure.ResidentTable.COLUMN_ENERGYPROVIDER
    };

    public int deleteUsage(String rowId) {
        String[] selectionArgs = { String.valueOf(rowId) };
        String selection = ERDBStructure.EleusageTable.COLUMN_USAGEID + " LIKE ?";
        return db.delete(ERDBStructure.EleusageTable.TABLE_NAME, selection,selectionArgs ); }

    public int deleteAll(){
        return db.delete(ERDBStructure.EleusageTable.TABLE_NAME,null,null);
    }

    public boolean updateAirconUsage(String usageid, String aircon) {
        ContentValues values = new ContentValues();
        values.put(ERDBStructure.EleusageTable.COLUMN_AIRCON, aircon);
        String selection = ERDBStructure.EleusageTable.COLUMN_USAGEID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(usageid) };
        int count = db.update( ERDBStructure.EleusageTable.TABLE_NAME, values,
                selection,
                selectionArgs);
        return count > 0; }

    public String getRecentUsage(String id)  {
        String usageid=id+genDate()+getCurrentTime();
     //   String selection = ERDBStructure.EleusageTable.COLUMN_USAGEID + " = ?";
     //   String[] selectionArgs = {String.valueOf(usageid)};
    //    Cursor cursor = db.query(true, ERDBStructure.EleusageTable.TABLE_NAME, columns,

        String selectQuery= "SELECT * FROM " + ERDBStructure.EleusageTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String str = "";
        if(cursor.moveToLast())
            str  =  cursor.getString( cursor.getColumnIndex(usageid) );
        cursor.close();
        return str;
    }

    private String genDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Calendar cal = Calendar.getInstance();
        String tempDate=dateFormat.format(cal.getTime());
        String date=tempDate.substring(0,10);
        return date;
    }

    private String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Calendar cal = Calendar.getInstance();
        String temp1=dateFormat.format(cal.getTime());
        String currentTime=temp1.substring(11,13);
        return currentTime;
    }


}
