package com.almuflihun.yukkajian;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import helper.FontManager;

public class KelolaKajian extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_kajian);
        FontManager.markAsIconContainer(findViewById(R.id.container_icon),FontManager.getTypeface(this,FontManager.font_awesome));
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(KelolaKajian.this,CreateKajian.class),1);
            }
        });
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
