package com.br.picker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.br.picker.utils.UtilsGUI;

public class loginActivity extends AppCompatActivity {

    private EditText editUserName,editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);

    }

    public boolean validationUser(){

        boolean contidion = false;
        String userName = editUserName.getText().toString();
        String password = editPassword.getText().toString();

        String nomeRegister  = "Peterson";
        String passRegister = "1234";

        if(userName == null || userName.trim().isEmpty()){
            Toast.makeText(this,R.string.nao_pode_ser_vazio,Toast.LENGTH_LONG).show();
            editUserName.requestFocus();
            return false;
        }else if(password == null || password.trim().isEmpty()){
            Toast.makeText(this,R.string.nao_pode_ser_vazio,Toast.LENGTH_LONG).show();
            editPassword.requestFocus();
            return  false;
        }else if(nomeRegister.equalsIgnoreCase(userName) && passRegister.equalsIgnoreCase(password)){
            contidion =  true;
        }else{
            UtilsGUI.alert(this, R.string.user_not_found);
        }
        return contidion;
    }

    public void input(View view){

        if(validationUser()) {
            Intent intent = new Intent(this, InputActivity.class);
            startActivity(intent);
        }
    }

}