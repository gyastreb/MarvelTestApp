package com.gyastreb.testproject.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by gyastreb on 9/13/16.
 */
@DatabaseTable(tableName = "thumbnail")
public class Thumbnail implements Serializable{
    @DatabaseField(columnName = "path")
    String path;
    @DatabaseField(columnName = "extension")
    String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
