package com.example.trocalivre.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trocalivre.R;
import com.example.trocalivre.config.ConfiguracaoFirebase;
import com.example.trocalivre.helper.Permissoes;
import com.example.trocalivre.model.Anuncio;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import dmax.dialog.SpotsDialog;

public class AddAnuncioActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText titulo, desc, trocoPor;
    private ImageView imagem1, imagem2, imagem3;
    private MaskEditText cep, celular;
    private CircularProgressButton botaoSalvar;
    private Anuncio anuncio;
    private Spinner campoEstado, campoCategoria;
    private StorageReference storage;
    private AlertDialog alertDialog;

    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private List<String> listaFotos = new ArrayList<>();
    private List<String> listaURLFotos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anuncio);

        //configurações init
        storage = ConfiguracaoFirebase.getStorage();

        // validar permissoes de camera
        Permissoes.validarPermissoes(permissoes, this, 1);

        initComp();
        carregarDadosSpinner();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageView_cadastro1:
                escolherImagem(1);
                break;
            case R.id.imageView_cadastro2:
                escolherImagem(2);
                break;
            case R.id.imageView_cadastro3:
                escolherImagem(3);
                break;
        }
    }

    public void escolherImagem(int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            // recuperar a imagem
            Uri imagemSelecionada = data.getData();
            String caminhoImagen = imagemSelecionada.toString();

            //Configura imagem no ImageView
            if (requestCode == 1) {
                imagem1.setImageURI(imagemSelecionada);
            } else if (requestCode == 2) {
                imagem2.setImageURI(imagemSelecionada);
            } else if (requestCode == 3) {
                imagem3.setImageURI(imagemSelecionada);
            }

            listaFotos.add(caminhoImagen);

        }
    }

    private void carregarDadosSpinner() {
        String[] estados = getResources().getStringArray(R.array.estados);
        String[] categorias = getResources().getStringArray(R.array.categoria);

        // configurando Estados
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoEstado.setAdapter(adapter);

        // configurando Estados
        ArrayAdapter<String> adapterCat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoCategoria.setAdapter(adapterCat);
    }

    private void initComp() {
        titulo = findViewById(R.id.editText_titulo);
        desc = findViewById(R.id.editText_desc);
        trocoPor = findViewById(R.id.editText_trocoPor);
        cep = findViewById(R.id.editText_cep);
        celular = findViewById(R.id.editText_celular);
        botaoSalvar = findViewById(R.id.button_salvarAnuncio);
        campoEstado = findViewById(R.id.spinner_estados);
        campoCategoria = findViewById(R.id.spinner_categorias);
        imagem1 = findViewById(R.id.imageView_cadastro1);
        imagem2 = findViewById(R.id.imageView_cadastro2);
        imagem3 = findViewById(R.id.imageView_cadastro3);
        imagem1.setOnClickListener(this);
        imagem2.setOnClickListener(this);
        imagem3.setOnClickListener(this);
    }

    public void salvarAnuncio() {
        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Salvando Anúncio")
                .build();
        alertDialog.show();

        for (int i = 0; i < listaFotos.size(); i++) {
            String urlImagem = listaFotos.get(i);
            int tamanhoLista = listaFotos.size();
            salvarFotoStorage(urlImagem, tamanhoLista, i);
        }

    }

    private void salvarFotoStorage(String urlString, final int totalFotos, int contador) {
        final int count = contador;

        //criar referencia Storage
        StorageReference imagenAnuncio = storage.child("imagens")
                .child("anuncios")
                .child(anuncio.getIdAnuncio())
                .child("imagen" + contador);

        UploadTask uploadTask = imagenAnuncio.putFile(Uri.parse(urlString));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        listaURLFotos.add(task.getResult().toString());

                        if (totalFotos == listaFotos.size()) {
                            anuncio.setFotos(listaURLFotos);
                            anuncio.salvar();
                            alertDialog.dismiss();
                            exibirMensagemErro("Anúncio salvo com sucesso!");
                            finish();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemErro("Falha ao fazer upload das imagens");
                alertDialog.dismiss();
            }
        });
    }

    public void validarCampos(View view) {
        anuncio = configurarAnuncio();

        if (listaFotos.size() != 0) {
            if (!anuncio.getTituloAnuncio().isEmpty()) {
                if (!anuncio.getDescricao().isEmpty()) {
                    if (!anuncio.getTrocoPor().isEmpty()) {
                        if (!anuncio.getCepCidade().isEmpty()) {
                            if (!anuncio.getEstado().isEmpty()) {
                                if (!anuncio.getCategoria().isEmpty()) {
                                    if (!anuncio.getCelular().isEmpty() && anuncio.getCelular().length() >= 10) {
                                        salvarAnuncio();
                                    } else {
                                        exibirMensagemErro("Digite um telefone de contato valido!");
                                    }
                                } else {
                                    exibirMensagemErro("Selecione uma categoria!");
                                }
                            } else {
                                exibirMensagemErro("Selecione um estado!");
                            }
                        } else {
                            exibirMensagemErro("Digite um CEP");
                        }
                    } else {
                        exibirMensagemErro("O campo de Interesses está vazio");
                    }
                } else {
                    exibirMensagemErro("A descrição está vazia");
                }
            } else {
                exibirMensagemErro("O Título está vazio");
            }
        } else {
            exibirMensagemErro("Selecione uma foto!");
        }

    }

    private Anuncio configurarAnuncio() {
        String titulo = this.titulo.getText().toString();
        String desc = this.desc.getText().toString();
        String trocoPor = this.trocoPor.getText().toString();
        String cep = this.cep.getRawText();
        String celular = this.celular.getRawText();
        String estado = this.campoEstado.getSelectedItem().toString();
        String categoria = this.campoCategoria.getSelectedItem().toString();

        Anuncio anuncio = new Anuncio();
        anuncio.setCepCidade(cep);
        anuncio.setTrocoPor(trocoPor);
        anuncio.setDescricao(desc);
        anuncio.setTituloAnuncio(titulo);
        anuncio.setCelular(celular);
        anuncio.setCategoria(categoria);
        anuncio.setEstado(estado);

        return anuncio;
    }

    private void exibirMensagemErro(String menssagem) {
        Toast.makeText(this, menssagem, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResult : grantResults) {
            if (permissaoResult == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
