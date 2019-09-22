package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class SecondActivity extends AppCompatActivity {
    EditText email;
    private EditText password;
    private TextView reset;
    private Button log;
    private FirebaseAuth fire;
    private ProgressDialog progress;
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
                                            startActivity(new Intent(SecondActivity.this, FourActivity.class));
                                            Toast.makeText(SecondActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            progress.cancel();
                                            Toast.makeText(SecondActivity.this, "Pleas verify the Email address", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        progress.cancel();
                                        Toast.makeText(SecondActivity.this,"login Failed",Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
            }
        }
        });
    }
}
