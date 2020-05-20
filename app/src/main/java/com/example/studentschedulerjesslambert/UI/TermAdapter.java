package com.example.studentschedulerjesslambert.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentschedulerjesslambert.Converters;
import com.example.studentschedulerjesslambert.Entities.TermEntity;
import com.example.studentschedulerjesslambert.R;
import com.example.studentschedulerjesslambert.CourseActivity;

import java.util.List;

public class TermAdapter extends  RecyclerView.Adapter<TermAdapter.TermViewHolder>{

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termView;
        private final TextView termDateView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termView = itemView.findViewById(R.id.termTextView);
            termDateView = itemView.findViewById(R.id.termDateView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final TermEntity current = mTerms.get(position);
                    Intent intent = new Intent(context, CourseActivity.class);
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("position", position);
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("termStart", current.getTermStart());
                    intent.putExtra("termEnd", current.getTermEnd());
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<TermEntity>mTerms;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {

        if (mTerms != null) {
            final TermEntity current = mTerms.get(position);
            String startDate = current.getTermStart();
            String endDate = current.getTermEnd();
            holder.termView.setText(current.getTermName());
            holder.termDateView.setText(startDate +  " - " + endDate);
        } else {
            holder.termView.setText("No Word");
        }
    }

    public void setTerms(List<TermEntity> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }
}
