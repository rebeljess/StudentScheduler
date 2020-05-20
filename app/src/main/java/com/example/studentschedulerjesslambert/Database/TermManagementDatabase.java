package com.example.studentschedulerjesslambert.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.studentschedulerjesslambert.Converters;
import com.example.studentschedulerjesslambert.DAO.AssessmentDAO;
import com.example.studentschedulerjesslambert.DAO.CourseDAO;
//import com.example.studentschedulerjesslambert.DAO.NoteDAO;
import com.example.studentschedulerjesslambert.DAO.TermDAO;
import com.example.studentschedulerjesslambert.Entities.AssessmentEntity;
import com.example.studentschedulerjesslambert.Entities.CourseEntity;
//import com.example.studentschedulerjesslambert.Entities.NoteEntity;
import com.example.studentschedulerjesslambert.Entities.TermEntity;
import java.sql.Date;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 27, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TermManagementDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
//    public abstract NoteDAO noteDAO();

    private static volatile TermManagementDatabase INSTANCE;

    static TermManagementDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TermManagementDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermManagementDatabase.class, "term_management_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
//            new PopulateDbAsync(INSTANCE).execute();
        }

    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final TermDAO mTermDAO;
        private final CourseDAO mCourseDAO;
        private final AssessmentDAO mAssessmentDAO;
//        private final NoteDAO mNoteDAO;


        PopulateDbAsync(TermManagementDatabase db) {
            mTermDAO = db.termDAO();
            mCourseDAO = db.courseDAO();
            mAssessmentDAO = db.assessmentDAO();
//            mNoteDAO = db.noteDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//           mTermDAO.deleteAllTerms();
//            mCourseDAO.deleteAllCourses();
//            mAssessmentDAO.deleteAllAssessments();

            TermEntity term = new TermEntity(1, "Term 1", "10-16-2020", "10-16-2020");
            mTermDAO.insertTerm(term);
            term = new TermEntity(2, "Term 2", "10-16-2020", "10-16-2020");
            mTermDAO.insertTerm(term);
            term = new TermEntity(3, "Term 3", "10-16-2020", "10-16-2020");
            mTermDAO.insertTerm(term);
            term = new TermEntity(4, "Term 4", "10-16-2020", "10-16-2020" );
            mTermDAO.insertTerm(term);
            term = new TermEntity(5, "Term 5", "10-16-2020", "10-16-2020");
            mTermDAO.insertTerm(term);
            term = new TermEntity(6, "Term 6", "10-16-2020", "10-16-2020");
            mTermDAO.insertTerm(term);

            CourseEntity course = new CourseEntity(1, "Course 1", 1, "01-01-2020", "01-30-2020", "In Progress", "My Mentor", "1234567890", "hi@email.com", "This is a note");
            mCourseDAO.insertCourse(course);
            course = new CourseEntity(2, "Course 2", 1, "02-01-2020", "02-29-2020", "In Progress", "My Mentor", "1234567890", "hi@email.com", "This is also a note.");
            mCourseDAO.insertCourse(course);

            AssessmentEntity assessment = new AssessmentEntity(1, "Assessment 1", "03-10-2020", 1);
            mAssessmentDAO.insertAssessment(assessment);
            assessment = new AssessmentEntity(2, "Assessment 2", "06-15-2020", 1);
            mAssessmentDAO.insertAssessment(assessment);

//            NoteEntity note = new NoteEntity(1, "this is rough");
//            mNoteDAO.insertNote(note);

            return null;
        }
    }
}
