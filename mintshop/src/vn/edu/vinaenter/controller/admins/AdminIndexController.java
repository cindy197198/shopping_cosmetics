package vn.edu.vinaenter.controller.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.daos.ProductDAO;
import vn.edu.vinaenter.daos.UserDAO;

@Controller
@RequestMapping("admincp")
public class AdminIndexController {
    @Autowired
    private CatDAO catDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;

    @GetMapping("index")
    public String index(ModelMap modelMap) {
        int numberofcat = catDAO.countItems();
        int numberofUser = userDAO.countItems();
        int numberofProduct = productDAO.countItems();
        modelMap.addAttribute("numberofcat", numberofcat);
        modelMap.addAttribute("numberofUser", numberofUser);
        modelMap.addAttribute("numberofProduct", numberofProduct);
        return "admin.index.index";
    }
}
