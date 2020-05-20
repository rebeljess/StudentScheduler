package com.example.studentschedulerjesslambert.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.studentschedulerjesslambert.DAO.AssessmentDAO;
import com.example.studentschedulerjesslambert.DAO.CourseDAO;
//import com.example.studentschedulerjesslambert.DAO.NoteDAO;
import com.example.studentschedulerjesslambert.DAO.TermDAO;
import com.example.studentschedulerjesslambert.Entities.AssessmentEntity;
import com.example.studentschedulerjesslambert.Entities.CourseEntity;
//import com.example.studentschedulerjesslambert.Entities.NoteEntity;
import com.example.studentschedulerjesslambert.Entities.TermEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermManagementRepository {

    private static TermManagementRepository myInstance;

    private TermManagementDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
//    private NoteDAO mNoteDAO;
    private LiveData<List<TermEntity>> mAllTerms;
    private LiveData<List<CourseEntity>> mAllCourses;
//    private LiveData<List<NoteEntity>> mAllNotes;
    private LiveData<List<AssessmentEntity>> mAllAssessments;
    private LiveData<List<CourseEntity>> mAssociatedCourses;
    private LiveData<List<AssessmentEntity>> mAssociatedAssessments;
//    private LiveData<List<NoteEntity>> mAssociatedNotes;
    private int termID;
    private int courseID;

    public TermManagementRepository(Application application) {
        TermManagementDatabase db = TermManagementDatabase.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
//        mNoteDAO = db.noteDAO();
        mAllTerms = mTermDAO.getAllTerms();
        mAllCourses = mCourseDAO.getAllCourses();
        mAllAssessments = mAssessmentDAO.getAllAssessments();
//        mAllNotes = mNoteDAO.getAllNotes();
        mAssociatedCourses = mCourseDAO.getAllAssociatedCourses(termID);
        mAssociatedAssessments = mAssessmentDAO.getAllAssociatedAssessments(courseID);
//        mAssociatedNotes = mNoteDAO.getAllAssociatedNotes(courseID);
    }

        public LiveData<List<TermEntity>> getAllTerms() {return mAllTerms;}
        public LiveData<List<CourseEntity>> getAllCourses() {
            return mAllCourses;
        }
        public LiveData<List<AssessmentEntity>> getAllAssessments() {
            return mAllAssessments;
        }
        public LiveData<List<CourseEntity>> getAssociatedCourses(int termID) {
            return mAssociatedCourses;
        }
//        public LiveData<List<NoteEntity>> getAllNotes() {return mAllNotes;}
        public LiveData<List<AssessmentEntity>> getAssociatedAssessments(int courseID) {
            return mAssociatedAssessments;
        }
        public LiveData<TermEntity> getLastTerm(){
            return mTermDAO.getLastTerm();
        }

        public void insertAssessment (AssessmentEntity assessmentEntity) {
            new insertAsyncTask1(mAssessmentDAO).execute(assessmentEntity);
        }

        private static class insertAsyncTask1 extends AsyncTask<AssessmentEntity, Void, Void> {
            private AssessmentDAO mAsyncTaskDao;

            insertAsyncTask1(AssessmentDAO dao) {mAsyncTaskDao = dao;}

            @Override
            protected Void doInBackground(final AssessmentEntity... params) {
                mAsyncTaskDao.insertAssessment(params[0]);
                return null;
            }
        }
        public void insertCourse (CourseEntity courseEntity) {
            new insertAsyncTask2(mCourseDAO).execute(courseEntity);
        }

        private static class insertAsyncTask2 extends AsyncTask<CourseEntity, Void, Void> {
            private CourseDAO mAsyncTaskDao;

            insertAsyncTask2(CourseDAO dao) {mAsyncTaskDao = dao;}

            @Override
            protected Void doInBackground(final CourseEntity... params) {
                mAsyncTaskDao.insertCourse(params[0]);
                return null;
            }
        }

        public void insertTerm (TermEntity termEntity) {
            new insertAsyncTask3(mTermDAO).execute(termEntity);
        }

        private static class insertAsyncTask3 extends AsyncTask<TermEntity, Void, Void> {

            private TermDAO mAsyncTaskDao;

            insertAsyncTask3(TermDAO dao) {mAsyncTaskDao = dao;}

            @Override
            protected Void doInBackground(final TermEntity... params) {
                mAsyncTaskDao.insertTerm(params[0]);
                return null;
            }
        }

//        public void insertNote (NoteEntity noteEntity) {
//            new insertAsyncTask4(mNoteDAO).execute(noteEntity);
//        }
//
//        private static class insertAsyncTask4 extends AsyncTask<NoteEntity, Void, Void> {
//            private NoteDAO mAsyncTaskDao;
//            insertAsyncTask4(NoteDAO dao) {mAsyncTaskDao = dao;}
//
//            @Override
//            protected Void doInBackground(final NoteEntity... params) {
//                mAsyncTaskDao.insertNote(params[0]);
//                return null;
//            }
//        }



        public void deleteTerm (TermEntity termEntity) {
            new deleteAsyncTask(mTermDAO).execute(termEntity);
        }



        private static class deleteAsyncTask extends AsyncTask<TermEntity, Void, Void> {
            private TermDAO mAsyncTaskDao;
            deleteAsyncTask(TermDAO dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final TermEntity... params) {
                mAsyncTaskDao.deleteTerm(params[0]);
                return null;
            }
        }

        public void deleteCourse (CourseEntity courseEntity) {
            new deleteAsyncTask2(mCourseDAO).execute(courseEntity);
        }


        private static class deleteAsyncTask2 extends AsyncTask<CourseEntity, Void, Void> {
            private CourseDAO mAsyncTaskDao;
            deleteAsyncTask2(CourseDAO dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final CourseEntity... params) {
                mAsyncTaskDao.deleteCourse(params[0]);
                return null;
            }
        }

        public void deleteAssessment (AssessmentEntity assessmentEntity) {
            new deleteAsyncTask3(mAssessmentDAO).execute(assessmentEntity);
        }

        private static class deleteAsyncTask3 extends AsyncTask<AssessmentEntity, Void, Void> {
            private AssessmentDAO mAsyncTaskDao;
            deleteAsyncTask3(AssessmentDAO dao) {
                mAsyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final AssessmentEntity... params) {
                mAsyncTaskDao.deleteAssessment(params[0]);
                return null;
            }
        }

//        public void deleteNote (NoteEntity noteEntity) {
//            new deleteAsyncTask4(mNoteDAO).execute(noteEntity);
//        }
//
//        private static class deleteAsyncTask4 extends AsyncTask<NoteEntity, Void, Void> {
//            private NoteDAO mAsyncTaskDao;
//            deleteAsyncTask4(NoteDAO dao) {
//                mAsyncTaskDao = dao;
//            }
//
//            @Override
//            protected Void doInBackground(final NoteEntity... params) {
//                mAsyncTaskDao.deleteNote(params[0]);
//                return null;
//            }
//        }

}


