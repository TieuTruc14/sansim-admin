package com.osp.web.controller.category;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.modelCustomer.GroupMsisdn;
import com.osp.modelCustomer.GroupPrice;
import com.osp.modelCustomer.GroupYear;
import com.osp.modelCustomer.view.SapXep;
import com.osp.validator.category.GroupPriceVallidator;
import com.osp.validator.category.GroupYearValidator;
import com.osp.web.service.category.GroupMsisdnService;
import com.osp.web.service.category.GroupPriceService;
import com.osp.web.service.category.GroupYearService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 2/6/2018.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    private Logger logger= LogManager.getLogger(CategoryController.class);
    @Autowired
    GroupPriceService groupPriceService;
    @Autowired
    GroupYearService groupYearService;
    @Autowired
    GroupMsisdnService groupMsisdnService;
    @Autowired
    GroupPriceVallidator groupPriceVallidator;
    @Autowired
    GroupYearValidator groupYearValidator;


    @GetMapping("/group-price/list")
    @Secured(ConstantAuthor.GroupPrice.view)
    public String grouppriceList(Model model){
        List<GroupPrice> items=new ArrayList<>();
        try{
            items=groupPriceService.list().orElse(new ArrayList<>());
        }catch (Exception e){
            logger.error("Have an error on method grouppriceList:"+e.getMessage());
        }
        model.addAttribute("listAll",items);
        return "category.group.price.list";
    }

    @GetMapping("/group-price/list/search")
    @Secured(ConstantAuthor.GroupPrice.view)
    public ResponseEntity<PagingResult> grouppriceListSearch( @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, String name){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=groupPriceService.page(page,name).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error on method grouppriceListSearch:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/group-price/add")
    @Secured(ConstantAuthor.GroupPrice.add)
    public String grouppriceAdd(){
        return "category.group.price.add";
    }

    @PostMapping("/group-price/add")
    @Secured(ConstantAuthor.GroupPrice.add)
    public String groupPriceAdd(Model model, @Valid GroupPrice item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        groupPriceVallidator.validate(item,result);
        if(result.hasErrors()){
            model.addAttribute("item",item);
            return "category.group.price.add";
        }
        boolean check=false;
        try{
            String ipClient= Utils.getIpClient(request);
            check=groupPriceService.addWithIp(item,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("Have a error method groupPriceAdd():"+e.getMessage());
        }
        if(!check){
            model.addAttribute("item",item);
            model.addAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
            return "category.group.price.add";
        }
        attributes.addFlashAttribute("success", "Thêm mới thành công!");
        return "redirect:/category/group-price/list";
    }

    @GetMapping("/group-price/edit/{id}")
    @Secured(ConstantAuthor.GroupPrice.edit)
    public String grouppriceEdit(Model model, @PathVariable("id") Integer id){
        GroupPrice item=groupPriceService.get(id).orElse(null);
        if(item==null) return "404";
        model.addAttribute("item",item);
        return "category.group.price.edit";
    }

    @PostMapping("/group-price/edit")
    @Secured(ConstantAuthor.GroupPrice.edit)
    public String groupPriceEdit(Model model, @Valid GroupPrice item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        groupPriceVallidator.validate(item,result);
        if(result.hasErrors()){
            model.addAttribute("item",item);
            return "category.group.price.edit";
        }else if(item.getId()==null){
            return "404";
        }
        boolean check=false;
        try{
            String ipClient= Utils.getIpClient(request);
            check=groupPriceService.edit(item,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("Have a error method groupPriceEdit():"+e.getMessage());
        }
        if(!check){
            model.addAttribute("item",item);
            model.addAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
            return "category.group.price.edit";
        }
        attributes.addFlashAttribute("success", "Sửa thông tin thành công!");
        return "redirect:/category/group-price/list";
    }

    @PutMapping("/group-price/order-list-all")
    @Secured(ConstantAuthor.GroupPrice.edit)
    public ResponseEntity<Boolean> sapxepGroupPrice(@RequestBody List<SapXep> items){
        if(items==null || items.size()==0) return new ResponseEntity<Boolean>(false,HttpStatus.OK);
        List<GroupPrice> listAll=new ArrayList<>();
        try{
            listAll=groupPriceService.list().orElse(new ArrayList<>());
            if(listAll==null || listAll.size()==0){
                return new ResponseEntity<Boolean>(false,HttpStatus.OK);
            }
            HashMap<String,Short> map=new HashMap<>();
            for(SapXep item:items){
                map.put(item.getId(),item.getOrder());
            }
            for(GroupPrice item:listAll){
                Short order=map.get(item.getId().toString());
                if(order!=null && order.intValue()>0){
                    item.setOrderNumber(order);
                }
            }
            boolean check=groupPriceService.editList(listAll).orElse(false);
            if(!check) return new ResponseEntity<Boolean>(false,HttpStatus.OK);
        }catch (Exception e){
            logger.error("Have an error on method sapxepGroupPrice:"+e.getMessage());
        }
        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }

    @PostMapping("/group-price/delete")
    @Secured(ConstantAuthor.GroupPrice.delete)
    public String deleteGroupPrice(Integer id, RedirectAttributes attributes,HttpServletRequest request){
        boolean check=false;
        try{
            String ipClient= Utils.getIpClient(request);
            check=groupPriceService.delete(id,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("have an error deleteGroupPrice:"+e.getMessage());
        }
        if(!check){
            attributes.addFlashAttribute("messageError","Có lỗi xảy ra, hãy thử lại sau!");
            return "redirect:/category/group-price/list";
        }
        attributes.addFlashAttribute("success","Xóa nhóm thành công!");
        return "redirect:/category/group-price/list";
    }


    /*FOR GROUP YEAR*/
    @GetMapping("/group-year/list")
    @Secured(ConstantAuthor.GroupYear.view)
    public String groupyearList(Model model){
        List<GroupYear> items=new ArrayList<>();
        try{
            items=groupYearService.list().orElse(new ArrayList<>());
        }catch (Exception e){
            logger.error("Have an error on method groupyearList:"+e.getMessage());
        }
        model.addAttribute("listAll",items);
        return "category.group.year.list";
    }

    @GetMapping("/group-year/list/search")
    @Secured(ConstantAuthor.GroupYear.view)
    public ResponseEntity<PagingResult> groupyearListSearch( @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, String name){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=groupYearService.page(page,name).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error on method grouppriceListSearch:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/group-year/add")
    @Secured(ConstantAuthor.GroupYear.add)
    public String groupyearAdd(){
        return "category.group.year.add";
    }

    @PostMapping("/group-year/add")
    @Secured(ConstantAuthor.GroupYear.add)
    public String groupYearAdd(Model model, @Valid GroupYear item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        groupYearValidator.validate(item,result);
        if(result.hasErrors()){
            model.addAttribute("item",item);
            return "category.group.year.add";
        }
        boolean check=false;
        try{
            String ipClient= Utils.getIpClient(request);
            check=groupYearService.addWithIp(item,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("Have a error method groupYearAdd():"+e.getMessage());
        }
        if(!check){
            model.addAttribute("item",item);
            model.addAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
            return "category.group.year.add";
        }
        attributes.addFlashAttribute("success", "Thêm mới thành công!");
        return "redirect:/category/group-year/list";
    }

    @GetMapping("/group-year/edit/{id}")
    @Secured(ConstantAuthor.GroupYear.edit)
    public String groupyearEdit(Model model, @PathVariable("id") Integer id){
        GroupYear item=groupYearService.get(id).orElse(null);
        if(item==null) return "404";
        model.addAttribute("item",item);
        return "category.group.year.edit";
    }

    @PostMapping("/group-year/edit")
    @Secured(ConstantAuthor.GroupYear.edit)
    public String groupYearEdit(Model model, @Valid GroupYear item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        groupYearValidator.validate(item,result);
        if(result.hasErrors()){
            model.addAttribute("item",item);
            return "category.group.year.edit";
        }else if(item.getId()==null){
            return "404";
        }
        boolean check=false;
        try{
            String ipClient= Utils.getIpClient(request);
            check=groupYearService.edit(item,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("Have a error method groupYearEdit():"+e.getMessage());
        }
        if(!check){
            model.addAttribute("item",item);
            model.addAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
            return "category.group.year.edit";
        }
        attributes.addFlashAttribute("success", "Sửa thông tin thành công!");
        return "redirect:/category/group-year/list";
    }
    @PutMapping("/group-year/order-list-all")
    @Secured(ConstantAuthor.GroupYear.edit)
    public ResponseEntity<Boolean> sapxepGroupYear(@RequestBody List<SapXep> items){
        if(items==null || items.size()==0) return new ResponseEntity<Boolean>(false,HttpStatus.OK);
        List<GroupYear> listAll=new ArrayList<>();
        try{
            listAll=groupYearService.list().orElse(new ArrayList<>());
            if(listAll==null || listAll.size()==0){
                return new ResponseEntity<Boolean>(false,HttpStatus.OK);
            }
            HashMap<String,Short> map=new HashMap<>();
            for(SapXep item:items){
                map.put(item.getId(),item.getOrder());
            }
            for(GroupYear item:listAll){
                Short order=map.get(item.getId().toString());
                if(order!=null && order.intValue()>0){
                    item.setOrderNumber(order);
                }
            }
            boolean check=groupYearService.editList(listAll).orElse(false);
            if(!check) return new ResponseEntity<Boolean>(false,HttpStatus.OK);
        }catch (Exception e){
            logger.error("Have an error on method sapxepGroupYear:"+e.getMessage());
        }
        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }

    @PostMapping("/group-year/delete")
    @Secured(ConstantAuthor.GroupPrice.delete)
    public String deleteGroupYear(Integer id, RedirectAttributes attributes,HttpServletRequest request){
        boolean check=false;
        try{
            String ipClient= Utils.getIpClient(request);
            check=groupYearService.delete(id,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("have an error deleteGroupYear:"+e.getMessage());
        }
        if(!check){
            attributes.addFlashAttribute("messageError","Có lỗi xảy ra, hãy thử lại sau!");
            return "redirect:/category/group-year/list";
        }
        attributes.addFlashAttribute("success","Xóa nhóm thành công!");
        return "redirect:/category/group-year/list";
    }


    /*FOR GROUP MSISDN*/
    @GetMapping("/group-type/list")
    @Secured(ConstantAuthor.GroupMsisdn.view)
    public String grouptypeList(Model model){
        List<GroupMsisdn> items=new ArrayList<>();
        try{
            items=groupMsisdnService.list().orElse(new ArrayList<>());
        }catch (Exception e){
            logger.error("Have an error on method grouptypeListAll:"+e.getMessage());
        }
        model.addAttribute("listAll",items);
        return "category.group.type.list";
    }

    @GetMapping("/group-type/list/search")
    @Secured(ConstantAuthor.GroupMsisdn.view)
    public ResponseEntity<PagingResult> grouptypeListSearch( @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, String name){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=groupMsisdnService.page(page,name).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error on method grouptypeListSearch:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/group-type/list-all")
    @Secured(ConstantAuthor.GroupMsisdn.view)
    public ResponseEntity<List<GroupMsisdn>> grouptypeListAll(){
        List<GroupMsisdn> items=new ArrayList<>();
        try{
            items=groupMsisdnService.list().orElse(new ArrayList<>());
        }catch (Exception e){
            logger.error("Have an error on method grouptypeListAll:"+e.getMessage());
        }
        return new ResponseEntity<List<GroupMsisdn>>(items, HttpStatus.OK);
    }

    @PutMapping("/group-type/order-list-all")
    @Secured(ConstantAuthor.GroupMsisdn.edit)
    public ResponseEntity<Boolean> sapxepGroupMsisdn(@RequestBody List<SapXep> items){
        if(items==null || items.size()==0) return new ResponseEntity<Boolean>(false,HttpStatus.OK);
        List<GroupMsisdn> listAll=new ArrayList<>();
        try{
            listAll=groupMsisdnService.list().orElse(new ArrayList<>());
            if(listAll==null || listAll.size()==0){
                return new ResponseEntity<Boolean>(false,HttpStatus.OK);
            }
            HashMap<String,Short> map=new HashMap<>();
            for(SapXep item:items){
                map.put(item.getId(),item.getOrder());
            }
            for(GroupMsisdn item:listAll){
                Short order=map.get(item.getId().toString());
                if(order!=null && order.intValue()>0){
                    item.setOrderNumber(order);
                }
            }
            boolean check=groupMsisdnService.editList(listAll).orElse(false);
            if(!check) return new ResponseEntity<Boolean>(false,HttpStatus.OK);
        }catch (Exception e){
            logger.error("Have an error on method sapxepGroupMsisdn:"+e.getMessage());
        }
        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }
}
