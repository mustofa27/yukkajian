package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import entity.Jadwal;
import entity.Kajian;
import entity.Pertanyaan;
import entity.User;

/**
 * Created by LENOVO on 2/2/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yk.db";
    private static final String CREATE_TABLE_kajian = "CREATE TABLE " + Kajian.tabel+ " ( " +
            Kajian.column_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Kajian.column_sid + " INTEGER, " +
            Kajian.column_10 + " INTEGER, " +
            Kajian.column_1 + " TEXT, " +
            Kajian.column_2 + " TEXT, " +
            Kajian.column_3 + " TEXT, " +
            Kajian.column_4 + " TEXT, " +
            Kajian.column_5 + " TEXT, " +
            Kajian.column_6 + " TEXT, " +
            Kajian.column_7 + " TEXT, " +
            Kajian.column_8 + " TEXT, " +
            Kajian.column_9 + " TEXT, " +
            Kajian.column_11 + " TEXT)"
            ;
    private static final String CREATE_TABLE_jadwal = "CREATE TABLE " + Jadwal.tabel+ " ( " +
            Jadwal.column_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Jadwal.column_1 + " TEXT, " +
            Jadwal.column_2 + " TEXT, " +
            Jadwal.column_3 + " TEXT, " +
            Jadwal.column_4 + " TEXT, " +
            Jadwal.column_5 + " TEXT, " +
            Jadwal.column_6 + " TEXT, " +
            Jadwal.column_7 + " TEXT, " +
            Jadwal.column_8 + " TEXT, " +
            Jadwal.column_9 + " TEXT)"
            ;
    private static final String CREATE_TABLE_user = "CREATE TABLE " + User.tabel+ " ( " +
            User.column_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            User.column_sid + " INTEGER, " +
            User.column_1 + " TEXT, " +
            User.column_2 + " TEXT, " +
            User.column_3 + " TEXT, " +
            User.column_4 + " TEXT, " +
            User.column_5 + " TEXT, " +
            User.column_6 + " TEXT, " +
            User.column_7 + " TEXT, " +
            User.column_8 + " TEXT, " +
            User.column_9 + " TEXT, " +
            User.column_10 + " TEXT, " +
            User.column_11 + " TEXT)"
            ;
    private static final String CREATE_TABLE_pertanyaan = "CREATE TABLE " + Pertanyaan.tabel+ " ( " +
            Pertanyaan.column_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Pertanyaan.column_sid + " INTEGER, " +
            Pertanyaan.column_1 + " INTEGER, " +
            Pertanyaan.column_2 + " INTEGER, " +
            Pertanyaan.column_3 + " TEXT)"
            ;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_pertanyaan);
        db.execSQL(CREATE_TABLE_kajian);
        db.execSQL(CREATE_TABLE_user);
        db.execSQL(CREATE_TABLE_jadwal);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Kajian.tabel);
        db.execSQL("DROP TABLE IF EXISTS " + Pertanyaan.tabel);
        db.execSQL("DROP TABLE IF EXISTS " + User.tabel);
        db.execSQL("DROP TABLE IF EXISTS " + Jadwal.tabel);
        onCreate(db);
    }
    public boolean insert(String table,  ContentValues contentValues)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(table, null, contentValues);
        return id != -1;
    }
    public Cursor all(String table, String order)
    {
        String query = "SELECT * FROM " + table + " ";
        if (!order.isEmpty())
        {
            query += order;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return  cursor;
    }
    public Cursor get(String table, String where, String order)
    {
        String query = "SELECT * FROM " + table + " ";
        if (!where.isEmpty())
        {
            query += " where " + where;
        }
        if (!order.isEmpty())
        {
            query += " " + order;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return  cursor;
    }

    public boolean update(String table, ContentValues contentValues, String filter)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = db.update(table, contentValues, filter, null);
        return id == 1;
    }
    public boolean delete(String table, String filter)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result;
        result = db.delete(table, filter, null) != 0;
        db.close();
        return result;
    }
    public int countAll(String table, String where){

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select count(*) from " + table ;
        if(!where.isEmpty()){
            sql = sql  + " where " + where;
        }
        Cursor cursorCount = db.rawQuery(sql, null);
        cursorCount.moveToFirst();
        int count = cursorCount.getInt(0);
        cursorCount.close();
        return count;
    }
    public Cursor statementRaw(String query)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public boolean truncate(String table)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(table, null, null);
        db.execSQL("VACUUM");
        return  result==1;
    }
    public void truncateAll(){
        truncate(Kajian.tabel);
        truncate(Pertanyaan.tabel);
        truncate(User.tabel);
    }
}
