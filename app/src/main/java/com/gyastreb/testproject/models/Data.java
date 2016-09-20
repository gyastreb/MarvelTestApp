package com.gyastreb.testproject.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gyastreb on 9/16/16.
 */
@DatabaseTable(tableName = "data")
public class Data implements Serializable{

    @DatabaseField()
    int offset;

    @DatabaseField()
    int limit;

    @DatabaseField()
    int total;

    @DatabaseField()
    int count;

    @ForeignCollectionField(eager = true)
    ArrayList<Result> results;

    public Data() {
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }
}
