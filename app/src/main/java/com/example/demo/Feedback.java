package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.firebase.client.Firebase;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Feedback extends AppCompatActivity {
    private Firebase ref;
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
        ref=new Firebase("https://fir-ba791.firebaseio.com/");

    }


    public void sendtofirebase(View view) {
    EditText Name;
        //Name = findViewById(R.id.etuser);

        auth=FirebaseAuth.getInstance();
        data=FirebaseDatabase.getInstance().getReference().child("Users");
//        HashMap<String,String> user=new HashMap<>();
//        user.put("Username",Name.getText().toString());
//        data.push().setValue(r)
      //  FirebaseUser user=auth.getCurrentUser();
        String feed=feedbackToFirebase.getText().toString().trim();
        Float r=rating.getRating();



            Firebase user=ref.child("Users");

                Firebase ref_id=user.child(auth.getCurrentUser().getUid());

                Firebase ref_feed=ref_id.child("Feedback");
                ref_feed.setValue(feed);
                Firebase ref_rating= ref_id.child("Rating");
                ref_rating.setValue(r);

        }






    }

