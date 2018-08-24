package com.osp.web.controller.system;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.Authority;
import com.osp.model.Group;
import com.osp.model.GroupAuthority;
import com.osp.model.view.AuthorityView;
import com.osp.model.view.GroupView;
import com.osp.validator.group.GroupViewAddValidator;
import com.osp.web.service.group.GroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
 * Created by Admin on 12/27/2017.
 */
@Controller
@RequestMapping("/system/group")
public class GroupController {
    private Logger logger= LogManager.getLogger(GroupController.class);
    @Autowired
    GroupService groupService;

    @Autowired
    GroupViewAddValidator groupViewAddValidator;


    @GetMapping("/list")
    @Secured(ConstantAuthor.Group.view)
    public String list(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
                           @RequestParam(value = "filterName", required = false, defaultValue = "") String filterName) {
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        page= groupService.page(Utils.trim(filterName),page).orElse(new PagingResult());
        model.addAttribute("page",page);
        model.addAttribute("filterName",filterName);
        return "group.list";
    }

    @GetMapping("/add")
    @Secured(ConstantAuthor.Group.add)
    public String groupAdd(Model model) {
        List<Authority> items=groupService.loadAllAuthority().orElse(new ArrayList<>());
        if(items==null && items.size()==0){
            return "404";
        }
        loadAuthorityToModel(model,items);
        GroupView item=new GroupView();
        model.addAttribute("item",item);

        return "group.add";
    }

    public void loadAuthorityToModel(Model model,List<Authority> items){
        List<AuthorityView> list=new ArrayList<>();
        List<Authority> childrens=new ArrayList<>();
        for(Authority item:items){
            if(item.getFid()==0){
                AuthorityView au=new AuthorityView();
                au.setParent(item);
                list.add(au);
            }
        }

        for(AuthorityView item:list){
            childrens=new ArrayList<>();
            for(Authority authority:items){
                if(authority.getFid()==item.getParent().getId()){
                    childrens.add(authority);
                }
            }
            item.setChildrens(childrens);
        }
        model.addAttribute("groups",list);
    }
    @PostMapping("/add")
    @Secured(ConstantAuthor.Group.add)
    public String groupAddSave(Model model, @Valid GroupView item, BindingResult result, RedirectAttributes attributes) {
        groupViewAddValidator.validate(item,result);
        try{
            if(!result.hasErrors() && groupService.saveGroupView(item).orElse(false)){
                attributes.addFlashAttribute("success","Thêm đối tượng thành công!");
                return "redirect:/system/group/list";
            }
        }catch (Exception e){
            logger.error("Have error GroupController.groupAddSave: "+e.getMessage());
        }
        /*Reload when error*/
        List<Authority> items=groupService.loadAllAuthority().orElse(new ArrayList<>());
        if(items==null && items.size()==0){
            return "404";
        }
        loadAuthorityToModel(model,items);
        model.addAttribute("messageError","Thêm đối tượng thất bại!");
        model.addAttribute("item",item);
        return "group.add";

    }

    @GetMapping("/edit/{id}")
    @Secured(ConstantAuthor.Group.edit)
    public String groupEdit(Model model,@PathVariable("id") Integer id) {
        if(id==null || id.intValue()==0) return "404";
        GroupView item=groupService.getGroupView(id).orElse(null);
        if(item==null) return "404";
        List<Authority> items=groupService.loadAllAuthority().orElse(new ArrayList<>());
        if(items==null && items.size()==0)return "404";

        loadAuthorityToModel(model,items);
        model.addAttribute("item",item);
        return "group.edit";
    }


    @Secured(ConstantAuthor.Group.edit)@PostMapping("/edit")
    public String groupEditSave(Model model, @Valid GroupView item, BindingResult result, RedirectAttributes attributes) {
        if(item.getId()==null|| item.getId().intValue()==0) return "404";
        groupViewAddValidator.validate(item,result);
        try{
            if(!result.hasErrors() && groupService.editGroupView(item).orElse(false)){
                attributes.addFlashAttribute("success","Sửa nhóm quyền thành công!");
                return "redirect:/system/group/list";
            }
        }catch (Exception e){
            logger.error("Have error GroupController.groupEditSave: "+e.getMessage());
        }
        List<Authority> items=groupService.loadAllAuthority().orElse(new ArrayList<>());
        if(items==null && items.size()==0) return "404";
        loadAuthorityToModel(model,items);
        model.addAttribute("messageError","Thêm đối tượng thất bại!");
        model.addAttribute("item",item);
        return "group.edit";
    }

    @PostMapping("/delete")
    @Secured(ConstantAuthor.Group.delete)
    public String GroupDelete(Integer id, RedirectAttributes attributes,HttpServletRequest request) {
        int check=0;
        try{
            String ipClient= Utils.getIpClient(request);
            check=groupService.deleteGroup(id,ipClient).orElse(0);
        }catch (Exception e){
            logger.error("have an error UserDelete:"+e.getMessage());
        }
        switch (check){
            case 0:
                attributes.addFlashAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
                return "redirect:/system/group/list";
            case 2:
                attributes.addFlashAttribute("messageError","Thất bại. Nhóm quyền này đã phân cho người dùng!");
                return "redirect:/system/group/list";
            case 1:
                attributes.addFlashAttribute("success", "Xóa nhóm quyền thành công!");
                return "redirect:/system/group/list";
            default:
                attributes.addFlashAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
                return "redirect:/system/group/list";
        }

    }

}
