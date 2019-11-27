package com.example.trocalivre.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.trocalivre.R;
import com.example.trocalivre.fragment.AnuncioFragment;
import com.example.trocalivre.fragment.Home_Fragment;
import com.example.trocalivre.fragment.MenssagemFragment;
import com.example.trocalivre.fragment.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigationView = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.frameLayout);

        infla(new Home_Fragment());

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home :
                        infla(new Home_Fragment());
                        return true;
                    case R.id.nav_anuncio :
                        infla(new AnuncioFragment());
                        return true;
                    case R.id.nav_menssagem :
                        infla(new MenssagemFragment());
                        return true;
                    case R.id.nav_perfil :
                        infla(new PerfilFragment());
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    public void infla(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }
}
