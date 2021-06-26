package com.example.GestionDeRessource;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button callSignUp,loginBtn;
    ImageView image,img;
    TextView textlogo,sloganText;
    TextInputLayout email,password;
    Dialog dialog;


    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);



        callSignUp = findViewById(R.id.callSignUp);
        loginBtn = findViewById(R.id.Signin);
        image = findViewById(R.id.imageLogo);
        textlogo = findViewById(R.id.logoName);
        sloganText = findViewById(R.id.sloganName);
        email = findViewById(R.id.lusername);
        password = findViewById(R.id.lpassword);
        img = findViewById(R.id.imageView3);
        dialog = new Dialog(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("users");

        //go to sign up
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignUp.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(textlogo,"logo_text");
                pairs[2] = new Pair<View,String>(sloganText,"logo_desc");
                pairs[3] = new Pair<View,String>(email,"email_tran");
                pairs[4] = new Pair<View,String>(password,"Password_tran");
                pairs[5] = new Pair<View,String>(loginBtn,"button_tran");
                pairs[6] = new Pair<View,String>(callSignUp,"login_signup_tran");
                //pairs[7] = new Pair<View,String>(img,"logo_image");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                    startActivity(intent,options.toBundle());
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });


    }

    public void login()
    {
        final String mail = email.getEditText().getText().toString();
        final String pass = password.getEditText().getText().toString();

        if(mail.equals("") ||  pass.equals(""))
        {
            openDialogErr();
        }

        else

        {
            mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String id = user.getUid();

                        if(user==null)
                        {
                            openDialogErr();
                        }
                        else
                        {
                            mDatabase.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String fullname = snapshot.child("nom").getValue(String.class);
                                    String username = snapshot.child("prenom").getValue(String.class);
                                    String email = snapshot.child("email").getValue(String.class);
                                    String phone = snapshot.child("phone").getValue(String.class);
                                    String password = snapshot.child("password").getValue(String.class);
                                    String uid = snapshot.child("userid").getValue(String.class);
                                    int type = snapshot.child("type").getValue(Integer.class);

                                    //System.out.println(type);

                                    User us = new User(uid, fullname, username, email, phone, password,type);

                                    System.out.println(us.getType());

                                    SessionManager session = new SessionManager(Login.this);
                                    session.createLoginSession(us.getUserid(), us.getnom(), us.getprenom(), us.getEmail(), us.getPhone(), us.getPassword(),String.valueOf(us.getType()) );

                                    if (us.getType() == 1) {
                                        Intent intent = new Intent(Login.this, DashAdmin.class);


                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                            ActivityOptions options =
                                                    ActivityOptions.makeSceneTransitionAnimation(Login.this, Pair.<View, String>create(image, "logo_image"));
                                            startActivity(intent, options.toBundle());
                                        }


                                    }
                                    else if (us.getType() == 0){
                                        Intent intent3 = new Intent(Login.this, DashUser.class);

                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                            ActivityOptions options =
                                                    ActivityOptions.makeSceneTransitionAnimation(Login.this, Pair.<View, String>create(image, "logo_image"));
                                            startActivity(intent3, options.toBundle());
                                        }
                                    }
                                }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }


                        }
                    else
                        {
                            openDialogErr();
                        }
                    }
                });
            }



        }
    //danger alert

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


