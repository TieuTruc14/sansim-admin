package com.osp.web.controller.content;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.modelCustomer.Article;
import com.osp.modelCustomer.Category;
import com.osp.validator.content.ArticleAddValidator;
import com.osp.validator.content.CategoryAddValidator;
import com.osp.validator.content.CategoryEditValidator;
import com.osp.web.service.content.ArticleService;
import com.osp.web.service.content.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Admin on 2/26/2018.
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    private Logger logger= LogManager.getLogger(ContentController.class);
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryAddValidator categoryAddValidator;
    @Autowired
    CategoryEditValidator categoryEditValidator;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleAddValidator articleAddValidator;
    @Value("${image_folder}")
    private String imageFolder;
    @Value("${image_folder_get}")
    private String imageFolderGet;

    @GetMapping("/category/list")
    @Secured(ConstantAuthor.Category.view)
    public String categoryList(Model model){
        return "content.category.list";
    }

    @GetMapping("/category/list/search")
    @Secured(ConstantAuthor.Category.view)
    public ResponseEntity<PagingResult> categoryListSearch(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, String name){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=categoryService.page(page,name).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error on method categoryListSearch:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/category/list/all")
    public ResponseEntity<List<Category>> categoryList(){
        List<Category> list=categoryService.list().orElse(new ArrayList<>());
        return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
    }

    @GetMapping("/category/add")
    @Secured(ConstantAuthor.Category.add)
    public String categoryAdd(){
        return "content.category.add";
    }

    @PostMapping("/category/add")
    @Secured(ConstantAuthor.Category.add)
    public String categoryAdd(Model model, @Valid Category item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        categoryAddValidator.validate(item,result);
        if(result.hasErrors()){
            model.addAttribute("item",item);
            return "content.category.add";
        }
        boolean check=false;
        try{
            String ipClient= Utils.getIpClient(request);
            check=categoryService.add(item,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("Have a error method categoryAdd():"+e.getMessage());
        }
        if(!check){
            model.addAttribute("item",item);
            model.addAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
            return "content.category.add";
        }
        attributes.addFlashAttribute("success", "Thêm mới thành công!");
        return "redirect:/content/category/list";
    }

    @GetMapping("/category/edit/{id}")
    @Secured(ConstantAuthor.Category.edit)
    public String categoryEdit(Model model, @PathVariable("id") Integer id){
        Category item=categoryService.get(id).orElse(null);
        if(item==null) return "404";
        model.addAttribute("item",item);
        return "content.category.edit";
    }

    @PostMapping("/category/edit")
    @Secured(ConstantAuthor.Category.edit)
    public String categoryEdit(Model model, @Valid Category item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        categoryEditValidator.validate(item,result);
        if(result.hasErrors()){
            model.addAttribute("item",item);
            return "content.category.edit";
        }
        boolean check=false;
        try{
            String ipClient= Utils.getIpClient(request);
            check=categoryService.edit(item,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("Have a error method categoryEdit():"+e.getMessage());
        }
        if(!check){
            model.addAttribute("item",item);
            model.addAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
            return "content.category.edit";
        }
        attributes.addFlashAttribute("success", "Sửa thành công!");
        return "redirect:/content/category/list";
    }

    @PostMapping("/category/delete")
    @Secured(ConstantAuthor.Category.delete)
    public String categoryDelete(RedirectAttributes attributes,Integer id,HttpServletRequest request){
        if(id==null) return "404";
        boolean check=false;
        try{
            String ip=Utils.getIpClient(request);
            check=categoryService.delete(id,ip).orElse(false);
        }catch (Exception e){
            logger.error("Have a error method categoryDelete():"+e.getMessage());
        }
        if(!check){
            attributes.addFlashAttribute("messageError","Có lỗi xảy ra, hãy thử lại sau!");
            return"redirect:/content/category/list";
        }
        attributes.addFlashAttribute("success","Xóa chuyên mục thành công!");
        return "redirect:/content/category/list";
    }

    /*ARTICLE*/
    @GetMapping("/article/list")
    @Secured(ConstantAuthor.Article.view)
    public String articleList(){
        return "content.article.list";
    }

    @GetMapping("/article/list/search")
    @Secured(ConstantAuthor.Article.view)
    public ResponseEntity<PagingResult> articleListSearch(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, String name,Integer categoryId){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        try{
            page=articleService.page(page,name,categoryId).orElse(new PagingResult());
        }catch (Exception e){
            logger.error("Have an error on method articleListSearch:"+e.getMessage());
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/article/add")
    @Secured(ConstantAuthor.Article.add)
    public String articleAdd(Model model){
        List<Category> list=categoryService.list().orElse(new ArrayList<>());
        if(list!=null && list.size()>0){
            model.addAttribute("categories",list);
        }else{
            return "404";
        }
        return "content.article.add";
    }

    @PostMapping("/article/add")
    @Secured(ConstantAuthor.Article.add)
    public String articleAdd(Model model, @Valid Article item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        articleAddValidator.validate(item,result);
        if(result.hasErrors()){
            List<Category> list=categoryService.list().orElse(new ArrayList<>());
            if(list!=null && list.size()>0){
                model.addAttribute("categories",list);
            }else{
                return "404";
            }
            model.addAttribute("item",item);
            return "content.article.add";
        }
        boolean check=false;
        try{
            MultipartFile file = item.getFile();
            if (!file.isEmpty()) {
                try {
                    if (file.getSize() > 0) {
                        String img_dir = imageFolder;
                        java.io.File dirs = new java.io.File(img_dir);
                        if (!dirs.exists()) {
                            dirs.mkdirs();
                        }
                        java.io.File fout = null;
                        String fileName = "";
                        int count=0;
                        do {
                            count++;
                            fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
                            fout = new java.io.File(img_dir, fileName);
                        } while (fout.exists() && count<10);
                        FileOutputStream outputStream = new FileOutputStream(fout);
                        outputStream.write(file.getBytes());
                        outputStream.close();
                        item.setImageUrl(fileName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String ipClient= Utils.getIpClient(request);
            check=articleService.add(item,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("Have a error method articleAdd():"+e.getMessage());
        }
        if(!check){
            model.addAttribute("item",item);
            model.addAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
            List<Category> list=categoryService.list().orElse(new ArrayList<>());
            if(list!=null && list.size()>0){
                model.addAttribute("categories",list);
            }else{
                return "404";
            }
            return "content.article.add";
        }
        attributes.addFlashAttribute("success", "Thêm thành công!");
        return "redirect:/content/article/list";
    }


    @GetMapping("/article/edit/{id}")
    @Secured(ConstantAuthor.Article.edit)
    public String articleEdit(Model model, @PathVariable("id") Integer id){
        Article item=articleService.get(id).orElse(null);
        List<Category> list=categoryService.list().orElse(new ArrayList<>());
        if(list!=null && list.size()>0 && item!=null){
            model.addAttribute("categories",list);
            model.addAttribute("item",item);
            model.addAttribute("urlImage",imageFolderGet);
        }else{
            return "404";
        }
        return "content.article.edit";
    }

    @PostMapping("/article/edit")
    @Secured(ConstantAuthor.Article.edit)
    public String articleEdit(Model model, @Valid Article item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
        articleAddValidator.validate(item,result);
        if(result.hasErrors()){
            model.addAttribute("item",item);
            model.addAttribute("urlImage",imageFolderGet);
            model.addAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
            List<Category> list=categoryService.list().orElse(new ArrayList<>());
            if(list!=null && list.size()>0){
                model.addAttribute("categories",list);
            }else{
                return "404";
            }
            return "content.article.edit";
        }
        boolean check=false;
        try{
            MultipartFile file = item.getFile();
            if (!file.isEmpty()) {
                try {
                    if (file.getSize() > 0) {
                        String img_dir = imageFolder;
                        java.io.File dirs = new java.io.File(img_dir);
                        if (!dirs.exists()) {
                            dirs.mkdirs();
                        }
                        java.io.File fout = null;
                        String fileName = "";
                        int count=0;
                        do {
                            count++;
                            fileName = UUID.randomUUID().toString()+ file.getOriginalFilename();
                            fout = new java.io.File(img_dir, fileName);
                        } while (fout.exists() && count<10);
                        FileOutputStream outputStream = new FileOutputStream(fout);
                        outputStream.write(file.getBytes());
                        outputStream.close();
                        item.setImageUrl(fileName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String ipClient= Utils.getIpClient(request);
            check=articleService.edit(item,ipClient).orElse(false);
        }catch (Exception e){
            logger.error("Have a error method articleEdit():"+e.getMessage());
        }
        if(!check){
            model.addAttribute("item",item);
            model.addAttribute("urlImage",imageFolderGet);
            model.addAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
            List<Category> list=categoryService.list().orElse(new ArrayList<>());
            if(list!=null && list.size()>0){
                model.addAttribute("categories",list);
            }else{
                return "404";
            }
            return "content.article.edit";
        }
        attributes.addFlashAttribute("success", "Sửa thành công!");
        return "redirect:/content/article/list";
    }

    @PostMapping("/article/delete")
    @Secured(ConstantAuthor.Article.delete)
    public String articleDelete(RedirectAttributes attributes,Integer id,HttpServletRequest request){
        if(id==null) return "404";
        boolean check=false;
        try{
            String ip=Utils.getIpClient(request);
            check=articleService.delete(id,ip).orElse(false);
        }catch (Exception e){
            logger.error("Have a error method articleDelete():"+e.getMessage());
        }
        if(!check){
            attributes.addFlashAttribute("messageError","Có lỗi xảy ra, hãy thử lại sau!");
            return"redirect:/content/article/list";
        }
        attributes.addFlashAttribute("success","Xóa bài viết thành công!");
        return "redirect:/content/article/list";
    }
}
