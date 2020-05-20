package com.example.studentschedulerjesslambert.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentschedulerjesslambert.AssessmentActivity;
import com.example.studentschedulerjesslambert.AssessmentDetailActivity;
import com.example.studentschedulerjesslambert.Entities.AssessmentEntity;
import com.example.studentschedulerjesslambert.Entities.CourseEntity;
import com.example.studentschedulerjesslambert.Entities.TermEntity;
import com.example.studentschedulerjesslambert.R;
import com.example.studentschedulerjesslambert.CourseActivity;

import com.example.studentschedulerjesslambert.ViewModel.CourseViewModel;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseView;
        private final TextView courseDateView;
        private final EditText courseName;
        private final TextView courseStart;
        private final TextView courseEnd;
        private final EditText mName;
        private final EditText phone;
        private final EditText email;
        private final EditText notes;

        private CourseViewHolder(View itemView) {


            super(itemView);
            courseView = itemView.findViewById(R.id.courseTextView);
            courseDateView = itemView.findViewById(R.id.courseDateView);
            courseName = itemView.findViewById(R.id.editCourse);
            courseStart = itemView.findViewById(R.id.courseStartSelected);
            courseEnd = itemView.findViewById(R.id.courseEndSelected);
            mName = itemView.findViewById(R.id.editMentorName);
            phone = itemView.findViewById(R.id.editPhone);
            email = itemView.findViewById(R.id.editEmail);
            notes = itemView.findViewById(R.id.editNotes);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final CourseEntity current = mCourses.get(position);
                    Intent intent = new Intent(context, AssessmentActivity.class);

                    intent.putExtra("position", position);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("courseStart", current.getCourseStart());
                    intent.putExtra("courseEnd", current.getCourseEnd());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("mentorName", current.getMentorName());
                    intent.putExtra("phone", current.getPhone());
                    intent.putExtra("email", current.getEmail());
                    intent.putExtra("notes", current.getNotes());
                    context.startActivity(intent);
                    Log.i("CHECK onClick RV ", "TermID = " + current.getTermID() + " CourseID= " + current.getCourseID());
                }
            });

        }
    }
    private final LayoutInflater mInflater;
    private final Context context;
    private List<CourseEntity> mCourses;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if (mCourses != null) {
            final CourseEntity current = mCourses.get(position);
            String startDate = current.getCourseStart();
            String endDate = current.getCourseEnd();
            holder.courseView.setText(current.getCourseName());
            holder.courseDateView.setText(startDate +  " - " + endDate);
        } else {
            holder.courseView.setText("No Items");
        }

    }

    public void setCourses(List<CourseEntity> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }
}
