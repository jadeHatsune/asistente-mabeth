package com.tonala.mabeth.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;
import com.tonala.mabeth.BuildConfig;
import com.tonala.mabeth.Inicio.MainActivity;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.databinding.ActivityMenuSlideBinding;
import com.tonala.mabeth.ui.Notas.ListaNotasTextoActivity;
import com.tonala.mabeth.ui.Settings.SettingsActivity;

import org.w3c.dom.Text;


public class MenuSlideActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private int xd54=0;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuSlideBinding binding;
    public static int xd2;
    public static int variablea;
    public static int variableb;
    public static int variablec;
    public static int  variableinex3;
    public static int variableinex2;
    public static int  variableinex4;
    public static int  variableinex5;
    public static int  variableinex6;
    public static int  variableinex7;
    private int prio;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if(!isConnected){
                startActivity(new Intent(getApplicationContext(), errorActivity.class));
                finish();
            }else {
                super.onCreate(savedInstanceState);
                mAuth = FirebaseAuth.getInstance();
                mDatabase = FirebaseDatabase.getInstance().getReference();


                binding = ActivityMenuSlideBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());


                setSupportActionBar(binding.appBarMenuSlide.toolbar);

                if(xd54==0){
                switch(getFirstTimeRun()) {
                    case 0:
                        showSuccesDialog();
                        xd2=0;
                        break;
                    default:
                        xd2=1;
                        variablea=1;
                        variableb=1;
                        variablec=1;
                        variableinex3=1;
                        variableinex2=1;
                        variableinex4=1;
                        variableinex5=1;
                        variableinex6=1;
                        variableinex7=1;
                        break;
                }}

                DrawerLayout drawer = binding.drawerLayout;
                NavigationView navigationView = binding.navView;

                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_home, R.id.nav_Recursos, R.id.nav_Calendar, R.id.nav_homeAd,
                        R.id.nav_userAd, R.id.nav_adminAd, R.id.nav_Gestor, R.id.nav_notas,
                        R.id.nav_foros, R.id.nav_gps)
                        .setOpenableLayout(drawer)
                        .build();
                NavController navController = Navigation.findNavController(this, R.id.escenario);
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);


                View headerView = navigationView.getHeaderView(0);
                TextView lblNombrehad = (TextView) headerView.findViewById(R.id.txtNombreHad);
                TextView lblCorreohad = (TextView) headerView.findViewById(R.id.txtCorreoHad);
                ImageView imgP = (ImageView) headerView.findViewById(R.id.imageView);
                String id = mAuth.getCurrentUser().getUid();
                mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String name = dataSnapshot.child("Name").getValue().toString();
                            String email = dataSnapshot.child("Email").getValue().toString();
                            String URLFP = dataSnapshot.child("URL FP").getValue().toString();
                            String prioridad = dataSnapshot.child("Prioridad").getValue().toString();


                            lblCorreohad.setText(email);
                            lblNombrehad.setText(name);
                            Picasso.get()
                                    .load(URLFP)
                                    .error(R.mipmap.ic_launcher_round)
                                    .into(imgP);
                            prio = Integer.parseInt(prioridad);

                            if (prio == 0) {
                                navigationView.getMenu().setGroupEnabled(R.id.grupo_usuario, true);
                                navigationView.getMenu().setGroupEnabled(R.id.grupo_admin, false);
                                navigationView.getMenu().setGroupVisible(R.id.grupo_usuario, true);
                                navigationView.getMenu().setGroupVisible(R.id.grupo_admin, false);
                            }
                            if (prio == 1) {
                                navController.navigate(R.id.action_trans);
                                navigationView.getMenu().setGroupEnabled(R.id.grupo_usuario, false);
                                navigationView.getMenu().setGroupEnabled(R.id.grupo_admin, true);
                                navigationView.getMenu().setGroupVisible(R.id.grupo_usuario, false);
                                navigationView.getMenu().setGroupVisible(R.id.grupo_admin, true);
                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_slide, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item){
            switch(item.getItemId()){
                case R.id.CerrarS:
                    this.logout();
                    break;
                case R.id.Settings:
                    this.Settings();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

    @Override
        public boolean onSupportNavigateUp() {
            NavController navController = Navigation.findNavController(this, R.id.escenario);
            return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                    || super.onSupportNavigateUp();
        }

    private int getFirstTimeRun() {
        SharedPreferences sp = getSharedPreferences("MYAPP", 0);
        int result, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0; else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        xd54++;
        variablea = 0;
        variableb = 0;
        variablec = 0;
        variableinex3=0;
        variableinex2=0;
        variableinex4=0;
        variableinex5=0;
        variableinex6=0;
        variableinex7=0;
        return result;
    }



    private void showSuccesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuSlideActivity.this);
        View view = LayoutInflater.from(MenuSlideActivity.this).inflate(R.layout.layout_dialog5, (ConstraintLayout)findViewById(R.id.layoutDialogContainer5));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showSuccesDialog2();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showSuccesDialog2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuSlideActivity.this);
        View view = LayoutInflater.from(MenuSlideActivity.this).inflate(R.layout.layout_dialog, (ConstraintLayout)findViewById(R.id.layoutDialogContainer1));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showSuccesDialog3();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showSuccesDialog3(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuSlideActivity.this);
        View view = LayoutInflater.from(MenuSlideActivity.this).inflate(R.layout.layout_dialog6, (ConstraintLayout)findViewById(R.id.layoutDialogContainer6));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showSuccesDialog7();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void showSuccesDialog7(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuSlideActivity.this);
        View view = LayoutInflater.from(MenuSlideActivity.this).inflate(R.layout.layout_dialog7, (ConstraintLayout)findViewById(R.id.layoutDialogContainer7));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnDialog7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(MenuSlideActivity.this, MainActivity.class));
        finish();
    }

    private void Settings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);

    }
}