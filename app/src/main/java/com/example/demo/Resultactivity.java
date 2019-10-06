package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Resultactivity extends AppCompatActivity {

    private TextView v_tq,v_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultactivity);
       // v_tq=(TextView)findViewById(R.id.vtotalque);
        v_score=(TextView)findViewById(R.id.vscore);

        Intent i=getIntent();

       // String questions=i.getStringExtra("Total Questions");
        String score=i.getStringExtra("Score");

      //  v_tq.setText(questions);
        v_score.setText(score);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Resultactivity.this,Feedback.class);
                startActivity(intent);
                finish();
            }
        },2000);

    }
}
