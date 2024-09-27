package com.br.picker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class InputActivity extends AppCompatActivity {

    public static final String RESULTBARCODE = " RESULTBARCODE";
    private String resultado;
    private ItemAdapter itemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        getSupportActionBar().setTitle("");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public void btnScan(View view){

        ScanOptions options = new ScanOptions();
        options.setPrompt("Volumn up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        launcherBarcode.launch(options);

    }
    ActivityResultLauncher<ScanOptions> launcherBarcode = registerForActivityResult(new ScanContract(), result ->{

        if(result.getContents() != null){

            resultado = result.getContents();
            handleScanResult(resultado);

        }
    });


    public void btnList(View view){

        Intent intent = new Intent(this, RecyclerViewItem.class);

        startActivity(intent);
    }

    public void btnNewItem(View view){

        Intent intent = new Intent(this, NewItemActivity.class);

        intent.putExtra(NewItemActivity.MODO,NewItemActivity.NOVO);
        startActivity(intent);

    }
    public void handleScanResult(String resultado) {
        // Exibe o diálogo de confirmação
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(resultado);
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            // Envia o resultado para a NewItemActivity depois do OK
            NewItemActivity.newResult(this, resultado);
            dialogInterface.dismiss();
        }).show();
    }



}