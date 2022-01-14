package com.example.mabiaat.offlinedata;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RepresentativesDao {

    //this function performs add new employee operation on the representative table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addRepresentative(Representative emp);

    //this function performs update employee operation on the representative table
    @Update
    public void updateRepresentative(Representative emp);

    //this function performs delete employee operation on the representative table
    @Delete
    public void deleteRepresentative(Representative emp);

    //this function perform as select all employees from representative table and return as a list of objects
    @Query("select * from Representative")
    public List<Representative> getAllRepresentatives();

    //this function performs select a1specific representative by his id and return him as an object
    @Query("select * from Representative where id = :id1")
    public Representative getRepresentativeById(int id1);

    @Query("select id from Representative")
    public List<Integer> getIds();


 }
