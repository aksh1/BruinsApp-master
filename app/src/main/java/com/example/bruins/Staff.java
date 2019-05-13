package com.example.bruins;

import android.support.v7.util.SortedList;

import java.util.Comparator;

public class Staff implements SortedListAdapter.ViewModel {
    private String name;
    private String email;
    private long mId;

    public Staff() {}

    public Staff(String name, String email, long id) {
        this.name = name;
        this.email = email;
        mId = id;
    }

    public long getId() {return mId;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Staff model = (Staff) o;

        if (mId != model.mId) return false;
        return name != null ? name.equals(model.name) : model.name == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (mId ^ (
                mId>>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public boolean areItemsTheSame(Staff item1, Staff item2) {
        return item1.getId() == item2.getId();
    }

    private static final Comparator<Staff> ALPHABETICAL_COMPARATOR = new Comparator<Staff>() {
        @Override
        public int compare(Staff a, Staff b) {
            return a.getName().compareTo(b.getName());
        }
    };


}
