package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import entity.Jadwal;


/**
 * Created by LENOVO on 2/2/2017.
 */

public class JadwalModel {
    private DatabaseHandler databaseHandler;
    public JadwalModel(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }
    public boolean InsertJadwal(Jadwal jadwal){
        if(GetJadwal() == null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Jadwal.column_3, jadwal.getSubuh());
            contentValues.put(Jadwal.column_4, jadwal.getTerbit());
            contentValues.put(Jadwal.column_5, jadwal.getDzuhur());
            contentValues.put(Jadwal.column_6, jadwal.getAsr());
            contentValues.put(Jadwal.column_7, jadwal.getMaghrib());
            contentValues.put(Jadwal.column_8, jadwal.getIsya());
            contentValues.put(Jadwal.column_1, jadwal.getLatitude());
            contentValues.put(Jadwal.column_2, jadwal.getLongitude());
            contentValues.put(Jadwal.column_9, jadwal.getTanggal());
            boolean result = databaseHandler.insert(Jadwal.tabel, contentValues);
            contentValues.clear();
            return result;
        }
        else
            return UpdateJadwal(jadwal);
    }
    public boolean DeleteJadwal(){
        return databaseHandler.delete(Jadwal.tabel,"");
    }
    public boolean UpdateJadwal(Jadwal jadwal){
        Jadwal current = GetJadwal();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Jadwal.column_3, jadwal.getSubuh());
        contentValues.put(Jadwal.column_4, jadwal.getTerbit());
        contentValues.put(Jadwal.column_5, jadwal.getDzuhur());
        contentValues.put(Jadwal.column_6, jadwal.getAsr());
        contentValues.put(Jadwal.column_7, jadwal.getMaghrib());
        contentValues.put(Jadwal.column_8, jadwal.getIsya());
        contentValues.put(Jadwal.column_1, jadwal.getLatitude());
        contentValues.put(Jadwal.column_2, jadwal.getLongitude());
        contentValues.put(Jadwal.column_9, jadwal.getTanggal());
        boolean result = databaseHandler.update(Jadwal.tabel,contentValues,Jadwal.column_id + " = " + current.getId());
        contentValues.clear();
        return result;
    }
    public Jadwal GetJadwal(){
        Cursor cursor = databaseHandler.get(Jadwal.tabel,"","");
        if(cursor.moveToFirst()) {
            return new Jadwal(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)
                    , cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
        }
        return null;
    }
}
