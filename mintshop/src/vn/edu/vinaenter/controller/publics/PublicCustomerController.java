package vn.edu.vinaenter.controller.publics;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.daos.OrderDAO;
import vn.edu.vinaenter.daos.UserDAO;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Detail_order;
import vn.edu.vinaenter.models.Order;
import vn.edu.vinaenter.models.User;

@Controller
@RequestMapping("my-account")
public class PublicCustomerController {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private CatDAO catDAO;
    @Autowired
    private UserDAO userDAO;

    @GetMapping()
    public String index(ModelMap modelMap, HttpSession session, Principal principal) {
        User userLogin = (User) userDAO.getItem(principal.getName());
        session.setAttribute("userLogin", userLogin);
        return "public.account.info";
    }

    @GetMapping("order")
    public String order(ModelMap modelMap, HttpSession session) {
        User user = (User) session.getAttribute("userLogin");
        ArrayList<Order> listOrder = (ArrayList<Order>) orderDAO.getItemByUser(user);
        for (Order itemOrder : listOrder) {
            List<Detail_order> listDetail = orderDAO.getListOrderDetail(itemOrder.getId());
            itemOrder.setListProduct(listDetail);
        }
        modelMap.addAttribute("listOrder", listOrder);
        return "public.account.order";
    }

    @ModelAttribute
    public void commonObjects(ModelMap modelMap) {
        List<Category> listCat = catDAO.getItemsbyParent_id(0);
        modelMap.addAttribute("listCat", listCat);
        modelMap.addAttribute("catDAO", catDAO);
    }
}

