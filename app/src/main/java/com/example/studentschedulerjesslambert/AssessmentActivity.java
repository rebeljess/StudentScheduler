package com.example.studentschedulerjesslambert;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.example.studentschedulerjesslambert.Entities.AssessmentEntity;
import com.example.studentschedulerjesslambert.Entities.CourseEntity;
import com.example.studentschedulerjesslambert.UI.AssessmentAdapter;
import com.example.studentschedulerjesslambert.UI.CourseAdapter;
import com.example.studentschedulerjesslambert.ViewModel.AssessmentViewModel;
import com.example.studentschedulerjesslambert.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AssessmentActivity extends AppCompatActivity {
    private AssessmentViewModel mAssessmentViewModel;
    private CourseViewModel mCourseViewModel;
    private EditText mEditCourse;
    private TextView mCourseStartSelected;
    private TextView mCourseEndSelected;
    private EditText mEditMentorName;
    private EditText mEditPhone;
    private EditText mEditEmail;
    private EditText mEditNotes;
    private TextView mGoToNotes;

    private static int numAssessments;
    public static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    TextView startDate;
    DatePickerDialog startDatePickerDialog;
    int startYear;
    int startMonth;
    int startDay;
    Calendar startCalendar;
    TextView endDate;
    DatePickerDialog endDatePickerDialog;
    int endYear;
    int endMonth;
    int endDay;
    Calendar endCalendar;
    private boolean mNewCourse;
    Spinner statusSpinner;
    TextView goToNotes;
    int LAUNCH_NOTES = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_back);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditCourse = findViewById(R.id.editCourse);
        mCourseStartSelected = findViewById(R.id.courseStartSelected);
        mCourseEndSelected = findViewById(R.id.courseEndSelected);
        mEditMentorName = findViewById(R.id.editMentorName);
        mEditPhone = findViewById(R.id.editPhone);
        mEditEmail = findViewById(R.id.editEmail);
        mEditNotes = findViewById(R.id.editNotes);
        goToNotes = findViewById(R.id.goToNotes);
        Intent intent = getIntent();
        int cId = intent.getIntExtra("courseID", 0);
        int tId = intent.getIntExtra("insertTID", 0);
        String cName = intent.getStringExtra("courseName");
        String cStart = intent.getStringExtra("courseStart");
        String cEnd = intent.getStringExtra("courseEnd");
        String cStatus = intent.getStringExtra("status");
        String cMName = intent.getStringExtra("mentorName");
        String cPhone = intent.getStringExtra("phone");
        String cEmail = intent.getStringExtra("email");
        String cNotes = intent.getStringExtra("notes");

        Log.i("LLLLOOOOOKKKKK!!!!!  onCreate", "TermID= " + tId + "courseID = " +cId);




        final CourseAdapter courseAdapter = new CourseAdapter(this);
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courseEntities) {
                courseAdapter.setCourses(courseEntities);
            }
        });

//        int insertCID;
//        if(cId == 0){
//            insertCID = mCourseViewModel.lastID() + 1;
//        }

