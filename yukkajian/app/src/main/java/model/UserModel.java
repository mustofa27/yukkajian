package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import entity.User;


/**
 * Created by LENOVO on 3/9/2017.
 */

public class UserModel {
    private DatabaseHandler databaseHandler;
    public UserModel(Context context){
        databaseHandler = new DatabaseHandler(context);
    }
    public boolean InsertUser(User user){
        List<String> key = new ArrayList<>();
        key.add(User.column_sid);
        List<String> operator = new ArrayList<>();
        operator.add(WhereHelper.equal);
        List<String> val = new ArrayList<>();
        val.add(String.valueOf(user.getSid()));
        WhereHelper whereHelper = new WhereHelper(key,val,operator,null);
        User current = GetUser(whereHelper);
        if(current!=null){
            return UpdateUser(current);
        }
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(User.column_sid,user.getSid());
            contentValues.put(User.column_1,user.getUsername());
            contentValues.put(User.column_2,user.getPassword());
            contentValues.put(User.column_3,user.getEmail());
            contentValues.put(User.column_4,user.getNama());
            contentValues.put(User.column_5,user.getAlamat_asal());
            contentValues.put(User.column_6,user.getJenis_kelamin());
            contentValues.put(User.column_7,user.getAlamat_domisili());
            contentValues.put(User.column_8,user.getNomor_hp());
            contentValues.put(User.column_9,user.getPekerjaan());
            contentValues.put(User.column_10,user.getTempat_lahir());
            contentValues.put(User.column_11,user.getTanggal_lahir());
            boolean result = databaseHandler.insert(User.tabel, contentValues);
            contentValues.clear();
            return result;
        }
    }
    public boolean DeleteUser(){
        return databaseHandler.delete(User.tabel,null);
    }
    public boolean UpdateUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.column_sid,user.getSid());
        contentValues.put(User.column_1,user.getUsername());
        contentValues.put(User.column_2,user.getPassword());
        contentValues.put(User.column_3,user.getEmail());
        contentValues.put(User.column_4,user.getNama());
        contentValues.put(User.column_5,user.getAlamat_asal());
        contentValues.put(User.column_6,user.getJenis_kelamin());
        contentValues.put(User.column_7,user.getAlamat_domisili());
        contentValues.put(User.column_8,user.getNomor_hp());
        contentValues.put(User.column_9,user.getPekerjaan());
        contentValues.put(User.column_10,user.getTempat_lahir());
        contentValues.put(User.column_11,user.getTanggal_lahir());
        boolean result = databaseHandler.update(User.tabel,contentValues,User.column_id+'='+user.getId());
        contentValues.clear();
        return result;
    }
    public User GetUser(WhereHelper whereHelper){
        String where = "";
        for(int i = 0; i < whereHelper.getKey().size(); i++){
            if(i>0){
                where+=whereHelper.getPenghubung().get(i-1);
            }
            where+=whereHelper.getKey().get(i)+whereHelper.getOperator().get(i)+whereHelper.getValue().get(i);
        }
        Cursor cursor = databaseHandler.get(User.tabel,where,"");
        if(cursor.moveToFirst()) {
            return new User(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)
                    , cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)
                    , cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12));
        }
        return null;
    }
    public User GetUser(){
        Cursor cursor = databaseHandler.all(User.tabel,"");
        if(cursor.moveToFirst()) {
            return new User(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)
                    , cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)
                    , cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12));
        }
        return null;
    }
}
