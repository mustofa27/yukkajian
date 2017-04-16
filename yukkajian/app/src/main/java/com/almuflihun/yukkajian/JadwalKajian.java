package com.almuflihun.yukkajian;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.KajianAdapter;

public class JadwalKajian extends AppCompatActivity {

    //GridView gridView;
    ListView listView;
    TextView tanggal;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_kajian);
        /*gridView = (GridView) findViewById(R.id.calendar);
        gridView.setAdapter(new CalendarAdapter(this));*/
        calendar = Calendar.getInstance();
        tanggal = (TextView) findViewById(R.id.tanggal);
        tanggal.setText(String.valueOf(calendar.get(calendar.DAY_OF_MONTH)) + " " +
                new DateFormatSymbols().getMonths()[calendar.get(calendar.MONTH)] + " " + String.valueOf(calendar.get(calendar.YEAR)));
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog mDialog = new DatePickerDialog(JadwalKajian.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        tanggal.setText(String.valueOf(dayOfMonth) + " " +
                                new DateFormatSymbols().getMonths()[monthOfYear] + " " + String.valueOf(year));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                mDialog.show();
            }
        });
        listView = (ListView) findViewById(R.id.list_kajian);
        List<Integer> integers = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            integers.add(new Integer(i));
        }
        KajianAdapter adapter = new KajianAdapter(this, integers);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,MainActivity.class));
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
