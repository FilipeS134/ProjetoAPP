package com.example.trocalivre.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trocalivre.R;
import com.example.trocalivre.config.ConfiguracaoFirebase;
import com.example.trocalivre.helper.Base64Custom;
import com.example.trocalivre.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegistroActivity extends AppCompatActivity {

    private EditText nome, email, senha, confirmar_senha;
    private CircularProgressButton criarConta;
    private FirebaseAuth auntenticao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nome = findViewById(R.id.editText_nome);
        email = findViewById(R.id.editText_email);
        senha = findViewById(R.id.editText_senha);
        confirmar_senha = findViewById(R.id.editText_senhaConfirmar);
        criarConta = findViewById(R.id.button_registrar);

        criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarUsuario();
            }
        });

    }

    private void criarUsuario() {
        String nome = this.nome.getText().toString();
        String email = this.email.getText().toString();
        String senha = this.senha.getText().toString();
        String confSenha = this.confirmar_senha.getText().toString();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!senha.isEmpty() && !confSenha.isEmpty()) {
                    if (senha.equals(confSenha)) {
                        usuario = new Usuario();
                        usuario.setNome(nome);
                        usuario.setEmail(email);
                        usuario.setSenha(senha);
                        configCadastro();
                    } else {
                        Toast.makeText(this, "As senhas não conferem", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "O campo senha está vazio", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "O campo Email está vazio", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "O campo Nome está vazio", Toast.LENGTH_SHORT).show();
        }
    }

    public void configCadastro() {
        criarConta.startAnimation();
        auntenticao = ConfiguracaoFirebase.getAutenticação();
        auntenticao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String excecao = "";
                        if (task.isSuccessful()) {
                            String idUser = Base64Custom.codificarBase64(usuario.getEmail());
                            usuario.setidUsuario(idUser);
                            usuario.salvar();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        } else {
                            criarConta.revertAnimation();
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                excecao = "Digite uma senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "Por favor, digite um e-mail válido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                excecao = "Esta conta já foi cadastrada";
                            } catch (Exception e) {
                                excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(), excecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
