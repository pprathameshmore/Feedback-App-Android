package com.prathameshmore.feedbackapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TeacherAuth extends AppCompatActivity {

    Button signInBtn;
    EditText email, password;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_auth);

        firebaseAuth = FirebaseAuth.getInstance();

        signInBtn = (Button) findViewById(R.id.BtnlogIn);
        email = (EditText) findViewById(R.id.edtEmail);
        password = (EditText) findViewById(R.id.edtPassword);

        progressDialog = new ProgressDialog(this);

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(TeacherAuth.this, TeacherAuth.class));
        }

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                if (TextUtils.isEmpty(emailString)) {
                    Toast.makeText(TeacherAuth.this, "Enter email address !", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordString)) {
                    Toast.makeText(TeacherAuth.this, "Enter password !", Toast.LENGTH_LONG).show();
                    return;
                }
                if (passwordString.length() < 6) {
                    Toast.makeText(TeacherAuth.this, "Password must 6 character long !", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    progressDialog.setMessage("Logging In...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);

                    firebaseAuth.signInWithEmailAndPassword(emailString, passwordString)
                            .addOnCompleteListener(TeacherAuth.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(TeacherAuth.this, "Authentication failed, Check email and password", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(TeacherAuth.this, "Authentication Successful", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(TeacherAuth.this, TeacherFeedback.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                }
            }
        });
    }

}
