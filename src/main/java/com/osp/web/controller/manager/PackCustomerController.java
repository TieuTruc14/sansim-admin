package com.osp.web.controller.manager;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.modelCustomer.ConfigPackage;
import com.osp.web.service.msisdn.ConfigPackageService;
import com.osp.web.service.transpay.TranspayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/10/2018.
 */
@Controller
@RequestMapping("/administer/pack-cus")
public class PackCustomerController {
    private Logger logger= LogManager.getLogger(PackCustomerController.class);
    @Autowired
    TranspayService transpayService;
    @Autowired
    ConfigPackageService configPackageService;

    @GetMapping("/list")
    @Secured(ConstantAuthor.Transpay.view)
    public String list(){
        return "pack.cus.list";
    }

    @GetMapping("/search")
    @Secured(ConstantAuthor.Transpay.view)
    public ResponseEntity<PagingResult> search(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
                                               String msisdn,String username,String from,String to,Long packageId,Byte type){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=transpayService.page(page, Utils.trim(msisdn),Utils.trim(username),Utils.trim(from),Utils.trim(to),packageId,type).orElse(new PagingResult());
        }catch (Exception e){
            return  new ResponseEntity<PagingResult>(new PagingResult(), HttpStatus.OK);
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

}
