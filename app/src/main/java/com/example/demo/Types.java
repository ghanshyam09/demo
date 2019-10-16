package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Types extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);

         GridLayout gridL1;

        gridL1=(GridLayout)findViewById(R.id.gridl1);

        nextTypes(gridL1);
    }

    private void nextTypes(GridLayout gridL1)
    {
        for(int i=0;i<gridL1.getChildCount();i++)
        {

            CardView cardView=(CardView)gridL1.getChildAt(i);
            final int j=i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(j==0)
                    {
                        Intent intent=new Intent(Types.this,Quiz.class);
                        intent.putExtra("sub","Maths");
                        startActivity(intent);
                    }
                    else if(j==1)
                    {
                        Intent intent=new Intent(Types.this,Quiz.class);
                        intent.putExtra("sub","English");

                        startActivity(intent);
                    }
                    else if(j==2)
                    {
                        Intent intent=new Intent(Types.this,Quiz.class);
                        intent.putExtra("sub","GK");

                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent=new Intent(Types.this,Quiz.class);
                        intent.putExtra("sub","Technical");
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
