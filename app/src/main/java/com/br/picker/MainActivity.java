package com.br.picker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextPlate;
    private EditText editTextLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPlate = findViewById(R.id.editTextPlate);
        editTextLocale = findViewById(R.id.editTextLocale);
    }

    public void clean(View view){
        editTextPlate.setText(null);
        editTextLocale.setText(null);

        editTextPlate.requestFocus();
    }

    public void notification(View view){
        String plate = editTextPlate.getText().toString();
        String locale = editTextLocale.getText().toString();

        if(plate ==  null  || plate.trim().isEmpty()){
            Toast.makeText(this,getString(R.string.plaqueta)+" - "+getString(R.string.nao_pode_ser_vazio),Toast.LENGTH_SHORT).show();
            editTextPlate.requestFocus();
            return;
        }
        if(locale == null || locale.trim().isEmpty()){
            Toast.makeText(this,getString(R.string.localizacao)+" - "+getString(R.string.nao_pode_ser_vazio),Toast.LENGTH_SHORT).show();
            editTextLocale.requestFocus();
            return;
        }

        Toast.makeText(this,R.string.salvar,Toast.LENGTH_SHORT).show();
    }
}