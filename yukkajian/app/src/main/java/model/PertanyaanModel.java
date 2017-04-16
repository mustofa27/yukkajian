package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import entity.Kajian;
import entity.Pertanyaan;
import entity.User;


/**
 * Created by LENOVO on 3/9/2017.
 */

public class PertanyaanModel {
    private DatabaseHandler databaseHandler;
    public PertanyaanModel(Context context){
        databaseHandler = new DatabaseHandler(context);
    }
    public boolean InsertPertanyaan(Pertanyaan pertanyaan){
        List<String> key = new ArrayList<>();
        key.add(Pertanyaan.column_sid);
        List<String> operator = new ArrayList<>();
        operator.add(WhereHelper.equal);
        List<String> val = new ArrayList<>();
        val.add(String.valueOf(pertanyaan.getSid()));
        WhereHelper whereHelper = new WhereHelper(key,val,operator,null);
        Pertanyaan current = GetPertanyaan(whereHelper);
        if(current!=null){
            return UpdatePertanyaan(pertanyaan);
        }
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Pertanyaan.column_sid,pertanyaan.getSid());
            contentValues.put(Pertanyaan.column_1,pertanyaan.getId_kajian());
            contentValues.put(Pertanyaan.column_2,pertanyaan.getId_user());
            contentValues.put(Pertanyaan.column_3,pertanyaan.getPertanyaan());
            boolean result = databaseHandler.insert(Pertanyaan.tabel, contentValues);
            contentValues.clear();
            return result;
        }
    }
    public boolean DeletePertanyaan(){
        return databaseHandler.delete(Pertanyaan.tabel,null);
    }
    public boolean UpdatePertanyaan(Pertanyaan pertanyaan){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Pertanyaan.column_sid,pertanyaan.getSid());
        contentValues.put(Pertanyaan.column_1,pertanyaan.getId_kajian());
        contentValues.put(Pertanyaan.column_2,pertanyaan.getId_user());
        contentValues.put(Pertanyaan.column_3,pertanyaan.getPertanyaan());
        boolean result = databaseHandler.update(Pertanyaan.tabel,contentValues,Pertanyaan.column_id+'='+pertanyaan.getId());
        contentValues.clear();
        return result;
    }
    public List<Pertanyaan> GetListPertanyaan(WhereHelper whereHelper){
        String where = "";
        List<Pertanyaan> pertanyaanList = new ArrayList<>();
        for(int i = 0; i < whereHelper.getKey().size(); i++){
            if(i>0){
                where+=whereHelper.getPenghubung().get(i-1);
            }
            where+=whereHelper.getKey().get(i)+whereHelper.getOperator().get(i)+whereHelper.getValue().get(i);
        }
        Cursor cursor = databaseHandler.get(User.tabel,where,"");
        if(cursor.moveToFirst()) {
            pertanyaanList.add(new Pertanyaan(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
        }
        return pertanyaanList;
    }
    public Pertanyaan GetPertanyaan(WhereHelper whereHelper){
        String where = "";
        for(int i = 0; i < whereHelper.getKey().size(); i++){
            if(i>0){
                where+=whereHelper.getPenghubung().get(i-1);
            }
            where+=whereHelper.getKey().get(i)+whereHelper.getOperator().get(i)+whereHelper.getValue().get(i);
        }
        Cursor cursor = databaseHandler.get(User.tabel,where,"");
        if(cursor.moveToFirst()) {
            return new Pertanyaan(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4));
        }
        return null;
    }
}
