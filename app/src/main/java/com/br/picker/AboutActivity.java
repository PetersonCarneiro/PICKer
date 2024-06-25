package com.br.picker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public static  void about(AppCompatActivity activity){
        Intent intent = new Intent(activity, AboutActivity.class);

        activity.startActivity(intent);

    }
}