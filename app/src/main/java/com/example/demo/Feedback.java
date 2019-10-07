package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

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
        //ref=new Firebase("https://fir-ba791.firebaseio.com/");



    }


    public void sendtofirebase(View view) {


        auth=FirebaseAuth.getInstance();
       data= FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());
      //  FirebaseUser user=auth.getCurrentUser();
        String feed=feedbackToFirebase.getText().toString().trim();
        Float r=rating.getRating();
        data.child("Feedback").setValue(feed);
        data.child("Rating").setValue(r);

//

//            Firebase user=ref.child("Users");
//
//                Firebase ref_id=user.child(auth.getCurrentUser().getUid());
//
//                Firebase ref_feed=ref_id.child("Feedback");
//                ref_feed.setValue(feed);
//                Firebase ref_rating= ref_id.child("Rating");
//                ref_rating.setValue(r);
//                user usr =new user(name,email);
//                Firebase ref_name=ref_id.child("username");
//                ref_name.setValue(name);
//                Firebase ref_email=ref_id.child("email_id");
//                ref_email.setValue(email);

        Intent intent=new Intent(Feedback.this,FourActivity.class);
        startActivity(intent);

        finish();
    }






    }

