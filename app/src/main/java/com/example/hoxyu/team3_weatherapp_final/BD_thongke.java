package com.example.hoxyu.team3_weatherapp_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class BD_thongke extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    private String DATABASE_NAME="dbInfoWeather.sqlite";
    ArrayList<InfoWeather> arrayList;
    LineChart lineChart;
    String city_name = "HCM";
    Double nhiet_do;
    Double do_am;
    Double gio;
    String date;
    int id;
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bd_thongke);

        arrayList = new ArrayList<>();

        tab();

        if(isConnected()){
            if(checkExistDate()){
                update();
                readData();
                makeLineChart_nhietdo();
                makeLineChart_doam();
                makeLineChart_gio();
            }else{
                deleteFirstRow();
                saveData();
                readData();
                makeLineChart_nhietdo();
                makeLineChart_doam();
                makeLineChart_gio();
            }
        }else{
            readData();
            makeLineChart_nhietdo();
            makeLineChart_doam();
            makeLineChart_gio();
        }

    }

    private void tab(){
        tabHost=findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        tab1.setIndicator("Do am");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab2.setIndicator("Nhiet do");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3=tabHost.newTabSpec("t3");
        tab3.setIndicator("Gio");
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);
    }
    protected boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    protected boolean checkExistDate(){
        Date cDate = new Date();
        String date_current = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        sqLiteDatabase = DB.initDatabase(this, DATABASE_NAME);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  infos WHERE date = "+"'"+date_current+"'", null);

        if(cursor.getCount() == 1)
        {
            cursor.moveToPosition(0);
            id = cursor.getInt(0);
            return true;
        }else{
            return false;
        }
    }
    protected void deleteFirstRow(){
        sqLiteDatabase = DB.initDatabase(this, DATABASE_NAME);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  infos ORDER BY id LIMIT 1", null);
        cursor.moveToPosition(0);
        sqLiteDatabase.delete("infos","id=?", new String[]{Integer.toString(cursor.getInt(0))});
    }
    private void makeLineChart_nhietdo(){
        lineChart = findViewById(R.id.lineChart_nhietdo);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        Cursor cursor = getData();

        final ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yValues = new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String date_label = cursor.getString(5).substring(5,10);
            xVals.add(date_label);

            String temp = cursor.getDouble(2)+"f";
            yValues.add(new Entry(i, Float.parseFloat(temp)));
        }
        LineDataSet set1 = new LineDataSet(yValues,"Giá trị nhiệt độ");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.RED);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xVals.get((int)value);
            }
        });
        LineData data = new LineData(dataSets);
        Description description = new Description();
        description.setText("Ngày");
        lineChart.setDescription(description);
        lineChart.setData(data);
    }
    private void makeLineChart_doam(){
        lineChart = findViewById(R.id.lineChart_doam);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        Cursor cursor = getData();

        final ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yValues = new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String date_label = cursor.getString(5).substring(5,10);
            xVals.add(date_label);

            String temp = cursor.getDouble(3)+"f";
            yValues.add(new Entry(i, Float.parseFloat(temp)));
        }
        LineDataSet set1 = new LineDataSet(yValues,"Giá trị độ ẩm");
        set1.setFillAlpha(110);
        set1.setColor(Color.BLUE);
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.RED);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xVals.get((int)value);
            }
        });
        LineData data = new LineData(dataSets);
        Description description = new Description();
        description.setText("Ngày");
        lineChart.setDescription(description);
        lineChart.setData(data);
    }
    private void makeLineChart_gio(){
        lineChart = findViewById(R.id.lineChart_gio);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        Cursor cursor = getData();

        final ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yValues = new ArrayList<>();

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String date_label = cursor.getString(5).substring(5,10);
            xVals.add(date_label);

            String temp = cursor.getDouble(4)+"f";
            yValues.add(new Entry(i, Float.parseFloat(temp)));
        }
        LineDataSet set1 = new LineDataSet(yValues,"Tốc độ gió");
        set1.setFillAlpha(110);
        set1.setColor(Color.GREEN);
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.RED);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xVals.get((int)value);
            }
        });
        LineData data = new LineData(dataSets);
        Description description = new Description();
        description.setText("Ngày");
        lineChart.setDescription(description);
        lineChart.setData(data);
            }
    protected  Cursor getData(){
        sqLiteDatabase = DB.initDatabase(this, DATABASE_NAME);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM infos", null);
        return cursor;
    }
    //    private  void deleteAllData(){
//        sqLiteDatabase = DB.initDatabase(this, DATABASE_NAME);
//        sqLiteDatabase.delete("infos",null,null);
//    }
    private void readData() {
        sqLiteDatabase = DB.initDatabase(this, DATABASE_NAME);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM infos", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String city_name = cursor.getString(1);
            Double nhiet_do = cursor.getDouble(2);
            Double do_am = cursor.getDouble(3);
            Double gio = cursor.getDouble(4);
            String dateString= cursor.getString(5);

//            if(dateTesst.compareTo(dateString)>0){
//                 kq = "Yess";
//            }else{
//                 kq = "no";
//            }

            InfoWeather infoWeather = new InfoWeather();
            infoWeather.setCity_name(city_name);
            infoWeather.setDo_am(do_am);
            infoWeather.setGio(gio);
            infoWeather.setNhiet_do(nhiet_do);
            infoWeather.setId(id);
            infoWeather.setDate(dateString);
            arrayList.add(infoWeather);
        }
    }
    protected void update(){
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get("http://api.openweathermap.org/data/2.5/weather?q=Ho%20Chi%20Minh%20city&appid=9962339932e4b20897e5661ee64852db")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject root = new JSONObject(response);
                            JSONObject main = root.getJSONObject("main");
                            JSONObject wind = root.getJSONObject("wind");
                            ArrayList<String> a = new ArrayList<>();
                            nhiet_do = main.getDouble("temp");
                            Double info_nhiet_do = nhiet_do - 273;
                            do_am  = main.getDouble("humidity");
                            gio  = wind.getDouble("speed");
                            Date cDate = new Date();
                            date = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

                            ContentValues contentValues= new ContentValues();
                            contentValues.put("city_name",city_name);
                            contentValues.put("nhiet_do",info_nhiet_do);
                            contentValues.put("do_am",do_am);
                            contentValues.put("gio",gio);
                            contentValues.put("date",date);

                            sqLiteDatabase=DB.initDatabase(BD_thongke.this,DATABASE_NAME);
                            sqLiteDatabase.update("infos",contentValues,"id = "+id,null);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getBaseContext(), anError.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void saveData(){
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get("http://api.openweathermap.org/data/2.5/weather?q=Ho%20Chi%20Minh%20city&appid=9962339932e4b20897e5661ee64852db")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject root = new JSONObject(response);
                            JSONObject main = root.getJSONObject("main");
                            JSONObject wind = root.getJSONObject("wind");
                            ArrayList<String> a = new ArrayList<>();
                            nhiet_do = main.getDouble("temp");
                            Double info_nhiet_do = nhiet_do - 273;
                            do_am  = main.getDouble("humidity");
                            gio  = wind.getDouble("speed");
                            Date cDate = new Date();
                            date = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

                            ContentValues contentValues= new ContentValues();
                            contentValues.put("city_name",city_name);
                            contentValues.put("nhiet_do",info_nhiet_do);
                            contentValues.put("do_am",do_am);
                            contentValues.put("gio",gio);
                            contentValues.put("date",date);

                            sqLiteDatabase=DB.initDatabase(BD_thongke.this,DATABASE_NAME);
                            sqLiteDatabase.insert("infos",null,contentValues);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getBaseContext(), anError.toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }
}
