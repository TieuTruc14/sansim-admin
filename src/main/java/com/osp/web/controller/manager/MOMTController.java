package com.osp.web.controller.manager;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.web.service.momt.MOMTService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/8/2018.
 */
@Controller
@RequestMapping("/administer/mo-mt")
public class MOMTController {
    private Logger logger= LogManager.getLogger(MOMTController.class);
    @Autowired
    MOMTService momtService;

    @GetMapping("/momt-of-msisdn")
//    @Secured(ConstantAuthor.MOMT.view)
    public ResponseEntity<PagingResult> momtOfMsisdn(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, Long msisdnId){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=momtService.getMOMTofMsisdn(page,msisdnId).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error method momtOfMsisdn: can't get of msisdnId: "+msisdnId +"----"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/list")
    @Secured(ConstantAuthor.MOMT.view)
    public String list(){
        return "momt.list";
    }

    @GetMapping("/search")
    @Secured(ConstantAuthor.MOMT.view)
    public ResponseEntity<PagingResult> msisdnList(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
                                                   String username, String from, String to, String msisdn) {
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=momtService.page(page,Utils.trim(username),Utils.trim(from),Utils.trim(to),Utils.trim(msisdn)).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error msisdnList:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @PostMapping("/resendMT")
    @Secured(ConstantAuthor.MOMT.resend_mt)
    public ResponseEntity<Boolean> resendMT(@RequestBody final Long id, HttpServletRequest request){
        if(id==null)  return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
        try{
            String ip= Utils.getIpClient(request);
            boolean check= momtService.resendMT(id,ip).orElse(false);
            if(!check) return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
        }catch (Exception e){
            logger.error("Have an error method resendMT:"+e.getMessage());
            return new ResponseEntity<Boolean>(Boolean.FALSE,HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(Boolean.TRUE,HttpStatus.OK);
    }
}
