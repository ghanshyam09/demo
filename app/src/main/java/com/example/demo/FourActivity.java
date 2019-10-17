package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class FourActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
     FirebaseAuth fire;
    private DatabaseReference reference;
   private TextView text;
   GridLayout grid;
    ImageView profilepic;
DatabaseReference ref,img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
         NavigationView navigation = findViewById(R.id.nav_view);
        View headerView= navigation.getHeaderView(0);
        text= headerView.findViewById(R.id.ettext);
        profilepic=headerView.findViewById(R.id.imageView);
        img= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("image");
        img.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uri=dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext()).load(uri).into(profilepic);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("username");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String  name1=dataSnapshot.getValue().toString();
                text.setText(name1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        grid=(GridLayout)findViewById(R.id.gridl);
        nextActivity(grid);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
     private void nextActivity(GridLayout grid)
     {
         for(int i=0;i<grid.getChildCount();i++)
         {
             CardView cardView=(CardView)grid.getChildAt(i);
            final int j=i;
             cardView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(j==0)
                     {
                         Intent intent=new Intent(FourActivity.this,Types.class);
                         startActivity(intent);
                     }
                     else
                     {
                         Intent intent=new Intent(FourActivity.this,Quiz.class);
                          intent.putExtra("sub","Quiz");
                         startActivity(intent);
                     }
                 }
             });

         }
     }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(this, FourActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }else if(id== R.id.Profile)
        {
            Intent intent=new Intent(FourActivity.this,Profile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if (id == R.id.Logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            SharedPreferences prefer= getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit=prefer.edit();
            edit.putString("login_Status","off");
            edit.commit();

        } else if (id == R.id.settings) {

        } else if (id == R.id.nav_share) {
            ApplicationInfo apk=getApplicationContext().getApplicationInfo();
            String apkpath=apk.sourceDir;
            Intent intent = new Intent((Intent.ACTION_SEND));
            startActivity(Intent.createChooser(intent, "Share using"));

            intent.setType("application/vnd.android.package-archive");
//            String body = "your body here";
//            String sub = "your subject here";
            intent.putExtra(intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
            //intent.putExtra(intent.EXTRA_TEXT, sub);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
