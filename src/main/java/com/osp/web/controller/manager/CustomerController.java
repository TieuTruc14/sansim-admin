package com.osp.web.controller.manager;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.modelCustomer.ConfigPackage;
import com.osp.modelCustomer.CustService;
import com.osp.modelCustomer.Customer;
import com.osp.web.service.customer.CustServiceService;
import com.osp.web.service.customer.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 1/4/2018.
 */
@Controller
@RequestMapping("/administer/customer")
public class CustomerController {
    private Logger logger= LogManager.getLogger(CustomerController.class);
    @Autowired
    CustomerService customerService;
    @Autowired
    CustServiceService custServiceService;

    @GetMapping("/list")
    @Secured(ConstantAuthor.Customer.view)
    public String list() {
        return "customer.list";
    }

    @GetMapping("/search")
    @Secured(ConstantAuthor.Customer.view)
    public ResponseEntity<PagingResult> customerList(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, Long msisdn, String username, String fullName, String packageCode, Byte active) {
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=customerService.page(page,msisdn,Utils.trim(username),Utils.trim(fullName),Utils.trim(packageCode),active).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error customerList:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @PutMapping("/lock")
    @Secured(ConstantAuthor.Customer.edit)
    public ResponseEntity<Boolean> lockCustomer(@RequestBody final Long id, HttpServletRequest request){
        if(id==null)  return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
        try{
            String ip= Utils.getIpClient(request);
            customerService.lockOrUnlock(id,Byte.valueOf((byte)2),ip);
        }catch (Exception e){
            logger.error("Have an error method lockCustomer:"+e.getMessage());
            return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(Boolean.TRUE,HttpStatus.OK);
    }

    @PutMapping("/unlock")
    @Secured(ConstantAuthor.Customer.edit)
    public ResponseEntity<Boolean> unlockCustomer(@RequestBody final Long id, HttpServletRequest request){
        if(id==null)  return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
        try{
            String ip= Utils.getIpClient(request);
            customerService.lockOrUnlock(id,Byte.valueOf((byte)1),ip);
        }catch (Exception e){
            logger.error("Have an error method unlockCustomer:"+e.getMessage());
            return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(Boolean.TRUE,HttpStatus.OK);
    }

    @GetMapping("/autosearch-username")
    public ResponseEntity<List<String>> autosearchUsername(String key){
        if(StringUtils.isBlank(key))return new ResponseEntity<List<String>>(new ArrayList<>(),HttpStatus.OK);
        List<String> list=new ArrayList<>();
        try{
            list=customerService.searchUsername(Utils.trim(key)).orElse(new ArrayList<>());
        }catch (Exception e){

        }
        return new ResponseEntity<List<String>>(list,HttpStatus.OK);
    }

    @PostMapping("/add-cus-package")
    @Secured(ConstantAuthor.Customer.edit)
    public ResponseEntity<Boolean> addPackageCustomer(Long cusId, Long packageId, Date dateExpired){
        if(cusId==null || packageId==null || dateExpired==null){
            return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
        }
        try{
            CustService item=new CustService();
            Customer cus=new Customer();
            ConfigPackage pag=new ConfigPackage();
            pag.setId(packageId);
            cus.setId(cusId);
            item.setCustomer(cus);
            item.setConfigPackage(pag);
            item.setStatus(Byte.valueOf("1"));
            item.setMtStatus(Byte.valueOf("0"));
            item.setExpiredDate(dateExpired);
            Boolean check=custServiceService.add(item).orElse(Boolean.FALSE);
            if(!check){
                return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(Boolean.TRUE,HttpStatus.OK);
    }

}
