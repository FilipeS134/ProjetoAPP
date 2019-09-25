package com.example.trocalivre.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trocalivre.R;
import com.example.trocalivre.config.ConfiguracaoFirebase;
import com.example.trocalivre.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class LoginActivity extends AppCompatActivity {


    private EditText email, senha;
    private CircularProgressButton logar;
    private TextView textCriarConta;
    private FirebaseAuth autenticacao;
    private Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editText_emailLogar);
        senha = findViewById(R.id.editText_senhaLogar);
        logar = findViewById(R.id.button_logar);
        textCriarConta = findViewById(R.id.textView_criarConta);


        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });


       textCriarConta.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
               startActivity(intent);

           }
       });

    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUserLogado();
    }

    public void login(){
            String textoEmail = email.getText().toString();
            String textoSenha = senha.getText().toString();

            if (!textoEmail.isEmpty()){
                if (!textoSenha.isEmpty()){

                    usuario = new Usuario();
                    usuario.setEmail(textoEmail);
                    usuario.setSenha(textoSenha);
                    validarLogin();
                    logar.startAnimation();

                }else {
                    Toast.makeText(getApplicationContext(), "Preencha sua Senha!", Toast.LENGTH_SHORT).show();

                }

            }else {
                Toast.makeText(getApplicationContext(), "Preencha seu Email!", Toast.LENGTH_SHORT).show();
            }
        }

    public void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getAutenticação();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            abrirTelaPrincipal();
                        }else {
                            logar.revertAnimation();
                            String excecao = "";
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthInvalidUserException e){
                                excecao = "Usuário não está cadastrado.";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                excecao = "E-mail ou senha não correspondem a um usuário cadastrado!";
                            }catch (Exception e){
                                excecao = "Erro ao logar " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(), excecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void verificarUserLogado(){
        autenticacao = ConfiguracaoFirebase.getAutenticação();
        if (autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
