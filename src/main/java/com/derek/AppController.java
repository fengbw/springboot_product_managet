package com.derek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    private ProductService service;

    @RequestMapping("/")
    public String viewHomePage(Model model){
        return listByPage(model, 1, "name", "asc");
    }

    @RequestMapping("/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir){
        Page<Product> page = service.listAll(currentPage, sortField, sortDir);
        Long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Product> listProducts = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        String reverseSortDir = sortDir.equals("asc") ? "des" : "asc";
        model.addAttribute("reverseSortDir", reverseSortDir);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewProductForm(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "new_product";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product){
        service.save(product);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductForm(@PathVariable("id") Long id){
        ModelAndView mav = new ModelAndView("edit_product");
        Product product = service.get(id);
        mav.addObject("product", product);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        service.delete(id);
        return "redirect:/";
    }

    @RequestMapping("/search")
    public ModelAndView searchProduct(@RequestParam String keyword){
        ModelAndView mav = new ModelAndView("search");
        List<Product> res = service.search(keyword);
        mav.addObject("result", res);
        return mav;
    }
}
