package com.example.trocalivre.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trocalivre.R;
import com.example.trocalivre.config.ConfiguracaoFirebase;
import com.example.trocalivre.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private Button sair;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private TextView nome,email;
    private ImageView imagenPerfil, imagenCapa;


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        auth = ConfiguracaoFirebase.getAutenticação();
        user = auth.getCurrentUser();
        databaseReference = ConfiguracaoFirebase.getDatabase().child("usuarios");

        nome = view.findViewById(R.id.textView_nomePerfil);
        email = view.findViewById(R.id.textView_emailPerfil);
        imagenPerfil = view.findViewById(R.id.imageView_perfil);
        imagenCapa = view.findViewById(R.id.imageView_capaPerfil);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Usuario usuario = snapshot.getValue(Usuario.class);

                    nome.setText(usuario.getNome());
                    email.setText(usuario.getEmail());

                    try {
                        Picasso.get().load(usuario.getImagem()).into(imagenPerfil);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.padrao).into(imagenPerfil);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        sair = view.findViewById(R.id.button_sair);

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                getActivity().onBackPressed();
            }
        });

        return view;
    }



}
