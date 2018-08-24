package com.osp.web.controller.system;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.User;
import com.osp.web.service.logaccess.LogAccessService;
import com.osp.web.service.user.UserService;
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

/**
 * Created by Admin on 1/9/2018.
 */
@Controller
@RequestMapping("/system/history")
public class LogAccessController {
    private Logger logger= LogManager.getLogger(LogAccessController.class);
    @Autowired
    LogAccessService logAccessService;
    @Autowired
    UserService userService;

    @GetMapping("/list")
    @Secured(ConstantAuthor.Log.view)
    public String list(){
        return "log.list";
    }
    @GetMapping("/search")
    @Secured(ConstantAuthor.Log.view)
    public ResponseEntity<PagingResult> HistoryList(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, String username) {
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page= logAccessService.page(page, Utils.trim(username)).orElse(new PagingResult());
        }catch (Exception e){

        }
        return new ResponseEntity<PagingResult>(page,HttpStatus.OK);
    }

    @GetMapping("/user-log")
    @Secured(ConstantAuthor.Log.view)
    public ResponseEntity<PagingResult> logOfUser( Long userId,@RequestParam(value = "p", required = false, defaultValue = "1")int pageNumber){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        if(userId==null || userId.longValue()==0) return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
        try{
            page=logAccessService.getByUserId(page,userId).orElse(new PagingResult());
        }catch (Exception e){

        }
        return new ResponseEntity<PagingResult>(page,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @Secured(ConstantAuthor.Log.view)
    public String getOfUser(Model model,@PathVariable("id") Long userId){
        if(userId==null|| userId.longValue()==0) return "404";
        try{
            User user=userService.get(userId).orElse(null);
            if(user==null) return "404";
            model.addAttribute("user",user);
        }catch (Exception e){
            return "404";
        }
        return "log.user";
    }
}
