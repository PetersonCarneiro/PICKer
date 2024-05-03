package com.br.picker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText editTextPlate;
    private EditText editTextLocale;
    private RadioGroup radioGroupStatus;
    private Spinner spinnerLocale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPlate = findViewById(R.id.editTextPlate);
        editTextLocale = findViewById(R.id.editTextLocale);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        spinnerLocale = findViewById(R.id.spinnerLocale);

        spinnerLocale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view,
                                       int i,
                                       long l) {
                String nome = adapterView.getItemAtPosition(i).toString();

                if(nome.equals(getString(R.string.barracao))){
                    spinnerLocale();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerLocale();

    }

    private void spinnerLocale(){
        String[] nomes = getResources().getStringArray(R.array.lacale);

        ArrayList<Locale> locales = new ArrayList<>();

        for(int i = 0; i< nomes.length;i++){
            locales.add(new Locale(nomes[i]));
        }


        spinnerLocale.setAdapter();

    }

    public void clean(View view){
        editTextPlate.setText(null);
        editTextLocale.setText(null);
        radioGroupStatus.setActivated(false);

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
        int selectRadio = radioGroupStatus.getCheckedRadioButtonId();
        if(selectRadio == R.id.radioButtonLocated){

        }

        Toast.makeText(this,R.string.save,Toast.LENGTH_SHORT).show();
    }
}