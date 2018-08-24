package com.osp.web.service.aggregation;

import com.osp.modelCustomer.Aggregation;
import com.osp.modelCustomer.view.CacGoiCuoc;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/1/2018.
 */
public interface AggregationService {
    Optional<Aggregation> getByDate(Date date);
    Optional<List<Aggregation>> getAggregationWeek(int year, int week);
    Optional<List<Aggregation>> getAggregationMonth(int year,int month);
    Optional<List<Aggregation>> getAggregationYear(int year);
    Optional<CacGoiCuoc> soLuongGiaoDichTuanType(int year,int week);
    Optional<CacGoiCuoc> soLuongGiaoDichThangType(int year,int month);
    Optional<CacGoiCuoc> soLuongGiaoDichNamType(int year);
    Optional<CacGoiCuoc> DoanhThuTuanType(int year,int week);
    Optional<CacGoiCuoc> DoanhThuThangType(int year,int month);
    Optional<CacGoiCuoc> DoanhThuNamType(int year);

    Optional<Aggregation> getByDay(int year,int month,int day);
}
