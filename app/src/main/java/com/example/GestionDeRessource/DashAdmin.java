package com.example.GestionDeRessource;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DashAdmin extends AppCompatActivity {

    private ImageView lg;
    private SessionManager session;
    RelativeLayout addEquipment,consult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_admin);
        session = new SessionManager(DashAdmin.this);
        init();
    }

    private void init() {
        addEquipment  =  (RelativeLayout) findViewById(R.id.add);
        consult = (RelativeLayout) findViewById(R.id.consult);
        lg = (ImageView) findViewById(R.id.lg);
        ecouteur();
    }

    private void ecouteur() {
        addEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToAddEq();

            }

        });
        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToConsult();
            }
        });
        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToLogOut();
            }
        });
    }
    private void ToLogOut() {
        new AlertDialog.Builder(this)
                .setTitle("Sign Out ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        session.logoutSession();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(DashAdmin.this,Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).create().show();
    }

    private void ToConsult() {
        Intent ii = new Intent(DashAdmin.this,ConsultEquipment.class);
        startActivity(ii);
    }

    private void ToAddEq() {
        Intent i = new Intent(DashAdmin.this,AddEquipment.class);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(getApplicationContext(), Exit.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }



}