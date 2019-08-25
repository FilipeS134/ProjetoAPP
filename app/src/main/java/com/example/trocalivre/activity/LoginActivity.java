package com.example.trocalivre.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trocalivre.R;

public class LoginActivity extends AppCompatActivity {

    private EditText email, senha;
    private Button logar;
    private TextView textCriarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editText_email);
        senha = findViewById(R.id.editText_senha);
        logar = findViewById(R.id.button_logar);
        textCriarConta = findViewById(R.id.textView_criarConta);

       textCriarConta.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
               startActivity(intent);
           }
       });

    }
}
