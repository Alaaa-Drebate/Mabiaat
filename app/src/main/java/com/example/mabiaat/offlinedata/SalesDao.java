package com.example.mabiaat.offlinedata;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SalesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addSalesReport(Sales report);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateSalesReport(Sales report);

    @Query("select * from sales")
    public List<Sales> getAllSalesReports();

    @Query("select * from sales where id = :id1")
    public Sales getSalesReportById(int id1);

    @Query("select * from sales where representativeId = :empId")
    public List<Sales> getSalesForEmp(int empId);

    @Query("select * from sales where year = :year")
    public List<Sales> getSalesForYear(int year);

    @Query("select * from sales where  month = :month")
    public List<Sales> getSalesForMonth(int month);

    @Query("SELECT EXISTS(select * from sales where representativeId = :empId AND year = :year AND month = :month)")
    public boolean isSalesReportExisted(int empId, int year, int month);

    @Query("select * from sales where representativeId = :empId AND year = :year AND month = :month")
    public Sales getSalesReportByEmpYearMonth(int empId, int year, int month);

    @Query("select * from sales where year = :year AND month = :month")
    public List<Sales> getSalesReportByYearMonth(int year, int month);

    @Query("select * from sales INNER JOIN representative ON representative.id = sales.representativeId where name LIKE '%' || :query || '%' ")
    public List<Sales> getSalesReportByEmpName(String query);

    @Query("select * from sales INNER JOIN representative ON representative.id = sales.representativeId where name LIKE '%' || :query || '%' AND month = :month")
    public List<Sales> getSalesByMonthEmpName(String query, int month);

    @Query("select * from sales INNER JOIN representative ON representative.id = sales.representativeId where name LIKE '%' || :query || '%' AND year = :year")
    public List<Sales> getSalesByYearEmpName(String query, int year);

    @Query("select * from sales INNER JOIN representative ON representative.id = sales.representativeId where name LIKE '%' || :query || '%' AND month = :month AND year = :year")
    public List<Sales> getSalesByMonthYearEmpName(String query, int month, int year);

}
