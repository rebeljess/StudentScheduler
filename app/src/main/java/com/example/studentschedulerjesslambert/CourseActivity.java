package com.example.studentschedulerjesslambert;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.example.studentschedulerjesslambert.Entities.CourseEntity;
//import com.example.studentschedulerjesslambert.Entities.NoteEntity;
import com.example.studentschedulerjesslambert.Entities.TermEntity;
import com.example.studentschedulerjesslambert.UI.CourseAdapter;
import com.example.studentschedulerjesslambert.UI.TermAdapter;
import com.example.studentschedulerjesslambert.ViewModel.CourseViewModel;
//import com.example.studentschedulerjesslambert.ViewModel.NoteViewModel;
import com.example.studentschedulerjesslambert.ViewModel.TermViewModel;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    private CourseViewModel mCourseViewModel;
    private TermViewModel mTermViewModel;
//    private NoteViewModel mNoteViewModel;
    private EditText mEditName;
    private TextView mTermStartSelected;
    private TextView mTermEndSelected;
    private static int numCourses;
    public static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
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
    private boolean mNewTerm;
    int termID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LLLLOOOOOKKKKK!!!!!", "TermID= " + termID);
        setContentView(R.layout.activity_course);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_back);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditName = findViewById(R.id.editName);
        mTermStartSelected = findViewById(R.id.termStartSelected);
        mTermEndSelected = findViewById(R.id.termEndSelected);
        Intent intent = getIntent();
        String tName = intent.getStringExtra("termName");
        String tStart = intent.getStringExtra("termStart");
        String tEnd = intent.getStringExtra("termEnd");
        int tID = intent.getIntExtra("termID",0);
        int cID = intent.getIntExtra("courseID", 0);
        Log.i("CHECK onCreate", "termID= " + tID + " CourseID= " + cID);
        TermEntity term = new TermEntity(tID, tName, tStart, tEnd);
        final TermAdapter termAdapter = new TermAdapter(this);
        mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(List<TermEntity> termEntities) {
                termAdapter.setTerms(termEntities);
            }
        });


        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            setTitle("New Term");
            mNewTerm = true;

        } else {

            setTitle("Edit " + tName);
            mEditName.setText(tName);
            mTermStartSelected.setText(tStart);
            mTermEndSelected.setText(tEnd);
        }


        FloatingActionButton add_fab = findViewById(R.id.add_fab);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("CHECK onClick", "TermID= " + tID + " CourseID= " + cID);
                Intent add = new Intent(CourseActivity.this, AssessmentActivity.class);

                String name = mEditName.getText().toString();
                String start = mTermStartSelected.getText().toString();
                String end = mTermEndSelected.getText().toString();
                add.putExtra("termName", name);
                add.putExtra("termStart", start);
                add.putExtra("termEnd", end);


                termID = getIntent().getIntExtra("termID",0);
                int insertTID;
                if(termID == 0){
                    add.putExtra("insertTID", mTermViewModel.lastID() + 1);
                    insertTID = mTermViewModel.lastID() + 1;
                } else {
                    add.putExtra("insertTID", termID);
                    insertTID = termID;
                }
                //intent.putExtra("termID", tID);
