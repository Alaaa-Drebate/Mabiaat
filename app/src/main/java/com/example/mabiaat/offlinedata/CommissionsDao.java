package com.example.mabiaat.offlinedata;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CommissionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addCommissionReport(Commission commission);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateCommission(Commission commission);

    @Query("select * from Commission")
    public List<Commission> getAllCommissionsReports();

    @Query("select * from Commission where id = :id1")
    public Commission getCommissionReportById(int id1);

    @Query("select * from Commission where representativeId = :empId")
    public List<Commission> getCommissionForEmp(int empId);

    @Query("select * from Commission where year = :year")
    public List<Commission> getCommissionYear( int year);

    @Query("select * from Commission where month = :month")
    public List<Commission> getCommissionMonth(int month);

    @Query("select * from Commission where representativeId = :empId AND year = :year AND month = :month")
    public Commission getCommissionForEmpNDate(int empId, int year, int month);

    @Query("select * from Commission INNER JOIN representative ON representative.id = Commission.representativeId where name LIKE '%' || :query || '%' ")
    public List<Commission> getCommissionsEmpName(String query);

    @Query("select * from Commission INNER JOIN representative ON representative.id = Commission.representativeId where name LIKE '%' || :query || '%' AND month = :month")
    public List<Commission> getCommissionsEmpMonth(String query, int month);

    @Query("select * from Commission INNER JOIN representative ON representative.id = Commission.representativeId where name LIKE '%' || :query || '%' AND year = :year")
    public List<Commission> getCommissionsEmpYear(String query, int year);

    @Query("select * from Commission INNER JOIN representative ON representative.id = Commission.representativeId where name LIKE '%' || :query || '%' AND month = :month AND year = :year")
    public List<Commission> getCommissionsMonthYearEmpName(String query, int month, int year);

    @Query("select * from Commission where year = :year AND month = :month")
    public List<Commission> getCommissionsYearMonth(int year, int month);


 }