//        int insertCID;
//        if (cId == 0) {
//            insertCID = mCourseViewModel.lastID()+1;
//        } else {
//            insertCID = cId;
//        }
        CourseEntity course = new CourseEntity(cId, cName, tId, cStart, cEnd, cStatus, cMName, cPhone, cEmail, cNotes);

        statusSpinner = (Spinner) findViewById(R.id.editStatus);
        statusSpinner.setAdapter(new ArrayAdapter<String>(AssessmentActivity.this,android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.status_array)));
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(AssessmentActivity.this, "\n Status: \t " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Bundle extras = getIntent().getExtras();
        if(cName == null) {
            setTitle("New Course");
        } else {

            setTitle("Edit " + cName);
            mEditCourse.setText(cName);
            mCourseStartSelected.setText(cStart);
            mCourseEndSelected.setText(cEnd);
            statusSpinner.setSelection(((ArrayAdapter) statusSpinner.getAdapter()).getPosition(cStatus));
            mEditMentorName.setText(cMName);
            mEditPhone.setText(cPhone);
            mEditEmail.setText(cEmail);
            //mEditNotes.setText(cNotes);

        }
        if(cNotes != null) {
            goToNotes.setText(cNotes);
        } else {
            goToNotes.setText("Enter Notes");
        }


        startDate = findViewById(R.id.courseStartSelected);

        startDate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                startCalendar = Calendar.getInstance();
                startYear = startCalendar.get(Calendar.YEAR);
                startMonth = startCalendar.get(Calendar.MONTH);
                startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
                startDatePickerDialog = new DatePickerDialog(AssessmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int startYear, int startMonth, int startDay) {
                                startDate.setText((startMonth + 1) + "-" + startDay + "-" + startYear);
                            }
                        }, startYear, startMonth, startDay);
                //startDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                startDatePickerDialog.show();
            }
        });


        endDate = findViewById(R.id.courseEndSelected);

        endDate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                endCalendar = Calendar.getInstance();
                endYear = endCalendar.get(Calendar.YEAR);
                endMonth = endCalendar.get(Calendar.MONTH);
                endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
                endDatePickerDialog = new DatePickerDialog(AssessmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int endYear, int endMonth, int endDay) {
                                endDate.setText((endMonth + 1) + "-" + endDay + "-" + endYear);
                            }
                        }, endYear, endMonth, endDay);
                //endDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                endDatePickerDialog.show();
            }
        });

        FloatingActionButton save_fab = findViewById(R.id.save_fab);
        save_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                int cId = intent.getIntExtra("insertCID", 0);
                //int tId = intent.getIntExtra("termID", 0);

                Intent save = new Intent();

                String name = mEditCourse.getText().toString();
                String start = mCourseStartSelected.getText().toString();
                String end = mCourseEndSelected.getText().toString();
                String status = statusSpinner.getSelectedItem().toString();
                String mentor = mEditMentorName.getText().toString();
                String phone = mEditPhone.getText().toString();
                String email = mEditEmail.getText().toString();
                //String notes = goToNotes.getText().toString();
                save.putExtra("courseName", name);
                save.putExtra("courseStart", start);
                save.putExtra("courseEnd", end);
                save.putExtra("status", status);
                save.putExtra("mentorName", mentor);
                save.putExtra("phone", phone);
                save.putExtra("email", email);
                save.putExtra("noteText", cNotes);
                save.putExtra("insertTID", tId);

                int insertCID;
                if (cId == 0) {
                    save.putExtra("insertCID", mCourseViewModel.lastID()+1);
                    insertCID = mCourseViewModel.lastID() + 1;
                } else {
                    save.putExtra("insertCID", cId);
                    insertCID = cId;
                }
                CourseEntity saveCourse = new CourseEntity(insertCID, name, tId, start, end, status, mentor, phone, email, cNotes);
                mCourseViewModel.insertCourse(saveCourse);

                setResult(RESULT_OK, save);
                Toast.makeText(getApplicationContext(), "Course has been saved", Toast.LENGTH_LONG).show();


            }
        });



        final FloatingActionButton deleteFab = findViewById(R.id.delete_fab);
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numAssessments == 0) {
                    mCourseViewModel.deleteCourse(course);
                    Toast.makeText(getApplicationContext(), "Course has been deleted", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "A Course with Assessments cannot be deleted", Toast.LENGTH_LONG).show();
                }

            }

        });

        RecyclerView recyclerView = findViewById(R.id.associated_assessments);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {

            @Override
            public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                Log.i("LLLLOOOOOKKKKK!!!!! Result", "TermID= " + tId + "courseID = " +cId + "noteText= " + "noteText");

                List<AssessmentEntity> filteredAssessments = new ArrayList<>();
                for(AssessmentEntity a:assessments)if(a.getCourseID()==getIntent().getIntExtra("courseID", 0))filteredAssessments.add(a);
                adapter.setAssessments(filteredAssessments);
                numAssessments = filteredAssessments.size();
            }
        });


        goToNotes.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AssessmentActivity.this, NoteActivity.class);
