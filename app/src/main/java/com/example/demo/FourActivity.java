package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.demo.module.Questions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.jar.Attributes;

public class FourActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
     FirebaseAuth fire;
    private Button Next;
    ImageView profilepic;
    private DatabaseReference reference,ref,img;
   private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        NavigationView navigation = findViewById(R.id.nav_view);
        View headerView= navigation.getHeaderView(0);
        text= headerView.findViewById(R.id.ettext);
        profilepic=headerView.findViewById(R.id.imageView);
        img=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("image");
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
//      // EditText Name = findViewById(R.id.etuser);

        //FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        Next=(Button)findViewById(R.id.bn);
        Next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(FourActivity.this,Quiz.class);
                startActivity(intent);
            }
        });
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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.four, menu);
//        return true;
//    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//           // return true;
//            fire.getInstance().signOut();
//           /// finish();
//            Intent intent = new Intent(FourActivity.this, SecondActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
            Intent intent = new Intent(this, FourActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Toast.makeText(FourActivity.this, "", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.Profile) {
            Intent intent=new Intent(this,Profile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.Logout) {
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
            intent.setType("application/vnd.android.package-archive");
//            String body = "your body here";
//            String sub = "your subject here";
            intent.putExtra(intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
            //intent.putExtra(intent.EXTRA_TEXT, sub);
            startActivity(Intent.createChooser(intent, "Share using"));
        } else if (id == R.id.About) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
