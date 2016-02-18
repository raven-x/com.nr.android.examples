package com.example.vkirillov.robospicesample;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by vkirillov on 16.12.2015.
 */
@DatabaseTable(tableName = "one_two_retrofit_entity")
public class OneTwoRetrofitEntity {
    @DatabaseField(allowGeneratedIdInsert = true, canBeNull = false, dataType = DataType.INTEGER, columnName = "id")
    private int id;
    @DatabaseField(dataType = DataType.STRING, columnName = "one")
    private String one;
    @DatabaseField(dataType = DataType.STRING, columnName = "two")
    private String two;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }
}
