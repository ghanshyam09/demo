package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecondActivity extends AppCompatActivity {
    EditText email;
    private EditText password;
    private TextView reset;
    private Button log;
    private FirebaseAuth fire;
    private  Context ctx;
    private FirebaseAuth.AuthStateListener authStateListener;

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

        //final String name=getIntent().getStringExtra("userName");

        fire=fire.getInstance();
         progress=new ProgressDialog(this);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this,ThirdActivity.class) );
            }
        });
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                checkifLogin();
            }
        };

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mail=email.getText().toString().trim();
                String pword=password.getText().toString().trim();
                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(SecondActivity.this,"Please enter the email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pword)) {
                    Toast.makeText(SecondActivity.this, "Please enter the Password", Toast.LENGTH_SHORT).show();
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
                                            SharedPreferences prefer= getSharedPreferences("data",MODE_PRIVATE);
                                             String name=prefer.getString("data","");

                                            startActivity(new Intent(SecondActivity.this, FourActivity.class));
                                            email.setText("");
                                            password.setText("");
                                            finish();
                                           // Store();
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

                                    // ...
                                }
                            });
            }
        }
        });

    }
    @Override
    protected  void onStart(){
        super.onStart();
        fire.addAuthStateListener(authStateListener);
    }
    void checkifLogin() {
        FirebaseUser user = fire.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, FourActivity.class);
        } else {
            Toast.makeText(this, "Login to continue", Toast.LENGTH_SHORT).show();
        }
    }


//    private void Store() {
//        SharedPreferences prefer= ctx.getSharedPreferences("demo",MODE_PRIVATE);
//        SharedPreferences.Editor edit=prefer.edit();
//        edit.putString("login_Status","on");
//        edit.commit();
//    }
}
