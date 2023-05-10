package com.example.knowledge.provider;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public int userId;
    public String userName;
    public int gender;
    public Book book;

    public User() {
    }

    public User(int userId, String userName, int gender) {
        this.userId = userId;
        this.userName = userName;
        this.gender = gender;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(userId);
        out.writeString(userName);
        out.writeInt(gender);
        out.writeParcelable(book, 0);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        gender = in.readInt();
        book = in
                .readParcelable(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public String toString() {
        return String.format(
                "[userId:%s, userName:%s, gender:%s, book:{%s}]",
                userId, userName, gender, book);
    }

}
