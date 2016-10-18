package com.logicdrop.todos;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ToDoId implements Parcelable {

    @SerializedName("id")
    private long mId;

    public ToDoId(){

    }

    public ToDoId(Parcel item){
        mId = item.readLong();
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
    }
}
