package com.osp.web.service.aggregation;

import com.osp.modelCustomer.Aggregation;
import com.osp.modelCustomer.view.CacGoiCuoc;
import com.osp.web.dao.aggregation.AggregationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/1/2018.
 */
@Service
public class AggregationServiceImpl implements AggregationService{
    @Autowired
    AggregationDao aggregationDao;

    @Override
    public Optional<Aggregation> getByDate(Date date) {
        return aggregationDao.getByDate(date);
    }

    @Override
    public Optional<List<Aggregation>> getAggregationWeek(int year, int week) {
        return aggregationDao.getAggregationWeek(year,week);
    }

    @Override
    public Optional<List<Aggregation>> getAggregationMonth(int year, int month) {
        return aggregationDao.getAggregationMonth(year,month);
    }

    @Override
    public Optional<List<Aggregation>> getAggregationYear(int year) {
        return aggregationDao.getAggregationYear(year);
    }

    @Override
    public Optional<CacGoiCuoc> soLuongGiaoDichTuanType(int year, int week) {
        Object[] item=aggregationDao.soLuongGiaoDichTuanType(year,week).orElse(null);
        if(item!=null){
            CacGoiCuoc result=genInfoSoLuongCacGoiCuoc(item);
            return Optional.ofNullable(result);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<CacGoiCuoc> soLuongGiaoDichThangType(int year, int month) {
        Object[] item=aggregationDao.soLuongGiaoDichThangType(year,month).orElse(null);
        if(item!=null){
            CacGoiCuoc result=genInfoSoLuongCacGoiCuoc(item);
            return Optional.ofNullable(result);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<CacGoiCuoc> soLuongGiaoDichNamType(int year) {
        Object[] item=aggregationDao.soLuongGiaoDichNamType(year).orElse(null);
        if(item!=null){
            CacGoiCuoc result=genInfoSoLuongCacGoiCuoc(item);
            return Optional.ofNullable(result);
        }
        return Optional.ofNullable(null);
    }

    private CacGoiCuoc genInfoSoLuongCacGoiCuoc(Object[] item){
        long dangky=Long.parseLong(item[0].toString());
        long giahan=Long.parseLong(item[1].toString());
        long xacthuc=Long.parseLong(item[2].toString());
        long giahanxacthuc=Long.parseLong(item[3].toString());
        long huy=Long.parseLong(item[4].toString());
        long huynguoidung=Long.parseLong(item[5].toString());
        long total=dangky+giahan+xacthuc+giahanxacthuc+huy+huynguoidung;
        CacGoiCuoc result=new CacGoiCuoc(dangky,giahan,xacthuc,giahanxacthuc,huy,huynguoidung,total);
        return result;
    }
    private CacGoiCuoc genInfoDoanhThu(Object[] item){
        long dangky=Long.parseLong(item[0].toString());
        long giahan=Long.parseLong(item[1].toString());
        long xacthuc=Long.parseLong(item[2].toString());
        long giahanxacthuc=Long.parseLong(item[3].toString());
        long total=Long.parseLong(item[4].toString());
        CacGoiCuoc result=new CacGoiCuoc(dangky,giahan,xacthuc,giahanxacthuc,0,0,total);
        return result;
    }

    @Override
    public Optional<CacGoiCuoc> DoanhThuTuanType(int year, int week) {
        Object[] item=aggregationDao.DoanhThuTuanType(year,week).orElse(null);
        if(item!=null){
            CacGoiCuoc result=genInfoDoanhThu(item);
            return Optional.ofNullable(result);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<CacGoiCuoc> DoanhThuThangType(int year, int month) {
        Object[] item=aggregationDao.DoanhThuThangType(year,month).orElse(null);
        if(item!=null){
            CacGoiCuoc result=genInfoDoanhThu(item);
            return Optional.ofNullable(result);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<CacGoiCuoc> DoanhThuNamType(int year) {
        Object[] item=aggregationDao.DoanhThuNamType(year).orElse(null);
        if(item!=null){
            CacGoiCuoc result=genInfoDoanhThu(item);
            return Optional.ofNullable(result);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<Aggregation> getByDay(int year, int month, int day) {
        return aggregationDao.getByDay(year,month,day);
    }
}
