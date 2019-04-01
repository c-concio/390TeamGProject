package com.example.a390project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Todo: input set firebase

public class TaskPackagingActivity extends AppCompatActivity {

    // activity widgets
    TextView descriptionTextView;
    EditText employeeCommentEditText;
    Button startTimeButton;
    Button endTimeButton;
    Button completeButton;
    Button postCommentButton;
    FirebaseHelper firebaseHelper;
    TextView mTimeElapsed;
    private boolean backPressed = false;

    String packagingTaskID;

    //check if user is manager from sharedpreferences
    private boolean isManager;

    private static final String TAG = "TaskPackagingActivity";


    // ------------------------- temporary ----------------------------
    Button addMaterialButton;
    EditText materialEditText;
    // ------------------------- temporary ----------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_packaging);

        checkIfManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setActionBar("Packaging");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setupUI();
        firebaseHelper.setTaskPackagingActivityListener(packagingTaskID, this);
        firebaseHelper.setStartTimeEndTimeButtons(startTimeButton,endTimeButton,packagingTaskID);
        firebaseHelper.getEmployeeComments(this, packagingTaskID);

    }

    private void setupUI(){
        // find the ids of the widgets
        descriptionTextView = findViewById(R.id.descriptionTextView);
        employeeCommentEditText = findViewById(R.id.employeeCommentEditText);
        startTimeButton = findViewById(R.id.startTimeButton);
        endTimeButton = findViewById(R.id.endTimeButton);
        completeButton = findViewById(R.id.completed_packaging_task);
        postCommentButton = findViewById(R.id.postCommentButton);
        mTimeElapsed = findViewById(R.id.elapsed_time_since_pressed_start_time_packaging);
        mTimeElapsed.setVisibility(View.GONE);

        startTimeButton.setOnClickListener(startTimeOnClickListener);
        endTimeButton.setOnClickListener(endTimeOnClickListener);
        if (isManager)
            completeButton.setOnClickListener(completeOnClickListener);
        else
            completeButton.setVisibility(View.GONE);
        postCommentButton.setOnClickListener(postCommentOnClickListener);

        // ------------------------- temporary ----------------------------
        materialEditText = findViewById(R.id.materialEditText);
        addMaterialButton = findViewById(R.id.addMaterialButton);
        addMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (materialEditText.getText().toString().isEmpty()){
                    materialEditText.setError("Please enter a material");
                }
                else{
                    String material = materialEditText.getText().toString();
                    DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("tasks").child(packagingTaskID);
                    taskRef.child("materialUsed").child(material).setValue(true);
                    materialEditText.getText().clear();
                    Toast.makeText(TaskPackagingActivity.this, "Material added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // ------------------------- temporary ----------------------------


        // setup the firebaseHelper
        firebaseHelper = new FirebaseHelper();
        Intent intent = getIntent();
        packagingTaskID = intent.getStringExtra("packagingTaskID");

        //check if workblock already running for this task and display the timer
        firebaseHelper.checkIfTaskStartedAlready(packagingTaskID, mTimeElapsed, TaskPackagingActivity.this, backPressed, null, "Packaging");
    }


    // onClickListener for the startTime button
    View.OnClickListener startTimeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            firebaseHelper.checkIfCanStart(packagingTaskID, getApplicationContext(), "Packaging", TaskPackagingActivity.this,startTimeButton,endTimeButton, mTimeElapsed, backPressed, null);
        }
    };

    // onClickListener for the endTime button
    View.OnClickListener endTimeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            firebaseHelper.checkIfCanEnd(packagingTaskID, getApplicationContext(), mTimeElapsed, null);
        }
    };

    /*
        -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    */

    // onClickListener for the complete button
    View.OnClickListener completeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            firebaseHelper.completeTask(packagingTaskID);
        }
    };

    View.OnClickListener postCommentOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String commentString = employeeCommentEditText.getText().toString();
            if (commentString.isEmpty()){
                employeeCommentEditText.setError("Field is empty");
            }
            else{
                firebaseHelper.postComment(packagingTaskID, commentString);
                employeeCommentEditText.getText().clear();
                Toast.makeText(TaskPackagingActivity.this, "Comment posted", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        firebaseHelper.detachTaskPackagingActivityListener(packagingTaskID);
    }

    //custom heading and back button
    public void setActionBar(String heading) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(heading);
        actionBar.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                mTimeElapsed.setVisibility(View.GONE);
                backPressed = true;
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkIfManager() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String manager = preferences.getString("isManager",null);
        if (manager.equals("true")) {
            isManager = true;
        }
        else {
            isManager = false;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mTimeElapsed.setVisibility(View.GONE);
        backPressed = true;
    }
}
