package com.example.bruins;

import android.content.Context;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.Comparator;
import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private StaffAdapter mStaffAdapter;
    private List<Staff> staff;



    public void setConfig(RecyclerView recyclerView, Context context, List<Staff> staff, List<String> keys) {

        mContext = context;
        mStaffAdapter = new StaffAdapter(staff, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mStaffAdapter);
        this.staff = staff;

    }



      class StaffItemView extends RecyclerView.ViewHolder {


        private TextView mName;
        private TextView mEmail;

        private String key;

        public StaffItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.staff_list_item, parent, false));

            mName = (TextView) itemView.findViewById(R.id.name_txtView);
            mEmail = (TextView) itemView.findViewById(R.id.email_txtView);
        }

        public void bind(Staff staff, String key) {
            mName.setText(staff.getName());
            mEmail.setText(staff.getEmail());
            this.key = key;
        }



    }

    class StaffAdapter extends RecyclerView.Adapter<StaffItemView>{
        public List<Staff> mStaffList;
        private List<String> mKeys;

        public StaffAdapter(List<Staff> mStaffList, List<String> mKeys) {
            this.mStaffList = mStaffList;
            this.mKeys = mKeys;
        }
        @NonNull
        @Override
        public StaffItemView onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            return new StaffItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull StaffItemView holder, int position) {
            holder.bind(mStaffList.get(position), mKeys.get(position));

        }


        private LayoutInflater mInflater;

        private Comparator<Staff> mComparator;

//        public ExampleAdapter(Context context, Comparator<Staff> comparator) {
//            mInflater = LayoutInflater.from(context);
//            mComparator = comparator;
//        }


//        @Override
//        public void onBindViewHolder(ExampleViewHolder holder, int position) {
//            final ExampleModel model = mSortedList.get(position);
//            holder.bind(model);
//        }



        @Override
        public int getItemCount() {
            return mStaffList.size();
        }
    }
}
