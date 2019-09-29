package com.example.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.module.Questions;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quiz extends AppCompatActivity
{

    DatabaseReference reference;
    private Firebase ref;
    private FirebaseAuth auth;
    private Button b1,b2,b3,b4;
    private TextView question,quecount,counter;
   public int total=0;
    public int score=0;
     public int qcounter=4;
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

        question=(TextView)findViewById(R.id.tque);
        quecount=(TextView)findViewById(R.id.qcount);
        counter=(TextView) findViewById(R.id.timer);

        updatequestion();
        reverseTimer(30,counter);

    }
    public void updatequestion()
    {
        total++;

        if(total>4)
        {
            //tv.setText("completed");
            //total--;

                    Intent intent = new Intent(Quiz.this,Feedback.class);
                    /*intent.putExtra("Total Questions",String.valueOf(total));
                    intent.putExtra("Score",String.valueOf(score));*/
                    startActivity(intent);
                    // finish();


        }
        else
        {
            reference= FirebaseDatabase.getInstance().getReference().child("Question").child(String.valueOf(total));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final Questions questions=dataSnapshot.getValue(Questions.class);

                    question.setText(questions.getQuestion());
                    b1.setText(String.valueOf(questions.getOption1()));
                    b2.setText(String.valueOf(questions.getOption2()));
                    b3.setText(String.valueOf(questions.getOption3()));
                    b4.setText(String.valueOf(questions.getOption4()));

                    b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                    b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                    b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                    b4.setBackgroundColor(Color.parseColor("#03A9F4"));

                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b1.getText().toString().equals(String.valueOf(questions.getAnswer())))
                            {
                                Toast.makeText(Quiz.this,"Correct answer",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Quiz.this,"wrong answer",Toast.LENGTH_SHORT).show();
                               b1.setBackgroundColor(Color.RED);
                               if(b2.getText().toString().equals(String.valueOf(questions.getAnswer())))
                               {
                                   b2.setBackgroundColor(Color.GREEN);
                               }
                               else if(b3.getText().toString().equals(String.valueOf(questions.getAnswer())))
                               {
                                   b3.setBackgroundColor(Color.GREEN);
                               }
                               else if(b4.getText().toString().equals(String.valueOf(questions.getAnswer())))
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
                            if(b2.getText().toString().equals(String.valueOf(questions.getAnswer())))
                            {
                                Toast.makeText(Quiz.this,"Correct answer",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Quiz.this,"wrong answer",Toast.LENGTH_SHORT).show();
                                b2.setBackgroundColor(Color.RED);
                                if(b1.getText().toString().equals(String.valueOf(questions.getAnswer())))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }
                                else if(b3.getText().toString().equals(String.valueOf(questions.getAnswer())))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }
                                else if(b4.getText().toString().equals(String.valueOf(questions.getAnswer())))
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
                            if(b3.getText().toString().equals(String.valueOf(questions.getAnswer())))
                            {
                                Toast.makeText(Quiz.this,"Correct answer",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Quiz.this,"wrong answer",Toast.LENGTH_SHORT).show();
                                b3.setBackgroundColor(Color.RED);
                                if(b1.getText().toString().equals(String.valueOf(questions.getAnswer())))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }
                                else if(b2.getText().toString().equals(String.valueOf(questions.getAnswer())))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }
                                else if(b4.getText().toString().equals(String.valueOf(questions.getAnswer())))
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
                            if(b4.getText().toString().equals(String.valueOf(questions.getAnswer())))
                            {
                                Toast.makeText(Quiz.this,"Correct answer",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Quiz.this,"wrong answer",Toast.LENGTH_SHORT).show();
                                b4.setBackgroundColor(Color.RED);
                                if(b1.getText().toString().equals(String.valueOf(questions.getAnswer())))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }
                                else if(b2.getText().toString().equals(String.valueOf(questions.getAnswer())))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }
                                else if(b3.getText().toString().equals(String.valueOf(questions.getAnswer())))
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
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        quecount.setText("Questions: "+total+"/"+qcounter);
    }

    public void reverseTimer(int seconds ,final TextView tv ){
        new CountDownTimer(seconds* 1000+1000,1000)
        {

            public void onTick(long millisUntilFinished){
                 int seconds=(int) (millisUntilFinished/1000);
                 int minutes=seconds/60;
                 seconds=seconds % 60;
                 tv.setText(String.format("%02d",minutes)+ ":" + (String.format("%02d",seconds)));
            }

            @Override
            public void onFinish() {

                tv.setText("completed");
                /*Intent intent = new Intent(Quiz.this,Resultactivity.class);
                intent.putExtra("Total Questions",String.valueOf(total));
                intent.putExtra("Score",String.valueOf(score));
                startActivity(intent);*/
                Intent intent = new Intent(Quiz.this,Feedback.class);
                startActivity(intent);
            }
        }.start();
    }
    public void sendtofirebase(View view)
    {
        auth=FirebaseAuth.getInstance();
        Firebase ref_id=ref.child(auth.getCurrentUser().getUid());
        Firebase ref_score=ref_id.child("Score: ");
        ref_score.setValue(score);
    }

}

