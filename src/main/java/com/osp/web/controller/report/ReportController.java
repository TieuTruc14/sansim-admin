package com.osp.web.controller.report;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.modelCustomer.ConfigPackage;
import com.osp.modelCustomer.MsisdnLog;
import com.osp.web.service.msisdn.ConfigPackageService;
import com.osp.web.service.msisdn.MsisdnLogService;
import com.osp.web.service.transpay.TranspayService;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Admin on 1/11/2018.
 */
@Controller
@RequestMapping("/report")
public class ReportController {
    private Logger logger= LogManager.getLogger(ReportController.class);
    @Autowired
    TranspayService transpayService;
    @Autowired
    ConfigPackageService configPackageService;
    @Autowired
    MsisdnLogService msisdnLogService;

    /*BAO CAO TONG HOP DOANH THU */
    @GetMapping("/general-sale-sms")
    @Secured(ConstantAuthor.Report.general_sale_sms)
    public String generalSaleSms(){
        return "report.general.sale.sms";
    }

    /*BAO CAO CHI TIET DOANH THU*/
    @GetMapping("/detail-sale-sms")
    @Secured(ConstantAuthor.Report.detail_sale_sms)
    public String detailSaleSms(){
        return "report.detail.sale.sms";
    }

    /*BAO CAO TONG HOP LUONG GIAO DICH*/
    @GetMapping("/general-trade")
    @Secured(ConstantAuthor.Report.general_trade)
    public String generalTrade(){
        return "report.general.trade";
    }

    /*BAO CAO CHI TIET LUONG GIAO DICH*/
    @GetMapping("/detail-trade")
    @Secured(ConstantAuthor.Report.detail_trade)
    public String detailTrade(){
        return "report.detail.trade";
    }

