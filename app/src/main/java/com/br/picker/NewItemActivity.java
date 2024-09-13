package com.br.picker;

import static com.br.picker.InputActivity.RESULTBARCODE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.br.picker.utils.UtilsGUI;
import com.journeyapps.barcodescanner.ScanOptions;


public class NewItemActivity extends AppCompatActivity {

    public static  final String MODO = "MODO";
    public static  final int NOVO = 1;
    public static  final int EDITAR = 2;
    public  static  final int RESULTADO = 3;
    private static int modo;

    private String plaquetaOriginal;
    private String tipoOriginal;
    private String localizacaoOriginal;
    private String situacaoOriginal;
    private String plaquetaResult;

    public static final String PLATE = "PLATE";
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
        setContentView(R.layout.activity_new_item);

        editTextPlate = findViewById(R.id.editTextPlate);
        editTextType = findViewById(R.id.editTextType);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        spinnerLocale = findViewById(R.id.spinnerLocale);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!= null){
            modo = bundle.getInt(MODO,NOVO);
            if(modo == NOVO){
                setTitle("Novo Item");
            }else if (modo == EDITAR){
                setTitle("Editar Item");

                plaquetaOriginal = bundle.getString(PLATE);
                tipoOriginal = bundle.getString(TYPE);
                localizacaoOriginal = bundle.getString(LOCALE);
                situacaoOriginal = bundle.getString(STATUS);

                editTextPlate.setText(plaquetaOriginal);
                editTextPlate.setSelection(editTextPlate.getText().length());
                editTextType.setText(tipoOriginal);

                for (int i = 0; i < spinnerLocale.getCount(); i++) {
                    if (spinnerLocale.getItemAtPosition(i).toString().equals(localizacaoOriginal)) {
                        spinnerLocale.setSelection(i);
                        break;
                    }
                }

                if (situacaoOriginal.equals(getString(R.string.naoLocalizado))) {
                    radioGroupStatus.setActivated(true);
                    radioGroupStatus.check(R.id.radioButtonNotFound);
                } else if (situacaoOriginal.equals(getString(R.string.localizado))) {
                    radioGroupStatus.check(R.id.radioButtonFound);
                }

            }else if(modo == RESULTADO){
                setTitle("Resultado BarCode");

                if (bundle != null) {
                    String resultInputBarCode = bundle.getString(RESULTBARCODE);
                    editTextPlate.setText(resultInputBarCode);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_item_edit_option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.idMenuSave) {
            save();
            return  true;
        } else if (itemId == R.id.idMenuClean) {
            cancelar();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void clean(){
        editTextPlate.setText(null);
        editTextType.setText(null);
        radioGroupStatus.setActivated(false);
        editTextPlate.requestFocus();

    }

    public void save(){

        String plate = editTextPlate.getText().toString();
        String type = editTextType.getText().toString();

        if(plate ==  null  || plate.trim().isEmpty()){
            //Toast.makeText(this,getString(R.string.plaqueta)+" - "+getString(R.string.nao_pode_ser_vazio),Toast.LENGTH_SHORT).show();
            UtilsGUI.alert(this, R.string.nao_pode_ser_vazio);
            editTextPlate.requestFocus();
            return;
        }

        if(type == null || type.trim().isEmpty()){
            //Toast.makeText(this,getString(R.string.tipo)+" - "+getString(R.string.nao_pode_ser_vazio),Toast.LENGTH_SHORT).show();
            UtilsGUI.alert(this, R.string.nao_pode_ser_vazio);
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
            //Toast.makeText(this,getString(R.string.situacao) + " - " +getString(R.string.nao_pode_ser_vazio),Toast.LENGTH_SHORT).show();
            UtilsGUI.alert(this,R.string.nao_pode_ser_vazio);
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

        Intent intent = new Intent(activity, NewItemActivity.class);

        intent.putExtra(MODO,NOVO);

        launcher.launch(intent);
    }

    public static void editItem(AppCompatActivity activity, ActivityResultLauncher<Intent> launcher, Item item){

        Intent intent = new Intent(activity, NewItemActivity.class);

        intent.putExtra(MODO,EDITAR);

        intent.putExtra(PLATE,item.getPlaqueta());
        intent.putExtra(TYPE,item.getTipo());
        intent.putExtra(STATUS, item.getSituacao());
        intent.putExtra(LOCALE,item.getLocalizacao());

        launcher.launch(intent);
    }
    public static void newResult(AppCompatActivity activity, String resultado){

        Intent intent = new Intent(activity, NewItemActivity.class);
        intent.putExtra(MODO, RESULTADO);
        intent.putExtra(RESULTBARCODE, resultado);
        activity.startActivity(intent);
    }






}