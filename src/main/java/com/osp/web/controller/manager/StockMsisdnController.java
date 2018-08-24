package com.osp.web.controller.manager;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.modelCustomer.StockMsisdn;
import com.osp.web.service.msisdn.StockMsisdnService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 1/6/2018.
 */
@Controller
@RequestMapping("/administer/msisdn")
public class StockMsisdnController {
    private Logger logger= LogManager.getLogger(StockMsisdnController.class);
    @Autowired
    StockMsisdnService stockMsisdnService;

    @GetMapping("/list")
    @Secured(ConstantAuthor.MsisdnHistory.view)
    public String list() {
        return "msisdn.list";
    }

    @GetMapping("/search")
    @Secured(ConstantAuthor.MsisdnHistory.view)
    public ResponseEntity<PagingResult> msisdnList(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
                                                   String username, String from, String to, String msisdn,Long price,Boolean confirmStatus,Integer confirmExpired,Byte telco) {
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=stockMsisdnService.page(page,Utils.trim(username),Utils.trim(from),Utils.trim(to),Utils.trim(msisdn),price,confirmStatus,confirmExpired,telco).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error msisdnList:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    @Secured(ConstantAuthor.MsisdnHistory.view)
    public String detail(Model model,@PathVariable("id") Long id) {
        if(id==null || !(id.longValue()>0)) return "404";
        StockMsisdn item=stockMsisdnService.get(id).orElse(null);
        if(item==null) return "404";
        model.addAttribute("item",item);
        return "msisdn.detail";
    }

}
