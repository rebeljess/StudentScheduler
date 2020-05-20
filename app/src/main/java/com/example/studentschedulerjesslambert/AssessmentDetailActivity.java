package com.example.studentschedulerjesslambert;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.studentschedulerjesslambert.Entities.AssessmentEntity;
import com.example.studentschedulerjesslambert.Entities.CourseEntity;
import com.example.studentschedulerjesslambert.UI.AssessmentAdapter;
import com.example.studentschedulerjesslambert.UI.CourseAdapter;
//import com.example.studentschedulerjesslambert.UI.NoteAdapter;
import com.example.studentschedulerjesslambert.ViewModel.AssessmentViewModel;
import com.example.studentschedulerjesslambert.ViewModel.CourseViewModel;
//import com.example.studentschedulerjesslambert.ViewModel.NoteViewModel;
import com.example.studentschedulerjesslambert.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AssessmentDetailActivity extends AppCompatActivity {
    private AssessmentViewModel mAssessmentViewModel;
    private CourseViewModel mCourseViewModel;
    private EditText mEditAssessment;
    private TextView mDueDateSelected;

    public static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    TextView aDueDate;
    DatePickerDialog dueDatePickerDialog;
    int dueYear;
    int dueMonth;
    int dueDay;
    Calendar dueCalendar;
    private boolean mNewAssessment;
    long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_back);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditAssessment = findViewById(R.id.editAssessment);
        mDueDateSelected = findViewById(R.id.dueDateSelected);
        Intent intent = getIntent();
        int aId = intent.getIntExtra("assessmentID", 0);

        String aName = intent.getStringExtra("assessmentName");
        String dueDate = intent.getStringExtra("dueDate");

        int insertCID = getIntent().getIntExtra("insertCID", 0);
        String name = getIntent().getStringExtra("courseName");
        int insertTID = getIntent().getIntExtra("insertTID", 0);
        String start = getIntent().getStringExtra("start");
        String end = getIntent().getStringExtra("end");
        String status = getIntent().getStringExtra("status");
        String mentor = getIntent().getStringExtra("mentor");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");
        String noteText = getIntent().getStringExtra("noteText");

        Log.i("LLLLOOOOOKKKKK!!!!! onCreate assessdetail", "TermID= " + insertTID + "courseID = " +insertCID + "noteText= " + noteText );



        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(List<AssessmentEntity> assessmentEntities) {
                assessmentAdapter.setAssessments(assessmentEntities);
            }
        });



        AssessmentEntity assessment = new AssessmentEntity(aId, aName, dueDate, insertCID);

        Bundle extras = getIntent().getExtras();
        if (aName == null) {
            setTitle("New Assessment");
            mNewAssessment = true;
        } else {
            setTitle("Edit " + aName);
            mEditAssessment.setText(aName);
            mDueDateSelected.setText(dueDate);
        }

        aDueDate = findViewById(R.id.dueDateSelected);

        aDueDate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                dueCalendar = Calendar.getInstance();
                dueYear = dueCalendar.get(Calendar.YEAR);
                dueMonth = dueCalendar.get(Calendar.MONTH);
                dueDay = dueCalendar.get(Calendar.DAY_OF_MONTH);
                dueDatePickerDialog = new DatePickerDialog(AssessmentDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int dueYear, int dueMonth, int dueDay) {
                                aDueDate.setText((dueMonth + 1) + "-" + dueDay + "-" + dueYear);
                            }
                        }, dueYear, dueMonth, dueDay);
                date = dueCalendar.getTimeInMillis();
                Intent dateIntent = new Intent();
                dateIntent.putExtra("thisDate", date);
                //startDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dueDatePickerDialog.show();

            }
        });

        FloatingActionButton save_fab = findViewById(R.id.save_fab);
        save_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent save = new Intent();
                String name = mEditAssessment.getText().toString();
                String dDate = mDueDateSelected.getText().toString();
                save.putExtra("assessmentName", name);
                save.putExtra("dueDate", dDate);
                Intent intent = getIntent();
                int aId = intent.getIntExtra("assessmentID", 0);
                if (aId == 0) {
                    aId = mAssessmentViewModel.lastID()+1;
                }

                //String notes = intent.getStringExtra("notes");
                save.putExtra("courseName", name);
                save.putExtra("courseStart", start);
                save.putExtra("courseEnd", end);
                save.putExtra("status", status);
                save.putExtra("mentorName", mentor);
                save.putExtra("phone", phone);
                save.putExtra("email", email);
                save.putExtra("noteText", noteText);
                save.putExtra("insertTID", insertTID);
                save.putExtra("insertCID", insertCID);

                AssessmentEntity assessment = new AssessmentEntity(aId, name, dDate, insertCID);
                mAssessmentViewModel.insertAssessment(assessment);

                Log.i("LLLLOOOOOKKKKK!!!!! Result", "TermID= " + insertTID + "courseID = " +insertCID + "noteText= " + noteText);



                setResult(RESULT_OK, save);
                finish();
            }
        });

        final FloatingActionButton deleteFab = findViewById(R.id.delete_fab);
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAssessmentViewModel.deleteAssessment(assessment);
                Toast.makeText(getApplicationContext(), "Assessment has been deleted", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
//    @Override
//    public boolean onSupportNavigateUp(){
//        onBackPressed();
//        return true;
//    }
    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();
//        returnIntent.putExtra("noteText", notes);
        setResult(AssessmentDetailActivity.RESULT_CANCELED, returnIntent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assessment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String theDate = mDueDateSelected.getText().toString();
        Date convertedDate = Converters.toDate(theDate);
        Calendar newCal;
        Calendar calDate = Calendar.getInstance();

        calDate = Calendar.getInstance();
        dueYear = calDate.get(Calendar.YEAR);
        dueMonth = calDate.get(Calendar.MONTH);
        dueDay = calDate.get(Calendar.DAY_OF_MONTH);

        calDate.setTime(convertedDate);

        Long newDate;
        newDate = calDate.getTimeInMillis();



        Intent getDate = getIntent();
        getDate.getLongExtra("thisDate",0);
        Log.i("LOOK HERE", "Date: " + date);
        Log.i("AND LOOK HERE, TOO!", "calDate: " + calDate);
        Log.i("ALSO LOOK HERE, TOO!", "convertedDate: " + newDate);
        int id = item.getItemId();
        if (id == R.id.notifications) {
            Intent intent=new Intent(AssessmentDetailActivity.this, MyReceiver.class);
            intent.putExtra("key", "Assessment Due " + mDueDateSelected.getText().toString());
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailActivity.this, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, newDate, sender);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


}
