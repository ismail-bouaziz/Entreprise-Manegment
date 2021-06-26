package com.example.GestionDeRessource;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEquipment extends AppCompatActivity {

    TextInputLayout quantity,ref,name;
    private Button setl;
    FirebaseStorage storage;
    StorageReference storageReference;
    private SessionManager session;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_equipment);
        Init();
    }

    private void Init() {
        setl = (Button) findViewById(R.id.setl);
        quantity = (TextInputLayout) findViewById(R.id.quantity);
        ref = (TextInputLayout) findViewById(R.id.ref);
        name = (TextInputLayout) findViewById(R.id.name);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        session = new SessionManager(AddEquipment.this);
        dialog = new Dialog(this);
        ecouteur();
    }

    private void ecouteur() {
        setl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToAdd();
            }
        });
    }

    private void ToAdd() {

        String reference = ref.getEditText().getText().toString();
        String nameEq = name.getEditText().getText().toString();
        int qt = Integer.parseInt(quantity.getEditText().getText().toString());
        Equipment eq =new Equipment(reference,nameEq,qt);
        String key = FirebaseDatabase.getInstance().getReference().push().getKey();
        DatabaseReference recl = FirebaseDatabase.getInstance().getReference("Equipment").child(key);
        recl.setValue(eq);
        Intent intent2 =new Intent(AddEquipment.this,DashAdmin.class);
        startActivity(intent2);
    }
    private void openDialogSucc()
    {
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button btnOk   = dialog.findViewById(R.id.BtnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(AddEquipment.this,DashAdmin.class);
                startActivity(in);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openDialogErr()
    {
        dialog.setContentView(R.layout.danger_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button btnOk   = dialog.findViewById(R.id.BtnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}