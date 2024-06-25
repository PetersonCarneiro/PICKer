package com.br.picker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }

    public void btnScan(View view){
        Intent intent = new Intent();
    }

    public void btnEnter(View view){
        Intent intent = new Intent(this, RecyclerViewItem.class);

        startActivity(intent);
    }
}