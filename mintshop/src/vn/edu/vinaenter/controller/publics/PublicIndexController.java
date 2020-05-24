package vn.edu.vinaenter.controller.publics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.daos.ProductDAO;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Product;

@Controller
@RequestMapping("/")
public class PublicIndexController {
    @Autowired
    private CatDAO catDAO;
    @Autowired
    private ProductDAO productDAO;

    @GetMapping
    public String index(ModelMap modelMap) {
        return "public.index";
    }

    @ModelAttribute
    public void commonObjects(ModelMap modelMap) {
        List<Category> listCat = catDAO.getItemsbyParent_id(0);
        modelMap.addAttribute("listCat", listCat);
        List<Product> listDeal = productDAO.getItemDeal(0);
        modelMap.addAttribute("listDeal", listDeal);
        modelMap.addAttribute("catDAO", catDAO);
        modelMap.addAttribute("productDAO", productDAO);
    }
}
