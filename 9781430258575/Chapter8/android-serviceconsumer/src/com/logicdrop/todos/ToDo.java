package com.logicdrop.todos;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ToDo implements Parcelable {

    @SerializedName("id")
    private Long mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("email")
    private String mEmail;

    /*
     * Default constructor for general object creation
     */
    public ToDo() {

    }

    /*
     * Constructor needed for parcelable object creation
     */
    public ToDo(Parcel item) {
        mId = item.readLong();
        mTitle = item.readString();
        mEmail = item.readString();
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    /*
     * Used to generate parcelable classes from a parcel
     */
    public static final Parcelable.Creator<ToDo> CREATOR
            = new Parcelable.Creator<ToDo>() {
        public ToDo createFromParcel(Parcel in) {
            return new ToDo(in);
        }

        public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if(mId != null) {
            parcel.writeLong(mId);
        }
        else {
            parcel.writeLong(-1);
        }
        parcel.writeString(mTitle);
        parcel.writeString(mEmail);
    }
}
