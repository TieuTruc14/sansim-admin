package com.osp.web.dao.aggregation;

import com.osp.modelCustomer.Aggregation;
import com.osp.modelCustomer.view.CacGoiCuoc;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/30/2018.
 */
public interface AggregationDao {
    Optional<Aggregation> getByDate(Date date);
    Optional<Boolean> edit(Aggregation item);
    Optional<Boolean> add(Aggregation item);
    Optional<Boolean> addList(List<Aggregation> items);
    Optional<List<Aggregation>> getAggregationWeek(int year, int week);
    Optional<List<Aggregation>> getAggregationMonth(int year,int month);
    Optional<List<Aggregation>> getAggregationYear(int year);
    Optional<Object[]> soLuongGiaoDichTuanType(int year, int week);
    Optional<Object[]> soLuongGiaoDichThangType(int year,int month);
    Optional<Object[]> soLuongGiaoDichNamType(int year);
    Optional<Object[]> DoanhThuTuanType(int year,int week);
    Optional<Object[]> DoanhThuThangType(int year,int month);
    Optional<Object[]> DoanhThuNamType(int year);
    Optional<Aggregation> getByDay(int year,int month,int day);
}
