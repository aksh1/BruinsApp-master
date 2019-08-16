package com.example.bruins.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bruins.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.net.URL;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePictureActivity extends AppCompatActivity {

    private String child;
    private CircleImageView profilePic;
    private Button complete;
    private Button retry;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        child = getIntent().getStringExtra("Child");
        Toast.makeText(this, child, Toast.LENGTH_SHORT).show();
        profilePic = findViewById(R.id.imageView4);
        complete = findViewById(R.id.button3);
        retry = findViewById(R.id.button2);
        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                final Uri mImageUri = result.getUri();
                final StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("profilePic");
                final StorageReference fileReference = mStorageRef.child(child);
                fileReference.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Picasso.get()
                                .load(mImageUri)
                                .placeholder(R.mipmap.ic_launcher)
                                .into(profilePic);

                        retry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(ProfilePictureActivity.this);
                            }
                        });
                        complete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                addToDB(mStorageRef.child("akvi2424gmailcom").getDownloadUrl().toString());
                                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                                        addToDB(uri.toString());
                                        startActivity(new Intent(ProfilePictureActivity.this, ImagesUploadActivity.class));
                                    }
                                });
                            }
                        });

                    }
                });
            }
        }
    }

    public void addToDB (String imageURL) {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");
        mDatabaseRef.child(child).child("profilePic").setValue(imageURL).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