//                intent.putExtra("courseName", cName);
//                intent.putExtra("notes", cNotes);

                Intent notesIntent = new Intent(AssessmentActivity.this, NoteActivity.class);

                String name = mEditCourse.getText().toString();
                String start = mCourseStartSelected.getText().toString();
                String end = mCourseEndSelected.getText().toString();
                String status = statusSpinner.getSelectedItem().toString();
                String mentor = mEditMentorName.getText().toString();
                String phone = mEditPhone.getText().toString();
                String email = mEditEmail.getText().toString();
                //String notes = goToNotes.getText().toString();
                notesIntent.putExtra("courseName", name);
                notesIntent.putExtra("courseStart", start);
                notesIntent.putExtra("courseEnd", end);
                notesIntent.putExtra("status", status);
                notesIntent.putExtra("mentorName", mentor);
                notesIntent.putExtra("phone", phone);
                notesIntent.putExtra("email", email);
                notesIntent.putExtra("noteText", cNotes);
                notesIntent.putExtra("insertTID", tId);

                int cId = notesIntent.getIntExtra("courseID", 0);

                int insertCID;
                if (cId == 0) {
                    notesIntent.putExtra("insertCID", mCourseViewModel.lastID()+1);
                    insertCID = mCourseViewModel.lastID() + 1;
                } else {
                    notesIntent.putExtra("insertCID", cId);
                    insertCID = cId;
                }
//                CourseEntity notesCourse = new CourseEntity(insertCID, name, tId, start, end, status, mentor, phone, email, cNotes);
//                mCourseViewModel.insertCourse(notesCourse);

                startActivityForResult(notesIntent, LAUNCH_NOTES);
            }
        }));

        FloatingActionButton fab = findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent add = new Intent(AssessmentActivity.this, AssessmentDetailActivity.class);

                String name = mEditCourse.getText().toString();
                String start = mCourseStartSelected.getText().toString();
                String end = mCourseEndSelected.getText().toString();
                String status = statusSpinner.getSelectedItem().toString();
                String mentor = mEditMentorName.getText().toString();
                String phone = mEditPhone.getText().toString();
                String email = mEditEmail.getText().toString();
                String noteText = goToNotes.getText().toString();
                add.putExtra("courseName", name);
                add.putExtra("courseStart", start);
                add.putExtra("courseEnd", end);
                add.putExtra("status", status);
                add.putExtra("mentorName", mentor);
                add.putExtra("phone", phone);
                add.putExtra("email", email);
                add.putExtra("noteText", noteText);
                add.putExtra("insertTID", tId);

                int cId = intent.getIntExtra("courseID", 0);
                String notes = intent.getStringExtra("notes");

                int insertCID;
                if (cId == 0) {
                    add.putExtra("insertCID", mCourseViewModel.lastID()+1);
                    insertCID = mCourseViewModel.lastID() + 1;
                } else {
                    add.putExtra("insertCID", cId);
                    insertCID = cId;
                }
//                CourseEntity addCourse = new CourseEntity(insertCID, name, tId, start, end, status, mentor, phone, email, cNotes);
//                mCourseViewModel.insertCourse(addCourse);


                startActivityForResult(add, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    //@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        adapter.notifyDataSetChanged();
        goToNotes = findViewById(R.id.goToNotes);
        Intent intent = getIntent();
        int cId = data.getIntExtra("insertCID", 0);
        int tId = data.getIntExtra("insertTID", 0);

        if (requestCode == LAUNCH_NOTES) {
            if (resultCode == RESULT_OK) {
                String noteText = data.getStringExtra("noteText");
                goToNotes.setText(noteText);

            }
            Log.i("LLLLOOOOOKKKKK!!!!! Result", "TermID= " + tId + "courseID = " +cId + "noteText= " + data.getStringExtra("noteText") );
        }

        final CourseAdapter courseAdapter = new CourseAdapter(this);
        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courseEntities) {
                courseAdapter.setCourses(courseEntities);
            }
        });

        if (requestCode == NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE) {
            if(data.getStringExtra("noteText") != null) {
                goToNotes.setText(data.getStringExtra("noteText"));
            } else {
                goToNotes.setText("Enter Notes");
            }
        }


        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {

            @Override
            public void onChanged(@Nullable final List<AssessmentEntity> assessments) {
                Log.i("LLLLOOOOOKKKKK!!!!! Result", "TermID= " + tId + "courseID = " +cId + "noteText= " + "noteText");

                List<AssessmentEntity> filteredAssessments = new ArrayList<>();
                for(AssessmentEntity a:assessments)if(a.getCourseID()==getIntent().getIntExtra("insertCID", 0))filteredAssessments.add(a);
                adapter.setAssessments(filteredAssessments);
                numAssessments = filteredAssessments.size();
            }
        });

        String noteText = data.getStringExtra("noteText");

        FloatingActionButton save_fab = findViewById(R.id.save_fab);
        save_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNotes.setText(getIntent().getStringExtra("noteText"));

                //Intent intent = getIntent();
                int cId = data.getIntExtra("insertCID", 0);
                int tId = data.getIntExtra("insertTID", 0);

                Intent save = new Intent();
                String name = mEditCourse.getText().toString();
                String startDate = mCourseStartSelected.getText().toString();
                String endDate = mCourseEndSelected.getText().toString();
                String status = statusSpinner.getSelectedItem().toString();
                String mName = mEditMentorName.getText().toString();
                String phone = mEditPhone.getText().toString();
                String email = mEditEmail.getText().toString();
                String noteText = data.getStringExtra("noteText");
                save.putExtra("courseName", name);
                save.putExtra("courseStart", startDate);
                save.putExtra("courseEnd", endDate);
                save.putExtra("status", status);
                save.putExtra("mentorName", mName);
                save.putExtra("phone", phone);
                save.putExtra("email", email);
                save.putExtra("noteText", noteText);
                save.putExtra("insertTID", tId);
                save.putExtra("insertCID", cId);

