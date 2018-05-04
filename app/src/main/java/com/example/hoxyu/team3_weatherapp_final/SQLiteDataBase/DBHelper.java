package com.example.hoxyu.team3_weatherapp_final.SQLiteDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hoxyu.team3_weatherapp_final.model.Weather;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    String DATABASE_NAME ="Weather.sqlite";
    private static final String DB_PATH_SUFIX ="/databases/";
    //SQLiteDatabase db=null;

    //Context context;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor GetData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }
//    private void CopyDataBaseFromAsset(){
//        try{
//            InputStream databaseInputStream=context.getAssets().open(DATABASE_NAME);
//
//            String outputStream = getPathDataBaseSystem();
//            File file=new File(context.getApplicationInfo().dataDir+DB_PATH_SUFIX);
//            if(!file.exists()){
//                file.mkdir();
//            }
//            OutputStream databaseOutputStream=new FileOutputStream(outputStream);
//            byte[] buffer=new byte[1024];
//            int length;
//            while ((length=databaseInputStream.read(buffer))>0){
//                databaseOutputStream.write(buffer,0,length);
//            }
//            databaseOutputStream.flush();
//            databaseOutputStream.close();
//            databaseInputStream.close();
//        }
//        catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//    private String getPathDataBaseSystem() {
//        return context.getApplicationInfo().dataDir+DB_PATH_SUFIX+DATABASE_NAME;
//    }
//    private void processSQLite(){
//        File dbFile=context.getDatabasePath(DATABASE_NAME);
//        if(!dbFile.exists()){
//            try{
//                CopyDataBaseFromAsset();
//                Toast.makeText(context, "Copy Sucessfull", Toast.LENGTH_SHORT).show();
//            }
//            catch (Exception ex){
//                ex.printStackTrace();
//            }
//        }
//    }
public ArrayList<Weather> getAllWeather(){
        ArrayList<Weather> arr=new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();
        //db=context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        //String sql="select * from NhanVien";
        Cursor cursor=database.rawQuery("select * from Weather",null);
        while (cursor.moveToNext()){
            arr.add(new Weather(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getDouble(3),
                    cursor.getDouble(4)));
        }
        return arr;
    }
    public void insertArrWeather(ArrayList <Weather> arr_weather){
        SQLiteDatabase database=getWritableDatabase();
        //database=context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
        for(Weather temp:arr_weather){
            ContentValues contentValues=new ContentValues();
            contentValues.put("id",temp.getId());
            contentValues.put("date",temp.getDate());
            contentValues.put("temp",temp.getTemp());
            contentValues.put("pressure",temp.getPressure());
            contentValues.put("rain",temp.getRain());
            if(database.insert("Weather",null,contentValues)>0) {
                //Toast.makeText(MainActivity.this, "Insert Successful", Toast.LENGTH_SHORT).show();
            } else{
                //Toast.makeText(context, "Insert NOT Successful", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//    public int getCount(int id){
//        String countQuery="select * from Playlist where idAlbums = "+id;
//        db=context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);
//        Cursor cursor=db.rawQuery(countQuery,null);
//        int count=cursor.getCount();
//        return count;
//    }
}
