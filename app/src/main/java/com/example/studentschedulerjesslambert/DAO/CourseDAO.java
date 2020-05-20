package com.example.studentschedulerjesslambert.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.studentschedulerjesslambert.Entities.CourseEntity;
import com.example.studentschedulerjesslambert.Entities.TermEntity;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity courseEntity);

    @Delete
    void deleteCourse(CourseEntity courseEntity);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table ORDER BY courseID ASC")
    LiveData<List<CourseEntity>> getAllCourses();

    @Query("SELECT * FROM  course_table WHERE termID= :tID ORDER BY courseID ASC")
    LiveData<List<CourseEntity>> getAllAssociatedCourses(int tID);
}
