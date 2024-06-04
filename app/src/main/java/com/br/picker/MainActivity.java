package com.br.picker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static final String PLATE = "NOME";
    public static final String TYPE = "TYPE";
    public static final String LOCALE = "LOCALE";
    public static final String STATUS = "STATUS";


    private EditText editTextPlate;
    private EditText editTextType;
    private RadioGroup radioGroupStatus;
    private Spinner spinnerLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPlate = findViewById(R.id.editTextPlate);
        editTextType = findViewById(R.id.editTextType);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        spinnerLocale = findViewById(R.id.spinnerLocale);

    }

    public void clean(View view){
        editTextPlate.setText(null);
        editTextType.setText(null);
        radioGroupStatus.setActivated(false);
        editTextPlate.requestFocus();
    }

    public void save(View view){

        String plate = editTextPlate.getText().toString();
        String type = editTextType.getText().toString();

        if(plate ==  null  || plate.trim().isEmpty()){
            Toast.makeText(this,getString(R.string.plaqueta)+" - "+getString(R.string.nao_pode_ser_vazio),Toast.LENGTH_SHORT).show();
            editTextPlate.requestFocus();
            return;
        }

        if(type == null || type.trim().isEmpty()){
            Toast.makeText(this,getString(R.string.tipo)+" - "+getString(R.string.nao_pode_ser_vazio),Toast.LENGTH_SHORT).show();
            editTextType.requestFocus();
            return;
        }
        String status= "";
        int selectRadio = radioGroupStatus.getCheckedRadioButtonId();
        if(selectRadio == R.id.radioButtonNotFound){
            status = getString(R.string.naoLocalizado);
        }
        if(selectRadio == R.id.radioButtonFound){
            status = getString(R.string.localizado);

        }
        if(status== null || status.trim().isEmpty()){
            Toast.makeText(this,getString(R.string.situacao)+" - " +getString(R.string.nao_pode_ser_vazio),Toast.LENGTH_SHORT).show();
        return;
        }


        String selectSpinner = spinnerLocale.getSelectedItem().toString();


        Intent intent = new Intent();
        intent.putExtra(PLATE,plate);
        intent.putExtra(TYPE,type);
        intent.putExtra(STATUS,status);
        intent.putExtra(LOCALE,selectSpinner);

        Toast.makeText(this,R.string.save,Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void lista(View viem){
        Intent intent = new Intent(this,RecyclerViewItem.class);

        startActivity(intent);

    }

    public static void newItem(AppCompatActivity activity, ActivityResultLauncher<Intent>launcher){

        Intent intent = new Intent(activity, MainActivity.class);

        launcher.launch(intent);
    }



}