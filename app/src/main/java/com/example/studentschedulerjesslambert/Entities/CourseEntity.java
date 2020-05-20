package com.example.studentschedulerjesslambert.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "course_table")
public class CourseEntity {

    @PrimaryKey
    private int courseID;

    private String courseName;
    private int termID;
    private String courseStart;
    private String courseEnd;
    private String status;
    private String mentorName;
    private String phone;
    private String email;
    private String notes;

    public CourseEntity(int courseID, String courseName, int termID, String courseStart, String courseEnd, String status, String mentorName, String phone, String email, String notes) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.termID = termID;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.status = status;
        this.mentorName = mentorName;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "courseID=" + courseID +
                ", courseName='" + courseName + '\'' +
                ", termID=" + termID +
                ", courseStart=" + courseStart + '\'' +
                ", courseEnd=" + courseEnd + '\'' +
                ", status=" + '\'' +
                ", mentorName=" + + '\'' +
                ", phone=" + '\'' +
                ", email=" + '\'' +
                ", notes" + '\'' +
                '}';
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
