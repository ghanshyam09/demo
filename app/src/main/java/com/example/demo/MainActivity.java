package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    private EditText Name,mail;
    private EditText pasword;
    private TextView Info;
    private Button Signup;
    private FirebaseAuth fire;
    private ProgressDialog progress;
    //ConstraintLayout layout;
    private int cnt=5;
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
        Info = findViewById(R.id.textView4);
        mail=findViewById(R.id.editText3);
        Signup =findViewById(R.id.button);
         fire= fire.getInstance();
              progress=new ProgressDialog(this);
        //Info.setText("No. of attempts remaining 5");
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                //startActivity(intent);
              // validate(username.getText().toString(),password.getText().toString());
                String n=Name.getText().toString().trim();
                String p=pasword.getText().toString().trim();
                String e=mail.getText().toString().trim();
               // info.setText("No. of attempts remaining 5");
                if(TextUtils.isEmpty(n)){
                    Toast.makeText(MainActivity.this,"Please enter the name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(e)){
                    Toast.makeText(MainActivity.this,"Please enter the Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(p)){
                    Toast.makeText(MainActivity.this,"Please enter the Password",Toast.LENGTH_SHORT).show();
                    return;
                }


                fire.createUserWithEmailAndPassword(e, p)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                                    //startActivity(intent);
                                    //progress.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                                    FirebaseUser user=fire.getCurrentUser();
                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(MainActivity.this,"Please check your mail",Toast.LENGTH_SHORT).show();
                                            }else{
                                                String error=task.getException().getMessage();
                                                Toast.makeText(MainActivity.this,"error"+error,Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                } else {
                                    //task.getException();
                                    Toast.makeText(MainActivity.this,"check the email and password",Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
                
            }
        });
        
    }

}
