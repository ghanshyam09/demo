package com.example.demo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ThirdActivity extends AppCompatActivity {
    private EditText email;
    private Button reset;
    private FirebaseAuth fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        email=findViewById(R.id.editText);
        reset=findViewById(R.id.button3);
        fire=FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString().trim();
                if (TextUtils.isEmpty(mail)) {
                    Toast.makeText(ThirdActivity.this, "Please enter the email", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    fire.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(ThirdActivity.this, "Reset link mail is sent on your E-mail", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ThirdActivity.this, "plzz check the Email and try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