//                int insertCID;
//                if (cId == 0) {
//                    save.putExtra("insertCID", mCourseViewModel.lastID()+1);
//                    insertCID = mCourseViewModel.lastID() + 1;
//                } else {
//                    save.putExtra("insertCID", cId);
//                    insertCID = cId;
//                }

                CourseEntity saveCourse = new CourseEntity(cId, name, tId, startDate, endDate, status, mName, phone, email, noteText);
                mCourseViewModel.insertCourse(saveCourse);

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
        setResult(AssessmentActivity.RESULT_CANCELED, returnIntent);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String theDate = mCourseStartSelected.getText().toString();
        Date convertedDate = Converters.toDate(theDate);
        Calendar newCal;
        Calendar calDate = Calendar.getInstance();

        calDate = Calendar.getInstance();
        startYear = calDate.get(Calendar.YEAR);
        startMonth = calDate.get(Calendar.MONTH);
        startDay = calDate.get(Calendar.DAY_OF_MONTH);

        calDate.setTime(convertedDate);

        Long newDate;
        newDate = calDate.getTimeInMillis();

        String theEndDate = mCourseEndSelected.getText().toString();
        Date convertedEndDate = Converters.toDate(theEndDate);
        Calendar newEndCal;
        Calendar calEndDate = Calendar.getInstance();

        calEndDate = Calendar.getInstance();
        endYear = calEndDate.get(Calendar.YEAR);
        endMonth = calEndDate.get(Calendar.MONTH);
        endDay = calEndDate.get(Calendar.DAY_OF_MONTH);

        calEndDate.setTime(convertedEndDate);

        Long newEndDate;
        newEndDate = calEndDate.getTimeInMillis();




        if (item.getItemId() == R.id.startNotifications) {
            Intent intent=new Intent(AssessmentActivity.this, MyReceiver.class);
            intent.putExtra("key", "New Course Starting " + startDate.getText().toString());
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentActivity.this, 1, intent, 0);

            Log.i("AND LOOK HERE, TOO!", "calDate: " + calDate);
            Log.i("ALSO LOOK HERE, TOO!", "convertedDate: " + newDate);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, newDate, sender);
            return true;
        }


        if (item.getItemId() == R.id.endNotifications) {
            Intent intent=new Intent(AssessmentActivity.this, MyReceiver.class);
            intent.putExtra("key", "Course Ending " + endDate.getText().toString());
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentActivity.this, 2, intent, 0);
            Log.i("AND LOOK HERE, TOO!", "calEndDate: " + calEndDate);
            Log.i("ALSO LOOK HERE, TOO!", "convertedEndDate: " + newEndDate);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, newEndDate, sender);
            return true;
        }



        return super.onOptionsItemSelected(item);

    }



}
