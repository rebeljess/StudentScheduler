package com.example.studentschedulerjesslambert;

import android.content.Intent;
import android.os.Bundle;

//import com.example.studentschedulerjesslambert.DAO.NoteDAO;
import com.example.studentschedulerjesslambert.Entities.CourseEntity;
//import com.example.studentschedulerjesslambert.Entities.NoteEntity;
import com.example.studentschedulerjesslambert.Entities.TermEntity;
//import com.example.studentschedulerjesslambert.UI.NoteAdapter;
import com.example.studentschedulerjesslambert.ViewModel.AssessmentViewModel;
import com.example.studentschedulerjesslambert.ViewModel.CourseViewModel;
//import com.example.studentschedulerjesslambert.ViewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class NoteActivity extends AppCompatActivity {


    private CourseViewModel mCourseViewModel;
    private EditText mEditNotes;
    int courseID;
    String courseName;
    int termID;
    String start;
    String end;
    String status;
    String mentor;
    String phone;
    String email;
    String notes;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_back);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditNotes = findViewById(R.id.editNotes);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        Intent intent = getIntent();

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






        Bundle extras = getIntent().getExtras();
        setTitle("Notes for " + name);
        mEditNotes.setText(noteText);


        final FloatingActionButton saveFab = findViewById(R.id.save_fab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent save = new Intent();

                String theNote = mEditNotes.getText().toString();

                save.putExtra("noteText", theNote);
                save.putExtra("courseName", name);
                save.putExtra("courseStart", start);
                save.putExtra("courseEnd", end);
                save.putExtra("status", status);
                save.putExtra("mentorName", mentor);
                save.putExtra("phone", phone);
                save.putExtra("email", email);

                save.putExtra("termID", termID);
                save.putExtra("insertCID", insertCID);

                //CourseEntity course = new CourseEntity(courseID, name, termID, start, end, status, mentor, phone, email, theNote);
                //mCourseViewModel.insertCourse(course);

                Log.i("LLLLOOOOOKKKKK!!!!!", "TermID= " + insertTID + "courseID = " +insertCID);



                setResult(RESULT_OK, save);
                finish();
            }
        });
        final FloatingActionButton deleteFab = findViewById(R.id.delete_fab);
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("noteText", noteText);
                setResult(NoteActivity.RESULT_CANCELED, returnIntent);
                finish();

            }

        });


    }
    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("noteText", notes);
        setResult(NoteActivity.RESULT_CANCELED, returnIntent);
        finish();

    }
//    @Override
//    public boolean onSupportNavigateUp(){
//        onBackPressed();
//        return true;
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String name = getIntent().getStringExtra("courseName");

        if(id == R.id.sharing) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mEditNotes.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Notes for " + name);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Notes for " + name);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "Notes for " + courseName);
            startActivity(shareIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
