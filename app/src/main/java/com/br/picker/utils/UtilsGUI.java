package com.br.picker.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.br.picker.R;

public class UtilsGUI {
    public static  void  alert(Context context,int idTexto){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.warning);

        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(idTexto);
        
        builder.setNeutralButton(R.string.ok,null);

        AlertDialog alert = builder.create();
        alert.show();
        
    }


    public static void ConirmationAlert(Context context, String msg, DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.confirmation);
        builder.setIcon(android.R.drawable.ic_dialog_info);

        builder.setMessage(msg);

        builder.setPositiveButton("Yes", listener);
        builder.setNegativeButton("no",listener);

        AlertDialog alert = builder.create();

        alert.show();
    }
}
