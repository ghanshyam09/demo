package com.example.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText Name,mail;
    private EditText pasword;
    private TextView login;
    private Button Signup;
    private FirebaseAuth fire;
    private ProgressDialog progress;
    public DatabaseReference data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //layout=findViewById(R.id.layout);
//              AnimationDrawable animationDrawable=(AnimationDrawable)layout.getBackground();
//            animationDrawable.setEnterFadeDuration(3000);
//        animationDrawable.setExitFadeDuration(2000);
//        animationDrawable.start();
        Name = findViewById(R.id.etuser);
        pasword = findViewById(R.id.etpassword);
        login = findViewById(R.id.textView4);
        mail = findViewById(R.id.editText3);
        Signup = findViewById(R.id.button);
        fire = FirebaseAuth.getInstance();


        progress = new ProgressDialog(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences prefer = getSharedPreferences("data", MODE_PRIVATE);
        String check = prefer.getString("login_Status", "off");
        if (check.equals("on")) {
            startActivity(new Intent(this, FourActivity.class));

        }
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String n = Name.getText().toString().trim();
                final String p = pasword.getText().toString().trim();
                final String e = mail.getText().toString().trim();
                if (TextUtils.isEmpty(n)) {
                    Name.requestFocus();
                    Toast.makeText(MainActivity.this, "Please enter the name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(e)) {
                    mail.requestFocus();
                    Toast.makeText(MainActivity.this, "Please enter the Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(p)) {
                    pasword.requestFocus();
                    Toast.makeText(MainActivity.this, "Please enter the Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pasword.length() < 6) {
                    pasword.setError("Minmum length of password should be 6");
                }


                progress.setMessage("Registering");
                progress.show();
                fire.createUserWithEmailAndPassword(e, p)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progress.cancel();
                                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                    progress.cancel();
                                    FirebaseUser user = fire.getCurrentUser();
                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                data= FirebaseDatabase.getInstance().getReference().child("Users").child(fire.getCurrentUser().getUid());
                                                Toast.makeText(MainActivity.this, "Please check your mail", Toast.LENGTH_SHORT).show();
                                                user user=new user(n,e);
                                                data.setValue(user);

                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(MainActivity.this, "error" + error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                } else {
                                    progress.cancel();
                                    Toast.makeText(MainActivity.this, "check the email and password", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

            }
        });

    }






}
