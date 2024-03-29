package com.example.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class Profile extends AppCompatActivity {
    TextView text1,text2;
    ImageView image;
    Button upload;
    private ProgressDialog progress;
    private Uri resultUri;
    DatabaseReference ref,ref2,img;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    private StorageReference mstorage=storage.getReference("uploads");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        text1=findViewById(R.id.textView13);
        text2=findViewById(R.id.textView16);
        image=findViewById(R.id.imageView2);
        upload=findViewById(R.id.button4);
        img= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("image");

        img.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uri=dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext()).load(uri).placeholder(R.drawable.icon).into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref2= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String email=dataSnapshot.child("email").getValue().toString();
                    String name=dataSnapshot.child("username").getValue().toString();
                    text1.setText(name);
                    text2.setText(email);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(Profile.this);
            }
        });


    }
    private  void uploadimage(){
        if(resultUri!=null)
        {
            final  ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("uploading...");
                progressDialog.show();
            final StorageReference file=mstorage.child( FirebaseAuth.getInstance().getCurrentUser().getUid());
            file.putFile(resultUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(Profile.this,"Uploaded",Toast.LENGTH_SHORT).show();

                            file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    ref2.child("image").setValue(String.valueOf(uri));
                                    Toast.makeText(Profile.this,"Stored",Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Profile.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    });


        }
    }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 resultUri = result.getUri();
                try {
                     Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                     image.setImageBitmap(bitmap);
                    uploadimage();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                FirebaseStorage.getInstance().getReference().putFile(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        img = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("image");
        img.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uri = dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext()).load(uri).into(image);
                Intent intent = new Intent(Profile.this, FourActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        finish();
    }
}
