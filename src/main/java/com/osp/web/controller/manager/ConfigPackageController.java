package com.osp.web.controller.manager;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.modelCustomer.ConfigPackage;
import com.osp.validator.configPackage.ConfigPackageAddValidator;
import com.osp.web.controller.system.GroupController;
import com.osp.web.service.msisdn.ConfigPackageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/3/2018.
 */
@Controller
@RequestMapping("/administer/package")
public class ConfigPackageController {
    private Logger logger= LogManager.getLogger(ConfigPackageController.class);
    @Autowired
    ConfigPackageService configPackageService;
    @Autowired
    ConfigPackageAddValidator configPackageAddValidator;

    @GetMapping("/list")
    @Secured(ConstantAuthor.ConfigPackage.view)
    public String list(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,String packageCode,Long from,Long to,String packageName) {
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        page=configPackageService.page(Utils.trim(packageCode),Utils.trim(packageName),from,to,page).orElse(new PagingResult());
        model.addAttribute("page",page);
        model.addAttribute("packageCode",packageCode);
        model.addAttribute("packageName",packageName);
        model.addAttribute("from",from);
        model.addAttribute("to",to);
        return "package.list";
    }
    @GetMapping("/listAll")
    public ResponseEntity<List<ConfigPackage>> listAll(){
        List<ConfigPackage> list=configPackageService.list().orElse(new ArrayList<>());
        return new ResponseEntity<List<ConfigPackage>>(list, HttpStatus.OK);
    }
    @GetMapping("/add")
    @Secured(ConstantAuthor.ConfigPackage.add)
    public String addView() {
        return "package.add";
    }

    @PostMapping("/add")
    @Secured(ConstantAuthor.ConfigPackage.add)
    public String add(Model model, @Valid ConfigPackage item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
        configPackageAddValidator.validate(item,result);
        if(result.hasErrors()){
            model.addAttribute("item",item);
            return "package.add";
        }
        ConfigPackage itemDB=configPackageService.getByPackageCode(item.getPackageCode()).orElse(null);
        if(itemDB!=null){
            model.addAttribute("messageError","Mã gói cước đã tồn tại!");
            model.addAttribute("item",item);
            return "package.add";
        }
        boolean check=false;
        try{
            String ipClient= Utils.getIpClient(request);
            check=configPackageService.add(item,ipClient).orElse(false);

        }catch (Exception e){
            logger.error("Have a error method add():"+e.getMessage());
        }
        if(!check){
            model.addAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
            model.addAttribute("item",item);
            return "package.add";
        }
        attributes.addFlashAttribute("success", "Thêm mới thành công!");

        return "redirect:/administer/package/list";
    }

    @GetMapping("/edit/{id}")
    @Secured(ConstantAuthor.ConfigPackage.edit)
    public String editView(Model model, @PathVariable("id") Long id) {
        ConfigPackage item=configPackageService.get(id).orElse(null);
        if(item==null) return "404";
        model.addAttribute("item",item);
        return "package.edit";
    }

    @PostMapping("/edit")
    @Secured(ConstantAuthor.ConfigPackage.edit)
    public String edit(Model model, @Valid ConfigPackage item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
        configPackageAddValidator.validate(item, result);
        if (result.hasErrors()) {
            model.addAttribute("item", item);
            return "package.edit";
        }
        if (item.getId() == null) return "404";
        boolean check = false;
        try {
            String ipClient = Utils.getIpClient(request);
            check = configPackageService.edit(item, ipClient).orElse(false);

        } catch (Exception e) {
            logger.error("Have a error ConfigPackage.edit:" + e.getMessage());
        }
        if (!check) {
            model.addAttribute("messageError", "Có lỗi xảy ra. Hãy thử lại sau!");
            model.addAttribute("item",item);
            return "package.edit";
        }
        attributes.addFlashAttribute("success", "Sửa thành công!");

        return "redirect:/administer/package/list";
    }

    @PostMapping("/delete")
    @Secured(ConstantAuthor.ConfigPackage.delete)
    public String packageDelete(Long id, RedirectAttributes attributes,HttpServletRequest request) {
        int check=0;

        try{
            String ipClient= Utils.getIpClient(request);
            check=configPackageService.delete(id,ipClient).orElse(0);
        }catch (Exception e){
            logger.error("have an error packageDelete:"+e.getMessage());
        }
        switch (check){
            case 0:
                attributes.addFlashAttribute("messageError","Có lỗi xảy ra, hãy thử lại sau!");
                return "redirect:/administer/package/list";
            case 2:
                attributes.addFlashAttribute("messageError","Không thể xóa, gói cước đã tồn tại quá 7 ngày từ khi tạo!");
                return "redirect:/administer/package/list";
            case 3:
                attributes.addFlashAttribute("messageError","Không thể xóa, đã có người đăng ký dùng gói cước này!");
                return "redirect:/administer/package/list";
        }

        attributes.addFlashAttribute("success", "Xóa thành công!");
        return "redirect:/administer/package/list";
    }

    @PostMapping("/disable")
    @Secured(ConstantAuthor.ConfigPackage.edit)
    public String packageDisable(Long id, RedirectAttributes attributes,HttpServletRequest request) {
        boolean check=false;
        try{
            String ipClient= Utils.getIpClient(request);
            check=configPackageService.disable(id,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("have an error packageDisable:"+e.getMessage());
        }
        if(!check){
            attributes.addFlashAttribute("messageError","Có lỗi xảy ra, hãy thử lại sau!");
            return "redirect:/administer/package/list";
        }

        attributes.addFlashAttribute("success", "Khóa gói thành công!");
        return "redirect:/administer/package/list";
    }

    @PostMapping("/enable")
    @Secured(ConstantAuthor.ConfigPackage.edit)
    public String packageEnable(Long id, RedirectAttributes attributes,HttpServletRequest request) {
        boolean check=false;
        try{
            String ipClient= Utils.getIpClient(request);
            check=configPackageService.enable(id,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("have an error packageEnable:"+e.getMessage());
        }
        if(!check){
            attributes.addFlashAttribute("messageError","Có lỗi xảy ra, hãy thử lại sau!");
            return "redirect:/administer/package/list";
        }
        attributes.addFlashAttribute("success", "Mở gói thành công!");
        return "redirect:/administer/package/list";
    }
}
