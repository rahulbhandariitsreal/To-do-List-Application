package com.example.to_do_list.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.to_do_list.BR;

import java.util.Objects;


@Entity(tableName = "tasktable")
public class List_Table extends BaseObservable implements Parcelable {



    @PrimaryKey(autoGenerate = true)
    private int list_id;



    @ColumnInfo(name = "time")
    private String reqtime;

    @ColumnInfo(name = "Task")
    private String task;


    public List_Table(int list_id, String task,String reqtime) {
        this.list_id = list_id;
        this.task = task;
        this.reqtime=reqtime;
    }
@Ignore
    public List_Table() {
    }

    protected List_Table(Parcel in) {
        list_id = in.readInt();
        reqtime = in.readString();
        task = in.readString();
    }

    public static final Creator<List_Table> CREATOR = new Creator<List_Table>() {
        @Override
        public List_Table createFromParcel(Parcel in) {
            return new List_Table(in);
        }

        @Override
        public List_Table[] newArray(int size) {
            return new List_Table[size];
        }
    };

    @Bindable
    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
notifyPropertyChanged(BR.list_id);
    }

    @Bindable
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
notifyPropertyChanged(BR.task);
    }

    @NonNull
    @Override
    public String toString() {
        return this.task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        List_Table that = (List_Table) o;
        return list_id == that.list_id && reqtime.equals(that.reqtime) && task.equals(that.task);
    }

    @Bindable
    public String getReqtime() {
        return reqtime;
    }

    public void setReqtime(String reqtime) {
        this.reqtime = reqtime;
    notifyPropertyChanged(BR.reqtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list_id, task);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(list_id);
        dest.writeString(reqtime);
        dest.writeString(task);
    }
}
