package com.example.demo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.demo.module.Questions;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Quiz extends AppCompatActivity
{

    DatabaseReference reference;
    private Firebase ref;
    private FirebaseAuth auth;
    private Button b1,b2,b3,b4;
    private TextView question,quecount,counter;
   public int total=0;
   private ConstraintLayout layout;
    public int score=0;
     public int qcounter=10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Firebase.setAndroidContext(this);
        ref=new Firebase("https://fir-ba791.firebaseio.com/Users");

        b1=(Button)findViewById(R.id.op1);
        b2=(Button)findViewById(R.id.op2);
        b3=(Button)findViewById(R.id.op3);
        b4=(Button)findViewById(R.id.op4);
        layout=findViewById(R.id.animi);
        AnimationDrawable animation=(AnimationDrawable)layout.getBackground();
        animation.setEnterFadeDuration(2000);
        animation.setExitFadeDuration(3000);
        animation.start();
        question=(TextView)findViewById(R.id.tque);
        quecount=(TextView)findViewById(R.id.qcount);
        counter=(TextView) findViewById(R.id.timer);

        updatequestion();
        reverseTimer(30,counter);

    }
    public void updatequestion()
    {
       int random= new  Random().nextInt(9)+1;
        total++;

        if(total>10)
        {
            //tv.setText("completed");
            //total--;
              //mCountDownTimer.cancel()

                    Intent intent = new Intent(Quiz.this,Resultactivity.class);
                    //intent.putExtra("Total Questions",String.valueOf(total));
                    intent.putExtra("Score",String.valueOf(score));
                    startActivity(intent);
                    finish();

            /*Intent intent = new Intent(Quiz.this,Feedback.class);
                    intent.putExtra("Total Questions",String.valueOf(total));
                    intent.putExtra("Score",String.valueOf(score));
                    startActivity(intent);
                     finish();*/


        }
        else
        {
            reference= FirebaseDatabase.getInstance().getReference().child("Question").child(String.valueOf(random));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final Questions questions=dataSnapshot.getValue(Questions.class);

                    question.setText(questions.getQuestion());
                    b1.setText(questions.getOption1());
                    b2.setText(questions.getOption2());
                    b3.setText(questions.getOption3());
                    b4.setText(questions.getOption4());

                    b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                    b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                    b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                    b4.setBackgroundColor(Color.parseColor("#03A9F4"));

                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b1.getText().toString().equals(questions.getAnswer()))
                            {
                                //Toast.makeText(Quiz.this,"Correct answer",Toast.LENGTH_SHORT).show();
                                score++;
                                b1.setBackgroundColor(Color.GREEN);
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                     updatequestion();
                                    }
                                },500);
                            }
                            else
                            {
                                //Toast.makeText(Quiz.this,"wrong answer",Toast.LENGTH_SHORT).show();
                               b1.setBackgroundColor(Color.RED);
                               if(b2.getText().toString().equals(questions.getAnswer()))
                               {
                                   b2.setBackgroundColor(Color.GREEN);
                               }
                               else if(b3.getText().toString().equals(questions.getAnswer()))
                               {
                                   b3.setBackgroundColor(Color.GREEN);
                               }
                               else if(b4.getText().toString().equals(questions.getAnswer()))
                               {
                                   b4.setBackgroundColor(Color.GREEN);
                               }
                               Handler handler=new Handler();
                               handler.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                       updatequestion();
                                   }
                               },500);
                            }
                        }
                    });
                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b2.getText().toString().equals(questions.getAnswer()))
                            {
                                //Toast.makeText(Quiz.this,"Correct answer",Toast.LENGTH_SHORT).show();
                                score++;
                                b2.setBackgroundColor(Color.GREEN);
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updatequestion();
                                    }
                                },500);
                            }
                            else
                            {
                                //Toast.makeText(Quiz.this,"wrong answer",Toast.LENGTH_SHORT).show();
                                b2.setBackgroundColor(Color.RED);
                                if(b1.getText().toString().equals(questions.getAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }
                                else if(b3.getText().toString().equals(questions.getAnswer()))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }
                                else if(b4.getText().toString().equals(questions.getAnswer()))
                                {
                                    b4.setBackgroundColor(Color.GREEN);
                                }
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updatequestion();
                                    }
                                },500);
                            }
                        }
                    });
                    b3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b3.getText().toString().equals(questions.getAnswer()))
                            {
                                //Toast.makeText(Quiz.this,"Correct answer",Toast.LENGTH_SHORT).show();
                                score++;
                                b3.setBackgroundColor(Color.GREEN);
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updatequestion();
                                    }
                                },500);
                            }
                            else
                            {
                                //Toast.makeText(Quiz.this,"wrong answer",Toast.LENGTH_SHORT).show();
                                b3.setBackgroundColor(Color.RED);
                                if(b1.getText().toString().equals(questions.getAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }
                                else if(b2.getText().toString().equals(questions.getAnswer()))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }
                                else if(b4.getText().toString().equals(questions.getAnswer()))
                                {
                                    b4.setBackgroundColor(Color.GREEN);
                                }
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updatequestion();
                                    }
                                },500);
                            }
                        }
                    });
                    b4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b4.getText().toString().equals(questions.getAnswer()))
                            {
                                //Toast.makeText(Quiz.this,"Correct answer",Toast.LENGTH_SHORT).show();
                                score++;
                                b4.setBackgroundColor(Color.GREEN);
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updatequestion();
                                    }
                                },500);
                            }
                            else
                            {
                                //Toast.makeText(Quiz.this,"wrong answer",Toast.LENGTH_SHORT).show();
                                b4.setBackgroundColor(Color.RED);
                                if(b1.getText().toString().equals(questions.getAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }
                                else if(b2.getText().toString().equals(questions.getAnswer()))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }
                                else if(b3.getText().toString().equals(questions.getAnswer()))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }
                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updatequestion();
                                    }
                                },500);
                            }
                        }
                    })/**/;
                // total++;
                 quecount.setText("Questions: "+total+"/"+qcounter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        auth=FirebaseAuth.getInstance();
        Firebase ref_id=ref.child(auth.getCurrentUser().getUid());
        Firebase ref_score=ref_id.child("Score");
        ref_score.setValue(score);

    }

    public void reverseTimer(int seconds ,final TextView tv ) {
        final CountDownTimer mCountDownTimer=new CountDownTimer(seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText(String.format("%02d", minutes) + ":" + (String.format("%02d", seconds)));
                //finish();
            }

            @Override
            public void onFinish() {
                if(total<10) {
                    Intent intent = new Intent(Quiz.this, Resultactivity.class);
                    //intent.putExtra("Total Questions",String.valueOf(total));
                    intent.putExtra("Score", String.valueOf(score));
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
        new CountDownTimer(seconds*1000+1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (total > 10) {
                    tv.setText("completed");
                    //assist.cancel();
                    mCountDownTimer.cancel();
                    //finish();
                }
               /* else
                {
                    Intent intent = new Intent(Quiz.this, Resultactivity.class);
                    //intent.putExtra("Total Questions",String.valueOf(total));
                    intent.putExtra("Score", String.valueOf(score));
                    startActivity(intent);
                }*/
            }
        };

    }

}

