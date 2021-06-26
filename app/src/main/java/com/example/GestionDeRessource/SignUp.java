package com.example.GestionDeRessource;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextInputLayout fn,us,email,phone,password;
    private Button su,sl;
    ImageView ima;
    TextView txtv1,txtv2;

    private static final String TAG = "SignUp";

    private User user;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        init();
    }

    private void init() {
        fn = (TextInputLayout) findViewById(R.id.fullname);
        us = (TextInputLayout) findViewById(R.id.username);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        phone = (TextInputLayout) findViewById(R.id.phone);
        su = (Button) findViewById(R.id.su);
        sl = (Button) findViewById(R.id.sl);

        ima = (ImageView) findViewById(R.id.ima);
        txtv1 = (TextView) findViewById(R.id.txtv1);
        txtv2 = (TextView) findViewById(R.id.txtv2);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();
        dialog = new Dialog(this);
        ecouteur();
    }

    public void ecouteur() {
        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fln = fn.getEditText().getText().toString();
                String usn = us.getEditText().getText().toString();
                String em = email.getEditText().getText().toString();
                String tel = phone.getEditText().getText().toString();
                String pw = password.getEditText().getText().toString();

                if(fln.equals("") || usn.equals("") || em.equals("") || tel.equals("") || pw.equals(""))
                    openDialogErr();
                else
                {
                    user = new User(fln,usn,em,tel,pw,0);
                    register(user.getEmail(),user.getPassword());
                }
            }
        });


        sl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,Login.class);
                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View,String>(ima,"logo_image");
                pairs[1] = new Pair<View,String>(txtv1,"logo_text");
                pairs[2] = new Pair<View,String>(txtv2,"logo_desc");
                pairs[3] = new Pair<View,String>(email,"email_tran");
                pairs[4] = new Pair<View,String>(password,"Password_tran");
                pairs[5] = new Pair<View,String>(su,"button_tran");
                pairs[6] = new Pair<View,String>(sl,"login_signup_tran");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this,pairs);
                    startActivity(intent,options.toBundle());
                }

            }
        });
    }

   public void register(String email,String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            openDialogErr();

                        }

                        // ...
                    }
                });
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

    private void openDialogSucc()
    {
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button btnOk   = dialog.findViewById(R.id.BtnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void updateUI(FirebaseUser currentUser)
    {
        user.setUserid(currentUser.getUid());
        mDatabase.child(user.getUserid()).setValue(user);
        openDialogSucc();
    }




}