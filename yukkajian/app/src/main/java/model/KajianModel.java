package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import entity.Kajian;
import entity.User;


/**
 * Created by LENOVO on 3/9/2017.
 */

public class KajianModel {
    private DatabaseHandler databaseHandler;
    public KajianModel(Context context){
        databaseHandler = new DatabaseHandler(context);
    }
    public boolean InsertKajian(Kajian kajian){
        List<String> key = new ArrayList<>();
        key.add(Kajian.column_sid);
        List<String> operator = new ArrayList<>();
        operator.add(WhereHelper.equal);
        List<String> val = new ArrayList<>();
        val.add(String.valueOf(kajian.getSid()));
        WhereHelper whereHelper = new WhereHelper(key,val,operator,null);
        Kajian current = GetKajian(whereHelper);
        if(current!=null){
            return UpdateKajian(kajian);
        }
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Kajian.column_sid,kajian.getSid());
            contentValues.put(Kajian.column_1,kajian.getTanggal());
            contentValues.put(Kajian.column_2,kajian.getJam());
            contentValues.put(Kajian.column_3,kajian.getTema());
            contentValues.put(Kajian.column_4,kajian.getPemateri());
            contentValues.put(Kajian.column_5,kajian.getAlamat());
            contentValues.put(Kajian.column_6,kajian.getLat());
            contentValues.put(Kajian.column_7,kajian.getLongi());
            contentValues.put(Kajian.column_8,kajian.getDeskripsi());
            contentValues.put(Kajian.column_9,kajian.getJenis_peserta());
            contentValues.put(Kajian.column_10,kajian.getIsRutin());
            boolean result = databaseHandler.insert(Kajian.tabel, contentValues);
            contentValues.clear();
            return result;
        }
    }
    public boolean DeleteKajian(){
        return databaseHandler.delete(Kajian.tabel,null);
    }
    public boolean UpdateKajian(Kajian kajian){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Kajian.column_sid,kajian.getSid());
        contentValues.put(Kajian.column_1,kajian.getTanggal());
        contentValues.put(Kajian.column_2,kajian.getJam());
        contentValues.put(Kajian.column_3,kajian.getTema());
        contentValues.put(Kajian.column_4,kajian.getPemateri());
        contentValues.put(Kajian.column_5,kajian.getAlamat());
        contentValues.put(Kajian.column_6,kajian.getLat());
        contentValues.put(Kajian.column_7,kajian.getLongi());
        contentValues.put(Kajian.column_8,kajian.getDeskripsi());
        contentValues.put(Kajian.column_9,kajian.getJenis_peserta());
        contentValues.put(Kajian.column_10,kajian.getIsRutin());
        boolean result = databaseHandler.update(Kajian.tabel,contentValues,Kajian.column_id+'='+kajian.getId());
        contentValues.clear();
        return result;
    }
    public Kajian GetKajian(WhereHelper whereHelper){
        String where = "";
        for(int i = 0; i < whereHelper.getKey().size(); i++){
            if(i>0){
                where+=whereHelper.getPenghubung().get(i-1);
            }
            where+=whereHelper.getKey().get(i)+whereHelper.getOperator().get(i)+whereHelper.getValue().get(i);
        }
        Cursor cursor = databaseHandler.get(User.tabel,where,"");
        if(cursor.moveToFirst()) {
            return new Kajian(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4)
                    , cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)
                    , cursor.getString(9), cursor.getString(10), cursor.getString(11));
        }
        return null;
    }
    public List<Kajian> GetAllKajian(){
        Cursor cursor = databaseHandler.all(Kajian.tabel,"");
        List<Kajian> kajianList = new ArrayList<>();
        if(cursor.moveToFirst()) {
            kajianList.add(new Kajian(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4)
                    , cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)
                    , cursor.getString(9), cursor.getString(10), cursor.getString(11)));
        }
        return kajianList;
    }
}
