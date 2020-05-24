package vn.edu.vinaenter.controller.publics;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.daos.CouponDAO;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Coupon;

@Controller
@RequestMapping("gio-hang")
public class PublicCartController {
    @Autowired
    private CatDAO catDAO;
    @Autowired
    private CouponDAO couponDAO;

    @GetMapping({"{page}", ""})
    public String index(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page) {
        return "public.cart";
    }

    @PostMapping("giam-gia")
    public String coupon(ModelMap modelMap, @RequestParam("coupon") String namecoupon, HttpSession session, RedirectAttributes ra) {
        Coupon coupon = couponDAO.getItem(namecoupon);
        if (coupon == null) {
            ra.addFlashAttribute("msg", "Mã giảm giá không tồn tại!");
            return "redirect:/gio-hang";
        }
        //session.setAttribute("coupon", coupon);
        modelMap.addAttribute("coupon", coupon);
        return "public.cart";
    }

    @ModelAttribute
    public void commonObjects(ModelMap modelMap) {
        List<Category> listCat = catDAO.getItemsbyParent_id(0);
        modelMap.addAttribute("listCat", listCat);
        modelMap.addAttribute("catDAO", catDAO);
    }
}

