package com.example.studentschedulerjesslambert.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.studentschedulerjesslambert.Database.TermManagementRepository;
import com.example.studentschedulerjesslambert.Entities.AssessmentEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    int courseID;
    private TermManagementRepository mRepository;
    private LiveData<List<AssessmentEntity>> mAllAssessments;
    private LiveData<List<AssessmentEntity>> mAssociatedAssessments;
    public AssessmentViewModel(Application application){
        super(application);
        mRepository = new TermManagementRepository(application);
        mAllAssessments = mRepository.getAllAssessments();
        mAssociatedAssessments = mRepository.getAssociatedAssessments(courseID);
    }

    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return mAllAssessments;
    }
    public LiveData<List<AssessmentEntity>> getAssociatedAssessments() {
        return mRepository.getAssociatedAssessments(courseID);
    }
    public void insertAssessment(AssessmentEntity assessmentEntity) {
        mRepository.insertAssessment(assessmentEntity);
    }
    public void deleteAssessment(AssessmentEntity assessmentEntity) {
        mRepository.deleteAssessment(assessmentEntity);
    }
    public int lastID(){
        return mAllAssessments.getValue().size();
    }
}
