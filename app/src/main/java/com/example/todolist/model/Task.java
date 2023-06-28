package com.example.todolist.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Task implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "listId")
    public int listId;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    @ColumnInfo(name = "done")
    public boolean done;

    @ColumnInfo(name = "important")
    public boolean important;

    public Task(){
    }

    protected Task(Parcel in) {
        uid = in.readInt();
        listId = in.readInt();
        text = in.readString();
        timestamp = in.readLong();
        done = in.readByte() != 0;
        important = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return uid == task.uid && listId == task.listId && timestamp == task.timestamp && done == task.done && important == task.important && Objects.equals(text, task.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, listId, text, timestamp, done, important);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(uid);
        parcel.writeInt(listId);
        parcel.writeString(text);
        parcel.writeLong(timestamp);
        parcel.writeByte((byte) (done ? 1 : 0));
        parcel.writeByte((byte) (important ? 1 : 0));
    }
}