    @GetMapping("/general-sale-sms/search")
    @Secured({ConstantAuthor.Report.general_sale_sms})
    public ResponseEntity<PagingResult> generalSaleSmsSearch(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
                                                                     Byte type,String username,String from,String to){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=transpayService.pageReportGeneralSaleAndTrade(page,type,false, Utils.trim(username),Utils.trim(from),Utils.trim(to)).orElse(new PagingResult());
        }catch (Exception e){
            logger.warn("Have an error when get data method generalSaleSmsAndTradeSearch:"+e.getMessage());
        }

        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }
    @GetMapping("/general-trade/search")
    @Secured({ConstantAuthor.Report.general_sale_sms,ConstantAuthor.Report.general_trade})
    public ResponseEntity<PagingResult> generalTradeSearch(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
                                                                     Byte type,String username,String from,String to){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=transpayService.pageReportGeneralSaleAndTrade(page,type,true, Utils.trim(username),Utils.trim(from),Utils.trim(to)).orElse(new PagingResult());
        }catch (Exception e){
            logger.warn("Have an error when get data method generalSaleSmsAndTradeSearch:"+e.getMessage());
        }

        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }
    @GetMapping("/general-sale-sms/download")
    @Secured(ConstantAuthor.Report.general_sale_sms)
    public void downloadGeneralSaleSmsSearch(HttpServletResponse response,Byte type, String username, String from, String to){
        PagingResult page=new PagingResult();
        page.setPageNumber(1);
        try{
            page=transpayService.downloadReportGeneralSaleAndTrade(page,type,false,Utils.trim(username),Utils.trim(from),Utils.trim(to)).orElse(new PagingResult());

            if(page.getItems().size()==0) return;
            Map<String, Object> beans = new HashMap<String, Object>();
            String typeString=genTypeName(type);
            if(StringUtils.isBlank(typeString)) typeString="Tất cả";
            beans.put("type",typeString);
            beans.put("from",from);
            if(StringUtils.isNotBlank(to)){
                beans.put("to","Đến ngày: "+to);
            }
            if(StringUtils.isNotBlank(username)){
                beans.put("username","Người bán: "+username);
            }

            beans.put("page",page);
            Calendar cal = Calendar.getInstance();
            cal.setTime( new Date());
            beans.put("year", cal.get(Calendar.YEAR));
            beans.put("month", cal.get(Calendar.MONTH)+1);
            beans.put("day", cal.get(Calendar.DAY_OF_MONTH));
            long total=0;

            for(int i=0;i<page.getItems().size();i++){
                Object[] item=(Object[])page.getItems().get(i);
                total+=Long.parseLong(String.valueOf(item[3]));
                String typeName=genTypeName(Byte.parseByte(String.valueOf(item[0])));
                item[0]=typeName;
            }
            beans.put("total",total);

            Resource resource = new ClassPathResource("file/report/TongHopDoanhThuSms.xlsx");
            InputStream fileIn = resource.getInputStream();

            ExcelTransformer transformer = new ExcelTransformer();
            Workbook workbook = transformer.transform(fileIn, beans);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + "BaoCaoTongHopDoanhThuSms.xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        }catch (Exception e){
            logger.warn("Have an error when get data method downloadGeneralSaleSmsSearch:"+e.getMessage());
        }

    }
    @GetMapping("/general-trade/download")
    @Secured(ConstantAuthor.Report.general_trade)
    public void downloadGeneralTradeSearch(HttpServletResponse response,Byte type, String username, String from, String to){
        PagingResult page=new PagingResult();
        page.setPageNumber(1);
        try{
            page=transpayService.downloadReportGeneralSaleAndTrade(page,type,true,Utils.trim(username),Utils.trim(from),Utils.trim(to)).orElse(new PagingResult());

            if(page.getItems().size()==0) return;
            Map<String, Object> beans = new HashMap<String, Object>();
            String typeString=genTypeName(type);
            if(StringUtils.isBlank(typeString)) typeString="Tất cả";
            beans.put("type",typeString);
            beans.put("from",from);
            if(StringUtils.isNotBlank(to)){
                beans.put("to","Đến ngày: "+to);
            }
            if(StringUtils.isNotBlank(username)){
                beans.put("username","Người bán: "+username);
            }
            beans.put("page",page);
            Calendar cal = Calendar.getInstance();
            cal.setTime( new Date());
            beans.put("year", cal.get(Calendar.YEAR));
            beans.put("month", cal.get(Calendar.MONTH)+1);
            beans.put("day", cal.get(Calendar.DAY_OF_MONTH));
            long total=0;

            for(int i=0;i<page.getItems().size();i++){
                Object[] item=(Object[])page.getItems().get(i);
                total+=Long.parseLong(String.valueOf(item[4]));
                String typeName=genTypeName(Byte.parseByte(String.valueOf(item[0])));
                item[0]=typeName;
            }
            beans.put("total",total);

            Resource resource = new ClassPathResource("file/report/TongHopLuongGiaoDich.xlsx");
            InputStream fileIn = resource.getInputStream();

            ExcelTransformer transformer = new ExcelTransformer();
            Workbook workbook = transformer.transform(fileIn, beans);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + "BaoCaoTongHopLuongGiaoDich.xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        }catch (Exception e){
            logger.warn("Have an error when get data method downloadGeneralTradeSearch:"+e.getMessage());
        }

    }

    private String genTypeName(Byte type){
        try{
            switch (type){
                case 1:
                    return "Đăng ký gói";
                case 2:
                    return "Gia hạn gói";
                case 3:
                    return "Xác thực";
                case 4:
                    return "Gia hạn xác thực";
                case 5:
                    return "Hủy do hệ thống";
                case 6:
                    return "Người dùng hủy";
                default:
                    return "";
            }
        }catch (Exception e){

        }

        return "";
    }

    @GetMapping("/detail-sale-sms/search")
    @Secured({ConstantAuthor.Report.detail_sale_sms,ConstantAuthor.Report.detail_trade})
    public ResponseEntity<PagingResult> detailSaleSmsSearch(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
                                                             Byte type,String username,String from,String to,Long packageId){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=transpayService.pageReportDetailSaleAndTrade(page,type,false,Utils.trim(username),Utils.trim(from),Utils.trim(to),packageId).orElse(new PagingResult());
        }catch (Exception e){
            logger.warn("Have an error when get data method detailSaleSmsAndTradeSearch:"+e.getMessage());
        }

        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }
    @GetMapping("/detail-trade/search")
    @Secured({ConstantAuthor.Report.detail_sale_sms,ConstantAuthor.Report.detail_trade})
    public ResponseEntity<PagingResult> detailTradeSearch(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
                                                                    Byte type,String username,String from,String to,Long packageId){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=transpayService.pageReportDetailSaleAndTrade(page,type,true,Utils.trim(username),Utils.trim(from),Utils.trim(to),packageId).orElse(new PagingResult());
        }catch (Exception e){
            logger.warn("Have an error when get data method detailSaleSmsAndTradeSearch:"+e.getMessage());
        }

        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/detail-sale-sms/download")
    @Secured(ConstantAuthor.Report.detail_sale_sms)
    public void downloadDetailSaleSmsAndTradeSearch(HttpServletResponse response,Byte type, String username, String from, String to,Long packageId){
        PagingResult page=new PagingResult();
        page.setPageNumber(1);
        try{
            page=transpayService.downloadReportDetailSaleAndTrade(page,type,false,Utils.trim(username),Utils.trim(from),Utils.trim(to),packageId).orElse(new PagingResult());

            if(page.getItems().size()==0) return;
            Map<String, Object> beans = new HashMap<String, Object>();
            String typeString=genTypeName(type);
            if(StringUtils.isBlank(typeString)) typeString="Tất cả";
            beans.put("type",typeString);
            if(packageId!=null){
                ConfigPackage item=configPackageService.get(packageId).orElse(new ConfigPackage());
                if(StringUtils.isNotBlank(typeString)){
                    beans.put("tradeName","Giao dịch: "+typeString+ " "+item.getPackageCode());
                }
            }

            beans.put("from",from);
            if(StringUtils.isNotBlank(to)){
                beans.put("to","Đến ngày: "+to);
            }
            if(StringUtils.isNotBlank(username)){
                beans.put("username","Người bán: "+username);
            }
            beans.put("page",page);
            Calendar cal = Calendar.getInstance();
            cal.setTime( new Date());
            beans.put("year", cal.get(Calendar.YEAR));
            beans.put("month", cal.get(Calendar.MONTH)+1);
            beans.put("day", cal.get(Calendar.DAY_OF_MONTH));
            long total=0;

            for(int i=0;i<page.getItems().size();i++){
                Object[] item=(Object[])page.getItems().get(i);
                total+=Long.parseLong(String.valueOf(item[3]));
                String typeName=genTypeName(Byte.parseByte(String.valueOf(item[0])));
                item[0]=typeName;
            }
            beans.put("total",total);

            Resource resource = new ClassPathResource("file/report/ChiTietDoanhThuSms.xlsx");
            InputStream fileIn = resource.getInputStream();

            ExcelTransformer transformer = new ExcelTransformer();
            Workbook workbook = transformer.transform(fileIn, beans);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + "BaoCaoChiTietDoanhThuSms.xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        }catch (Exception e){
            logger.warn("Have an error when get data method downloadDetailSaleSmsAndTradeSearch:"+e.getMessage());
        }

    }
    @GetMapping("/detail-trade/download")
    @Secured(ConstantAuthor.Report.detail_trade)
    public void downloadDetailTradeSearch(HttpServletResponse response,Byte type, String username, String from, String to,Long packageId){
        PagingResult page=new PagingResult();
        page.setPageNumber(1);
        try{
            page=transpayService.downloadReportDetailSaleAndTrade(page,type,true,Utils.trim(username),Utils.trim(from),Utils.trim(to),packageId).orElse(new PagingResult());

            if(page.getItems().size()==0) return;
            Map<String, Object> beans = new HashMap<String, Object>();
            String typeString=genTypeName(type);
            if(StringUtils.isBlank(typeString)) typeString="Tất cả";
            beans.put("type",typeString);
            if(packageId!=null){
                ConfigPackage item=configPackageService.get(packageId).orElse(new ConfigPackage());
                if(StringUtils.isNotBlank(typeString)){
                    beans.put("tradeName",typeString+ " "+item.getPackageCode());
                }
            }
            if(packageId!=null){
                ConfigPackage item=configPackageService.get(packageId).orElse(new ConfigPackage());
                if(StringUtils.isNotBlank(typeString)){
                    beans.put("tradeName","Giao dịch: "+typeString+ " "+item.getPackageCode());
                }
            }
            beans.put("from",from);
            if(StringUtils.isNotBlank(to)){
                beans.put("to","Đến ngày: "+to);
            }
            if(StringUtils.isNotBlank(username)){
                beans.put("username","Người bán: "+username);
            }
            beans.put("page",page);
            Calendar cal = Calendar.getInstance();
            cal.setTime( new Date());
            beans.put("year", cal.get(Calendar.YEAR));
            beans.put("month", cal.get(Calendar.MONTH)+1);
            beans.put("day", cal.get(Calendar.DAY_OF_MONTH));
            long total=0;
            for(int i=0;i<page.getItems().size();i++){
                Object[] item=(Object[])page.getItems().get(i);
                total+=Long.parseLong(String.valueOf(item[4]));
                String typeName=genTypeName(Byte.parseByte(String.valueOf(item[0])));
                item[0]=typeName;
            }
            beans.put("total",total);

            Resource resource = new ClassPathResource("file/report/ChiTietLuongGiaoDich.xlsx");
            InputStream fileIn = resource.getInputStream();

            ExcelTransformer transformer = new ExcelTransformer();
            Workbook workbook = transformer.transform(fileIn, beans);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + "BaoCaoChiTietLuongGiaoDich.xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        }catch (Exception e){
            logger.warn("Have an error when get data method downloadDetailTradeSearch:"+e.getMessage());
        }

    }

    /*BAO CAO THEM-XOA THUE BAO*/
    @GetMapping("/crud-msisdn")
    @Secured(ConstantAuthor.Report.add_and_delete_msisdn)
    public String crudMsisdn(){
        return "report.crud.msisdn";
    }

    @GetMapping("/crud-msisdn/search")
    @Secured(ConstantAuthor.Report.add_and_delete_msisdn)
    public ResponseEntity<PagingResult> crudMsisdnSearch(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
                                                                    Byte type,String username,String from,String to){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=msisdnLogService.pageReport(page,type,Utils.trim(username),Utils.trim(from),Utils.trim(to)).orElse(new PagingResult());
        }catch (Exception e){
            logger.warn("Have an error when get data method crudMsisdnSearch:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/crud-msisdn/download")
    @Secured(ConstantAuthor.Report.add_and_delete_msisdn)
    public void downloadCrudMsisdn(HttpServletResponse response,Byte type, String username, String from, String to) {
        PagingResult page = new PagingResult();
        page.setPageNumber(1);
        try {
            page = msisdnLogService.downloadReport(page, type, Utils.trim(username), Utils.trim(from), Utils.trim(to)).orElse(new PagingResult());

            if (page.getItems().size() == 0) return;
            Map<String, Object> beans = new HashMap<String, Object>();

            String typeString = genTypeMsisdnLog(type);
            beans.put("type", typeString);
            beans.put("from", from);
            if (StringUtils.isNotBlank(to)) {
                beans.put("to", "Đến ngày: " + to);
            }
            if(StringUtils.isNotBlank(username)){
                beans.put("username","Người bán: "+username);
            }
            beans.put("page", page);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            beans.put("year", cal.get(Calendar.YEAR));
            beans.put("month", cal.get(Calendar.MONTH) + 1);
            beans.put("day", cal.get(Calendar.DAY_OF_MONTH));
            long total = 0;

            for (int i = 0; i < page.getItems().size(); i++) {
                Object[] item = (Object[]) page.getItems().get(i);
                total += Long.parseLong(String.valueOf(item[3]));
                String typeName = genTypeMsisdnLog(Byte.parseByte(String.valueOf(item[0])));
                item[0] = typeName;
            }
            beans.put("total", total);

            Resource resource = new ClassPathResource("file/report/ThemXoaSo.xlsx");
            InputStream fileIn = resource.getInputStream();

            ExcelTransformer transformer = new ExcelTransformer();
            Workbook workbook = transformer.transform(fileIn, beans);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + "BaoCaoThemXoaSo.xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {
            logger.warn("Have an error when get data method downloadCrudMsisdn:" + e.getMessage());
        }
    }
    private String genTypeMsisdnLog(Byte type){
        if(type!=null){
            switch (type){
                case 0:
                    return "Số bị xóa";
                case 1: return "Số đăng mới";
                default:return "Tất cả";
            }
        }
        return "Tất cả";
    }

}
