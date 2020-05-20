package com.example.studentschedulerjesslambert.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.studentschedulerjesslambert.Database.TermManagementRepository;
import com.example.studentschedulerjesslambert.Entities.TermEntity;

import java.util.List;
import java.util.concurrent.Executor;

public class TermViewModel extends AndroidViewModel {
    private TermManagementRepository mRepository;
    private LiveData<List<TermEntity>> mAllTerms;

    public TermViewModel(Application application){
        super(application);
        mRepository = new TermManagementRepository(application);
        mAllTerms = mRepository.getAllTerms();
    }
    public LiveData<List<TermEntity>> getAllTerms() {return mAllTerms;}
    public void insertTerm(TermEntity termEntity) {mRepository.insertTerm(termEntity);}
    public void deleteTerm(TermEntity termEntity) {
        mRepository.deleteTerm(termEntity);
    }

    public int lastID() {return mAllTerms.getValue().size();}

    public LiveData<TermEntity> getLastTerm(){
        return mRepository.getLastTerm();
    }
}
