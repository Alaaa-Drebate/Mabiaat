package com.example.mabiaat.offlinedata;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
    @ForeignKey(
            entity = Representative.class,
            parentColumns = "id",
            childColumns = "representativeId",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
    )
})
public class Commission {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;

    //foreign key from representative table
    int representativeId;

    int month;
    int year;
    double northCommission;
    double southCommission;
    double eastCommission;
    double westCommission;
    double lebanonCommission;
    double commission;
    String createdAt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(int representativeId) {
        this.representativeId = representativeId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getNorthCommission() {
        return northCommission;
    }

    public void setNorthCommission(double northCommission) {
        this.northCommission = northCommission;
    }

    public double getSouthCommission() {
        return southCommission;
    }

    public void setSouthCommission(double southCommission) {
        this.southCommission = southCommission;
    }

    public double getEastCommission() {
        return eastCommission;
    }

    public void setEastCommission(double eastCommission) {
        this.eastCommission = eastCommission;
    }

    public double getWestCommission() {
        return westCommission;
    }

    public void setWestCommission(double westCommission) {
        this.westCommission = westCommission;
    }

    public double getLebanonCommission() {
        return lebanonCommission;
    }

    public void setLebanonCommission(double lebanonCommission) {
        this.lebanonCommission = lebanonCommission;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
