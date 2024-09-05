package com.br.picker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    }

    public void btnScan(View view){

        ScanOptions options = new ScanOptions();
        options.setPrompt("Volumn up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        launcherBarcode.launch(options);
        NewItemActivity.newResult(this,resultado);

    }
    ActivityResultLauncher<ScanOptions> launcherBarcode = registerForActivityResult(new ScanContract(), result ->{


        if(result.getContents() != null){

           resultado = result.getContents();


            /*
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();*/
        }
    });


    public void btnEnter(View view){
        Intent intent = new Intent(this, RecyclerViewItem.class);

        startActivity(intent);
    }
}