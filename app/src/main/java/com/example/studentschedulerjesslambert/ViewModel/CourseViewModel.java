package com.example.studentschedulerjesslambert.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.studentschedulerjesslambert.Database.TermManagementRepository;
import com.example.studentschedulerjesslambert.Entities.CourseEntity;
import com.example.studentschedulerjesslambert.Entities.TermEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    int termID;
    private TermManagementRepository mRepository;
    private LiveData<List<CourseEntity>> mAllCourses;
    private LiveData<List<CourseEntity>> mAssociatedCourses;
    public CourseViewModel(Application application){
        super(application);
        mRepository = new TermManagementRepository(application);
        mAllCourses = mRepository.getAllCourses();
        mAssociatedCourses = mRepository.getAssociatedCourses(termID);
    }
    public LiveData<List<CourseEntity>> getAssociatedCourses(int termID){
        return mRepository.getAssociatedCourses(termID);
    }
    public LiveData<List<CourseEntity>> getAllCourses() {
        return mAllCourses;
    }
    public void insertCourse(CourseEntity courseEntity) {
        mRepository.insertCourse(courseEntity);
    }
    public void deleteCourse(CourseEntity courseEntity) {
        mRepository.deleteCourse(courseEntity);
    }
    public int lastID(){
        return mAllCourses.getValue().size();
    }

}
