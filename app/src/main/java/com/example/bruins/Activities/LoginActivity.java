package com.example.bruins.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bruins.R;
import com.example.bruins.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.example.bruins.Activities.ImagesUploadActivity.defaultPic;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;
    DatabaseReference mDatabaseRef;
    DatabaseReference rootRef;
    boolean recentlyLoggedIn = false;
    User databaseUser;
    private EditText mEmail, mPassword, mUsername, mEmailForgotPassword;
    private Button btnSignIn, btnSignOut, btnForgotPassword, btnResetPassword;
    private boolean firstStart;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if(firstStart){
            intent.putExtra("First Launch", true);
        }
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setup();

        passwordButtons();

        toolbarSetup();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    rootRef = FirebaseDatabase.getInstance().getReference("users");
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String email = mEmail.getText().toString();
                            String regx = ".";
                            char[] ca = regx.toCharArray();
                            for (char c : ca) {
                                email = email.replace(""+c, "");
                            }
                            if (!snapshot.hasChild(email)) {
                                //If it is the first time the user is logging in ever so it has no profile pic
                                Log.d("FIRSTTIMELOGIN", "TRUE");
                                databaseUser = new User(email, mUsername.getText().toString(), mPassword.getText().toString(),
                                        defaultPic);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    toastMessage("Successfully signed in with: " + user.getEmail());


                    Intent intent = new Intent(LoginActivity.this, ImagesUploadActivity.class).putExtra("Email",
                            user.getEmail());
                    recentlyLoggedIn = false;
                    startActivity(intent);


                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("You are signed out.");
                }
                //
            }
        }

        ;

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                if (!email.equals("") && !pass.equals("")) {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            recentlyLoggedIn = true;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            toastMessage(e.getMessage());
                        }
                    });

                } else {
                    toastMessage("You didn't fill in all the fields.");
                }
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                assert user == null;
                toastMessage("Signing Out...");
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setup() {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mUsername = findViewById(R.id.username);
        mEmailForgotPassword = findViewById(R.id.email_forgot_password);
        btnSignIn = findViewById(R.id.email_sign_in_button);
        btnSignOut = findViewById(R.id.email_sign_out_button);
        btnForgotPassword = findViewById(R.id.forgot_password);
        btnResetPassword = findViewById(R.id.btn_reset_password);

        mUsername.setHint("Username (what people will see you as when you post)");

        mEmailForgotPassword.setVisibility(View.GONE);

        btnResetPassword.setVisibility(View.GONE);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        firstStart = getIntent().getBooleanExtra("First Launch", false);

        if (firstStart){
            showStartDialog();
        }
    }

    public void passwordButtons() {
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmailForgotPassword.setText("");

                mEmailForgotPassword.setVisibility(View.VISIBLE);


                btnResetPassword.setVisibility(View.VISIBLE);

            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(mEmailForgotPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    toastMessage("Email sent to " + mEmailForgotPassword.getText().toString());
                                } else {
                                    toastMessage(Objects.requireNonNull(task.getException()).toString());
                                }
                            }
                        });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void toolbarSetup() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void showStartDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Login to Post")
                .setMessage("If you are a Club President and want to post updates, please contact bruinsappteam@gmail.com" +
                        " to apply for an account. In the email, show proof of your club. Then " +
                        "give your email, username, and password")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}
