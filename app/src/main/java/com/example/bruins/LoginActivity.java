package com.example.bruins;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;
    DatabaseReference mDatabaseRef;
    DatabaseReference rootRef;
    boolean recentlyLoggedIn = false;
    User databaseUser;
    String usernameString;
    private EditText mEmail, mPassword, mUsername, mEmailForgotPassword;
    private Button btnSignIn, btnSignOut, btnForgotPassword, btnResetPassword;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent1);
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
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(mUsername.getText().toString())) {
                            } else {
                                databaseUser = new User(mEmail.getText().toString(), mUsername.getText().toString(), mPassword.getText().toString());
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
                            toastMessage(e.getMessage().toString());
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


    /**
     * customizable toast
     *
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setup() {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mUsername = (EditText) findViewById(R.id.username);
        mEmailForgotPassword = (EditText) findViewById(R.id.email_forgot_password);
        btnSignIn = (Button) findViewById(R.id.email_sign_in_button);
        btnSignOut = (Button) findViewById(R.id.email_sign_out_button);
        btnForgotPassword = (Button) findViewById(R.id.forgot_password);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        mUsername.setHint("Username (what people will see you as when you post)");

        mEmailForgotPassword.setVisibility(View.GONE);

        btnResetPassword.setVisibility(View.GONE);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
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

    public void toolbarSetup() {
        final Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
    }
}
