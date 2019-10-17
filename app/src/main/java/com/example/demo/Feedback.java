package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Feedback extends AppCompatActivity {
    private Firebase ref;
    private EditText Name,mail;
    private RatingBar rating;
    private EditText feedbackToFirebase;
     private Button send;
    private FirebaseAuth auth;
    private DatabaseReference data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        rating=findViewById(R.id.ratingBar);
        feedbackToFirebase=findViewById(R.id.et2);
         send=findViewById(R.id.btn_send);
        Firebase.setAndroidContext(this);

    }

    public void sendtofirebase(View view) {


        auth=FirebaseAuth.getInstance();
       data= FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());
        String feed=feedbackToFirebase.getText().toString().trim();
        Float r=rating.getRating();
        data.child("Feedback").setValue(feed);
        data.child("Rating").setValue(r);


        Intent intent=new Intent(Feedback.this,FourActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setMessage("Please give feedback");
        alert.setCancelable(true);
        AlertDialog alertDialog=alert.create();
        alertDialog.show();
    }



}

