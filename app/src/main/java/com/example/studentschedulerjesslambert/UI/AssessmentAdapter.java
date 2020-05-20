package com.example.studentschedulerjesslambert.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentschedulerjesslambert.AssessmentActivity;
import com.example.studentschedulerjesslambert.AssessmentDetailActivity;
import com.example.studentschedulerjesslambert.Entities.AssessmentEntity;
import com.example.studentschedulerjesslambert.R;
import com.example.studentschedulerjesslambert.Entities.CourseEntity;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentView;
        private final TextView assessmentDateView;
        private final EditText editAssessment;
        private final TextView dueDateSelected;



        private AssessmentViewHolder(View itemView) {


            super(itemView);

            assessmentView = itemView.findViewById(R.id.assessmentTextView);
            assessmentDateView = itemView.findViewById(R.id.assessmentDateView);
            editAssessment = itemView.findViewById(R.id.editAssessment);
            dueDateSelected = itemView.findViewById(R.id.dueDateSelected);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final AssessmentEntity current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetailActivity.class);

                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("position", position);
                    intent.putExtra("dueDate", current.getDueDate());
                    intent.putExtra("courseID", current.getCourseID());

                    context.startActivity(intent);
                }
            });

        }
    }
    private final LayoutInflater mInflater;
    private final Context context;
    private List<AssessmentEntity> mAssessments;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        if(mAssessments != null) {
            final AssessmentEntity current = mAssessments.get(position);
            String dueDate = current.getDueDate();
            holder.assessmentView.setText(current.getAssessmentName());
            holder.assessmentDateView.setText(dueDate);
        } else {
            holder.assessmentView.setText("No Items");
        }

    }

    public void setAssessments(List<AssessmentEntity> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }
}