//                TermEntity term = new TermEntity(insertTID, name, start, end);
//                mTermViewModel.insertTerm(term);
                Log.i("LLLLOOOOOKKKKK!!!!!", "TermID= " + termID + "insertTID = " +insertTID);

                startActivityForResult(add, NEW_COURSE_ACTIVITY_REQUEST_CODE);
            }


        });



        startDate = findViewById(R.id.termStartSelected);

        startDate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                startCalendar = Calendar.getInstance();
                startYear = startCalendar.get(Calendar.YEAR);
                startMonth = startCalendar.get(Calendar.MONTH);
                startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
                startDatePickerDialog = new DatePickerDialog(CourseActivity.this,
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


        endDate = findViewById(R.id.termEndSelected);

        endDate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                endCalendar = Calendar.getInstance();
                endYear = endCalendar.get(Calendar.YEAR);
                endMonth = endCalendar.get(Calendar.MONTH);
                endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
                endDatePickerDialog = new DatePickerDialog(CourseActivity.this,
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

        RecyclerView recyclerView = findViewById(R.id.associated_courses);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable final  List<CourseEntity> courses) {
                List<CourseEntity> filteredCourses = new ArrayList<>();
                for(CourseEntity c:courses)if(c.getTermID()==getIntent().getIntExtra("termID", 0))filteredCourses.add(c);
                adapter.setCourses(filteredCourses);
                numCourses = filteredCourses.size();

            }
        });



        final FloatingActionButton saveFab = findViewById(R.id.save_fab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent save = new Intent();
                String name = mEditName.getText().toString();
                String start = mTermStartSelected.getText().toString();
                String end = mTermEndSelected.getText().toString();
                save.putExtra("termName", name);
                save.putExtra("termStart", start);
                save.putExtra("termEnd", end);


//                if (getIntent().getStringExtra("termName") != null) {
//                    int id = getIntent().getIntExtra("termID", 0);
//                    TermEntity term = new TermEntity(id, name, start, end);
//                    mTermViewModel.insertTerm(term);
//                }
                termID = getIntent().getIntExtra("termID",0);
                int insertTID;
                if(termID == 0){
                    save.putExtra("termID", mTermViewModel.lastID() + 1);
                    insertTID = mTermViewModel.lastID() + 1;
                } else {
                    save.putExtra("termID", termID);
                    insertTID = termID;
                }
                intent.putExtra("termID", tID);
                TermEntity term = new TermEntity(insertTID, name, start, end);
                mTermViewModel.insertTerm(term);
                Log.i("LLLLOOOOOKKKKK!!!!!", "TermID= " + termID + "insertTID = " +insertTID);

                //setResult(RESULT_OK, save);
                finish();
            }
        });
        final FloatingActionButton deleteFab = findViewById(R.id.delete_fab);
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numCourses == 0) {
                    mTermViewModel.deleteTerm(term);
                    Toast.makeText(getApplicationContext(), "Term has been deleted", Toast.LENGTH_LONG).show();;
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "A Term with Courses cannot be deleted", Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {

//            Intent onReturnIntent = getIntent();
            int insertTID = data.getIntExtra("insertTID", 0);
            Log.i("ONRETURN ", "insertTID = " + insertTID);
            CourseEntity course = new CourseEntity(data.getIntExtra("insertTID", insertTID), data.getStringExtra("courseName"), data.getIntExtra("termID", 0), data.getStringExtra("courseStart"), data.getStringExtra("courseEnd"), data.getStringExtra("status"), data.getStringExtra("mentorName"), data.getStringExtra("phone"), data.getStringExtra("email"), data.getStringExtra("notes"));
            mCourseViewModel.insertCourse(course);
        }
    }

    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();
        //returnIntent.putExtra("noteText", notes);
        setResult(CourseActivity.RESULT_CANCELED, returnIntent);
        finish();

    }

//    @Override
//    public boolean onSupportNavigateUp(){
//        onBackPressed();
//        return true;
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String theDate = mTermStartSelected.getText().toString();
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

        String theEndDate = mTermEndSelected.getText().toString();
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
            Intent intent=new Intent(CourseActivity.this, MyReceiver.class);
            intent.putExtra("key", "New Term Starting " + startDate.getText().toString());
            PendingIntent sender = PendingIntent.getBroadcast(CourseActivity.this, 3, intent, 0);

            Log.i("AND LOOK HERE, TOO!", "calDate: " + calDate);
            Log.i("ALSO LOOK HERE, TOO!", "convertedDate: " + newDate);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, newDate, sender);
            return true;
        }


        if (item.getItemId() == R.id.endNotifications) {
            Intent intent=new Intent(CourseActivity.this, MyReceiver.class);
            intent.putExtra("key", "Term Ending " + endDate.getText().toString());
            PendingIntent sender = PendingIntent.getBroadcast(CourseActivity.this, 4, intent, 0);
            Log.i("AND LOOK HERE, TOO!", "calEndDate: " + calEndDate);
            Log.i("ALSO LOOK HERE, TOO!", "convertedEndDate: " + newEndDate);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, newEndDate, sender);
            return true;
        }



        return super.onOptionsItemSelected(item);

    }


}
