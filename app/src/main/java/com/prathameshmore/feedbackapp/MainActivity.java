package com.prathameshmore.feedbackapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Spinner subjects;
    private DatabaseReference mDatabaseReference;
    private Button btnSumbit;
    private EditText rollNumber;
    private String rollNum;
    String selectedSubject;
    RadioButton radioButtonA;
    RadioButton radioButtonB;
    RadioButton radioButtonC;
    RadioButton radioButtonD;
    RadioButton radioButtonE;
    RadioButton radioButtonF;
    RadioButton radioButtonG;
    RadioButton radioButtonH;
    RadioGroup radioGroupA;
    RadioGroup radioGroupB;
    RadioGroup radioGroupC;
    RadioGroup radioGroupD;
    RadioGroup radioGroupE;
    RadioGroup radioGroupF;
    RadioGroup radioGroupG;
    RadioGroup radioGroupH;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSumbit = findViewById(R.id.btn_submit);
        rollNumber = findViewById(R.id.et_rollno);

        radioGroupA = findViewById(R.id.rg_a);
        radioGroupB = findViewById(R.id.rg_b);
        radioGroupC = findViewById(R.id.rg_c);
        radioGroupD = findViewById(R.id.rg_d);
        radioGroupE = findViewById(R.id.rg_e);
        radioGroupF = findViewById(R.id.rg_f);
        radioGroupG = findViewById(R.id.rg_g);
        radioGroupH = findViewById(R.id.rg_h);

        firebaseAuth = FirebaseAuth.getInstance();

        subjects = findViewById(R.id.spinner_subjects);
        List<String> subjectsList = new ArrayList<String>();
        subjectsList.add("Computer Network");
        subjectsList.add("Theory of Computational");
        subjectsList.add("Software Testing & Project Management");
        subjectsList.add("Database Management System");
        subjectsList.add("Information Systems");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,subjectsList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjects.setAdapter(arrayAdapter);


        subjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRG_A = radioGroupA.getCheckedRadioButtonId();
                int selectedRG_B = radioGroupB.getCheckedRadioButtonId();
                int selectedRG_C = radioGroupC.getCheckedRadioButtonId();
                int selectedRG_D = radioGroupD.getCheckedRadioButtonId();
                int selectedRG_E = radioGroupE.getCheckedRadioButtonId();
                int selectedRG_F = radioGroupF.getCheckedRadioButtonId();
                int selectedRG_G = radioGroupG.getCheckedRadioButtonId();
                int selectedRG_H = radioGroupH.getCheckedRadioButtonId();

                radioButtonA = findViewById(selectedRG_A);
                radioButtonB = findViewById(selectedRG_B);
                radioButtonC = findViewById(selectedRG_C);
                radioButtonD = findViewById(selectedRG_D);
                radioButtonE = findViewById(selectedRG_E);
                radioButtonF = findViewById(selectedRG_F);
                radioButtonG = findViewById(selectedRG_G);
                radioButtonH = findViewById(selectedRG_H);

                rollNum = rollNumber.getText().toString();
                if (TextUtils.isEmpty(rollNum)) {
                    rollNumber.setError("Please enter roll number");
                } else {

                    Toast.makeText(MainActivity.this, rollNum, Toast.LENGTH_SHORT).show();
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback").child(rollNum);
                    DatabaseReference subDatabaseReference = mDatabaseReference.child(selectedSubject);
                    subDatabaseReference.child("A").setValue("A + " + radioButtonA.getText());
                    subDatabaseReference.child("B").setValue("B + " + radioButtonB.getText());
                    subDatabaseReference.child("C").setValue("C + " + radioButtonB.getText());
                    subDatabaseReference.child("D").setValue("D + " + radioButtonB.getText());
                    subDatabaseReference.child("E").setValue("E + " + radioButtonB.getText());
                    subDatabaseReference.child("F").setValue("F + " + radioButtonB.getText());
                    subDatabaseReference.child("G").setValue("G + " + radioButtonB.getText());
                    subDatabaseReference.child("H").setValue("H + " + radioButtonB.getText());
                    Toast.makeText(MainActivity.this, "Feedback is updated", Toast.LENGTH_LONG).show();
                }



            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null) {
            Intent startAuthActivity = new Intent(MainActivity.this,AuthActivity.class);
            startActivity(startAuthActivity);
            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                firebaseAuth.signOut();
                startActivity(new Intent(this,AuthActivity.class));
                Toast.makeText(this, "Sign outed", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
