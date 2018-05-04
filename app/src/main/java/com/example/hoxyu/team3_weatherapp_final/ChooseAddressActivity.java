package com.example.hoxyu.team3_weatherapp_final;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hoxyu.team3_weatherapp_final.model.OpenWeatherJSon;
import com.example.hoxyu.team3_weatherapp_final.utils.OpenWeatherMapAPI;
import com.example.hoxyu.team3_weatherapp_final.utils.TypePrediction;

import java.util.ArrayList;

public class ChooseAddressActivity extends AppCompatActivity {
    AutoCompleteTextView txtAddress;

    ListView lvTinhThanh;
     String[] arrTinhThanh;
    ArrayAdapter<String> adapterTinhThanh;
    String[] arrHuyenTinhThanh;

    Button btnCheckWeather;
    int id_city=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        addControls();
        addEvents();
    }
    private void addEvents() {
        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapterTinhThanh.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnCheckWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mở màn hình xem thời tiết theo địa chỉ nhập bất kỳ

//truyền địa chỉ qua bên kia để xử lý
//                String a=txtAddress.getText()+"";
//                int vitri = 0;
//                for(int i=0;i<arrTinhThanh.length;i++){
//                    if(a.compareTo(arrTinhThanh[i])==0){
//                        vitri=i;
//                    }
//                }
//                Toast.makeText(ChooseAddressActivity.this, vitri+"  :vitri"+ arrHuyenTinhThanh[vitri], Toast.LENGTH_SHORT).show();
//                IdCity_AsyncTask getIDcityAsyncTask=new IdCity_AsyncTask(ChooseAddressActivity.this,arrHuyenTinhThanh[vitri]);
//                getIDcityAsyncTask.execute();
                Intent intent=new Intent(ChooseAddressActivity.this,WeatherByAddressActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("ID_CITY",id_city);
                intent.putExtra("bundle",bundle);
                Toast.makeText(ChooseAddressActivity.this, id_city+":id", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
        lvTinhThanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtAddress.setText(arrTinhThanh[position]);
                String a=txtAddress.getText()+"";
                int vitri = 0;
                for(int i=0;i<arrTinhThanh.length;i++){
                    if(a.compareTo(arrTinhThanh[i])==0){
                        vitri=i;
                    }
                }
                Toast.makeText(ChooseAddressActivity.this, vitri+"  :vitri"+ arrHuyenTinhThanh[vitri], Toast.LENGTH_SHORT).show();
                IdCity_AsyncTask getIDcityAsyncTask=new IdCity_AsyncTask(ChooseAddressActivity.this,arrHuyenTinhThanh[vitri]);
                getIDcityAsyncTask.execute();
            }
        });
    }
    public int getId_city(){
        return id_city;
    }
    public void  addControls()
    {
        txtAddress=(AutoCompleteTextView)findViewById(R.id.txtAddressCheck);

        lvTinhThanh= (ListView) findViewById(R.id.lvTinhThanh);
        arrTinhThanh=getResources().getStringArray(R.array.arrTinhThanh);
        arrHuyenTinhThanh=getResources().getStringArray(R.array.arrHuyenTheoTinhThanh);
        adapterTinhThanh=new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1,arrTinhThanh);

        lvTinhThanh.setAdapter(adapterTinhThanh);
        btnCheckWeather= (Button) findViewById(R.id.btnCheckWeather);
        txtAddress.setAdapter(adapterTinhThanh);
    }
    public void getOpenWeatherJson(OpenWeatherJSon openWeatherJSon){
        id_city=openWeatherJSon.getCity().getId();
        //Toast.makeText(this, id_city+"  ", Toast.LENGTH_SHORT).show();

    }

    public class GetIDcityAsyncTask extends AsyncTask<String,Void,OpenWeatherJSon> {
        String q;
        Activity activity;
        TypePrediction typePrediction;


        public GetIDcityAsyncTask(Activity activity, String q) {
            this.typePrediction= TypePrediction.ADDRESS_NAME;
            this.q=q;
            this.activity=activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(OpenWeatherJSon jSon) {
            super.onPostExecute(jSon);
            getOpenWeatherJson(jSon);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected OpenWeatherJSon doInBackground(String... strings) {
            OpenWeatherJSon openWeatherJSon=null;
            if(typePrediction== TypePrediction.ADDRESS_NAME)
                openWeatherJSon= OpenWeatherMapAPI.prediction(q);
            return openWeatherJSon;
        }
    }
    class IdCity_AsyncTask extends AsyncTask<String,Void,OpenWeatherJSon>{
        String name;
        Activity activity;
        TypePrediction typePrediction;


        public IdCity_AsyncTask(Activity activity,String name) {
            this.typePrediction= TypePrediction.ID_CITY;
            this.name=name;
            this.activity=activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(OpenWeatherJSon openWeatherJSon) {
            super.onPostExecute(openWeatherJSon);

            getOpenWeatherJson(openWeatherJSon);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected OpenWeatherJSon doInBackground(String... strings) {

            OpenWeatherJSon openWeatherJSon=null;
            openWeatherJSon= OpenWeatherMapAPI.prediction(name);
            return openWeatherJSon;
        }
    }
}
