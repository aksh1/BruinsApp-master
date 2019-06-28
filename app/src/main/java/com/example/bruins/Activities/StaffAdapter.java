package com.example.bruins.Activities;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bruins.R;
import com.example.bruins.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ExampleViewHolder> implements Filterable {
    private List<Staff> exampleList;
    private List<Staff> exampleListFull;
    private onClickListener mOnClickListener;
    private Context context;

    StaffAdapter(List<Staff> exampleList) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_list_item,
                parent, false);
        return new ExampleViewHolder(v, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Staff currentItem = exampleList.get(position);
        holder.textViewName.setText(currentItem.getName());
        holder.textViewEmail.setText(currentItem.getEmail());
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName;
        TextView textViewEmail;
        onClickListener mOnClickListener;

        @Override
        public void onClick(View view) {
            Log.d("STAFF", "onClick: " + getAdapterPosition());
            mOnClickListener.onClick(getAdapterPosition());
        }

        ExampleViewHolder(View itemView, onClickListener mOnClickListener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name_txtView);
            textViewEmail = itemView.findViewById(R.id.email_txtView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("CLICK", textViewName.getText().toString());
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",textViewEmail.getText().toString(), null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Add A Subject");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, textViewEmail.getText().toString());
                    v.getContext().startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Staff> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Staff item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface onClickListener{
        void onClick(int position);
    }
}