package com.example.bruins;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.*;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;

public class ImagesUploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;



    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mUsernameRef;
    private DatabaseReference mUserRef;

    private StorageTask mUploadTask;

    Upload upload;

    MenuItem menuItem;

    FirebaseAuth mAuth;

    String email;

    String username;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menuItem = menu.findItem(R.id.action_settings);

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mUserRef = FirebaseDatabase.getInstance().getReference("users");




        mAuth = FirebaseAuth.getInstance();

        email = getIntent().getStringExtra("Email");

        String regx = "@.";
        char[] ca = regx.toCharArray();
        for (char c : ca) {
            email = email.replace(""+c, "");
        }

        mUsernameRef = mUserRef.child(email).child("username");



        mUsernameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                username = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        toastMessage(username);




        Toolbar toolbar = findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        }

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);





        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(ImagesUploadActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                return true;
        }

    }


    private void openFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//                && data != null && data.getData() != null) {
//            mImageUri = data.getData();
//
//            mImageView.setImageUriAsync(mImageUri);
//
//            mImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
//                @Override
//                public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
//
//                    mImageView.getCroppedImageAsync();
//                }
//            });
//
//
////            Picasso.get().load(mImageUri).into(mImageView);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                ((ImageView) findViewById(R.id.image_view)).setImageURI(mImageUri);
                Toast.makeText(
                        this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }


    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(mEditTextFileName.getText().toString());


            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);


                            mStorageRef.child(mEditTextFileName.getText().toString().trim()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'



                                    upload = new Upload(mEditTextFileName.getText().toString().trim(), uri.toString(), username);
                                    Log.d("URLLINK", uri.toString());
                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(upload);
                                    Toast.makeText(ImagesUploadActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                    mEditTextFileName.setText("");
                                    Intent intent = new Intent(ImagesUploadActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(ImagesUploadActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ImagesUploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openImagesActivity() {
//        Intent intent = new Intent(this, ImagesActivity.class);
//        startActivity(intent);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}