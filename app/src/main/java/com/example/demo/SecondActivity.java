package com.example.demo;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;

public class SecondActivity extends AppCompatActivity {
    EditText email;
    private EditText password;
    private TextView reset;
    private Button log;
    private FirebaseAuth fire;


    private ProgressDialog progress;
    public DatabaseReference data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        email=findViewById(R.id.username);
        password=findViewById(R.id.password);
        log=findViewById(R.id.button2);
        reset=findViewById(R.id.textView7);


        fire=fire.getInstance();
         progress=new ProgressDialog(this);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this,ThirdActivity.class) );
            }
        });


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=email.getText().toString().trim();
                String pword=password.getText().toString().trim();
                if(TextUtils.isEmpty(mail)&&TextUtils.isEmpty(pword))
                {
                    email.setError("please enter your E-Mail");
                    password.setError("please enter your password");

                    return;
                }
                if(TextUtils.isEmpty(mail)){
                    email.setError("please enter your E-Mail");
                    return;
                }
                if(TextUtils.isEmpty(pword)) {
                    password.setError("please enter your password");
                    return;
                }

                else{
                    progress.setMessage("Logging in");
                    progress.show();
                    fire.signInWithEmailAndPassword(mail, pword)
                            .addOnCompleteListener(SecondActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progress.cancel();
                                        if(fire.getCurrentUser().isEmailVerified()) {
                                            SharedPreferences prefer= getApplicationContext().getSharedPreferences("data",Context.MODE_PRIVATE);
                                            SharedPreferences.Editor edit=prefer.edit();
                                            edit.putString("login_Status","on");
                                            edit.commit();
                                            startActivity(new Intent(SecondActivity.this, FourActivity.class));
                                            email.setText("");
                                            password.setText("");
                                            finish();
                                            Toast.makeText(SecondActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                        }
                                        else{
                                            progress.cancel();
                                            Toast.makeText(SecondActivity.this, "Please verify the Email address", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progress.cancel();
                                        Toast.makeText(SecondActivity.this,"Please check your email and password ",Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
            }
        }
        });

    }

}
