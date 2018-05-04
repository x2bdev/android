package com.example.hoxyu.team3_weatherapp_final;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.example.hoxyu.team3_weatherapp_final.SQLiteDataBase.DBHelper;
import com.example.hoxyu.team3_weatherapp_final.model.Weather;
import com.example.hoxyu.team3_weatherapp_final.model.list;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class BieuDoActivity extends AppCompatActivity {
    ArrayList<list> weatherList = new ArrayList<>();

    float minTemp = 100000;
    float maxTemp = 0;

    float minRain = 100000;
    float maxRain = 0;

    float minPressure = 100000;
    float maxPressure = 0;

    DBHelper db;
    String DATABASE_NAME ="Weather.sqlite";

    NumberFormat format = new DecimalFormat("#0.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bieu_do);


        Toolbar toolbar = (Toolbar) findViewById(R.id.graph_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db=new DBHelper(this,DATABASE_NAME,null,1);
        ArrayList<Weather> temp=new ArrayList<>();
        temp=db.getAllWeather();
        temperatureGraph(temp);
        rainGraph(temp);
        pressureGraph(temp);
    }
    private void temperatureGraph(ArrayList<Weather> arrayList){
        LineChartView lineChartView = (LineChartView) findViewById(R.id.graph_temperature);
        LineSet dataset = new LineSet();
        for (int i = 0; i < arrayList.size(); i++) {
            float temperature = Float.valueOf(format.format(arrayList.get(i).getTemp()- 273.15));

            if (temperature < minTemp) {
                minTemp = temperature;
            }

            if (temperature > maxTemp) {
                maxTemp = temperature;
            }

            dataset.addPoint(getDateLabel(arrayList.get(i), i), (float) temperature);
        }
        dataset.setSmooth(false);
        dataset.setColor(Color.parseColor("#FF5722"));
        dataset.setThickness(4);

        lineChartView.addData(dataset);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#333333"));
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        paint.setStrokeWidth(1);
        lineChartView.setGrid(ChartView.GridType.HORIZONTAL, paint);
        lineChartView.setBorderSpacing(Tools.fromDpToPx(10));
        lineChartView.setAxisBorderValues((int) (Math.round(minTemp)) - 1, (int) (Math.round(maxTemp)) + 1);
        lineChartView.setStep(2);
        lineChartView.setXAxis(false);
        lineChartView.setYAxis(false);

        lineChartView.show();
    }
    private void rainGraph(ArrayList<Weather> arrayList) {
        LineChartView lineChartView = (LineChartView) findViewById(R.id.graph_rain);

        // Data
        LineSet dataset = new LineSet();
        for (int i = 0; i < arrayList.size(); i++) {
            float rain = Float.valueOf((float) arrayList.get(i).getRain());

            if (rain < minRain) {
                minRain = rain;
            }

            if (rain > maxRain) {
                maxRain = rain;
            }

            dataset.addPoint(getDateLabel(arrayList.get(i), i), rain);
        }
        dataset.setSmooth(false);
        dataset.setColor(Color.parseColor("#2196F3"));
        dataset.setThickness(4);

        lineChartView.addData(dataset);

        // Grid
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#333333"));
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        paint.setStrokeWidth(1);
        lineChartView.setGrid(ChartView.GridType.HORIZONTAL, paint);
        lineChartView.setBorderSpacing(Tools.fromDpToPx(10));
        lineChartView.setAxisBorderValues(0, (int) (Math.round(maxRain)) + 1);
        lineChartView.setStep(1);
        lineChartView.setXAxis(false);
        lineChartView.setYAxis(false);

        lineChartView.show();
    }
    private void pressureGraph(ArrayList<Weather> arrayList) {
        LineChartView lineChartView = (LineChartView) findViewById(R.id.graph_pressure);

        // Data
        LineSet dataset = new LineSet();
        for (int i = 0; i < arrayList.size(); i++) {

            float pressure = Float.valueOf((float) arrayList.get(i).getPressure());

            if (pressure < minPressure) {
                minPressure = pressure;
            }

            if (pressure > maxPressure) {
                maxPressure = pressure;
            }

            dataset.addPoint(getDateLabel(arrayList.get(i), i), pressure);
        }
        dataset.setSmooth(true);
        dataset.setColor(Color.parseColor("#4CAF50"));
        dataset.setThickness(4);

        lineChartView.addData(dataset);

        // Grid
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#333333"));
        paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        paint.setStrokeWidth(1);
        lineChartView.setGrid(ChartView.GridType.HORIZONTAL, paint);
        lineChartView.setBorderSpacing(Tools.fromDpToPx(10));
        lineChartView.setAxisBorderValues((int) minPressure - 1, (int) maxPressure + 1);
        lineChartView.setStep(2);
        lineChartView.setXAxis(false);
        lineChartView.setYAxis(false);

        lineChartView.show();
    }
    String previous = "";
    public String getDateLabel(Weather weather, int i) {
        if ((i + 4) % 4 == 0) {
            String dateMsString = weather.getDate() + "000";
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Long.parseLong(dateMsString));
            Date resultdate = new Date(Long.valueOf(dateMsString));

            SimpleDateFormat resultFormat = new SimpleDateFormat("E");
            resultFormat.setTimeZone(TimeZone.getDefault());
            String output = resultFormat.format(resultdate);
            if (!output.equals(previous)) {
                previous = output;
                return output;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
}
