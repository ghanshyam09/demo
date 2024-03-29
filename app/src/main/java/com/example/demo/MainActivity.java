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
                if (TextUtils.isEmpty(n) && TextUtils.isEmpty(e) && TextUtils.isEmpty(p) ) {
                    mail.setError("please enter the E-Mail");
                    Name.setError("please Enter the Name");
                    pasword.setError("please Enter the Password");

                    return;
                }
                if (TextUtils.isEmpty(n) ) {
                    Name.setError("please Enter the Name");
                    return;
                }
                if (TextUtils.isEmpty(e)) {
                    mail.setError("please enter the E-Mail");
                    return;
                }
                if (TextUtils.isEmpty(p)) {
                    pasword.setError("please enter the Password");
                    return;
                }
                if (pasword.length() < 6) {
                    pasword.setError("Minimum length of password should be 6");
                }

                progress.setMessage("Registering");
                progress.show();
                fire.createUserWithEmailAndPassword(e, p)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
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
                                                Name.setText("");
                                                pasword.setText("");
                                                mail.setText("");

                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(MainActivity.this, "error" + error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                } else {
                                    progress.cancel();
                                    Toast.makeText(MainActivity.this, "check the email ", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

            }
        });

    }






}
