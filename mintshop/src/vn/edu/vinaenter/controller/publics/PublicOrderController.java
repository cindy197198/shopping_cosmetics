package vn.edu.vinaenter.controller.publics;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.daos.ContactDAO;
import vn.edu.vinaenter.daos.CouponDAO;
import vn.edu.vinaenter.daos.OrderDAO;
import vn.edu.vinaenter.daos.ProductDAO;
import vn.edu.vinaenter.daos.UserDAO;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Contact;
import vn.edu.vinaenter.models.Coupon;
import vn.edu.vinaenter.models.Detail_order;
import vn.edu.vinaenter.models.User;
import vn.edu.vinaenter.utils.AuthUtil;

@Controller
public class PublicOrderController {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private CatDAO catDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CouponDAO couponDAO;

    @GetMapping({"thanh-toan/{coupon_id}", "thanh-toan"})
    public String checkout(ModelMap modelMap, @RequestParam(value = "msg", required = false) String msg,
                           HttpSession session, Principal principal,
                           @PathVariable(value = "coupon_id", required = false) Integer coupon_id) {
        if (msg != null) modelMap.addAttribute("paypal", "checked");
        if (principal != null) {
            User userLogin = (User) userDAO.getItem(principal.getName());
            session.setAttribute("userLogin", userLogin);
        }
        if (coupon_id != null) {
            Coupon coupon = couponDAO.getItem(coupon_id);
            if (coupon != null) modelMap.addAttribute("coupon", coupon);
        }
        return "public.checkout";
    }

    @PostMapping({"thanh-toan/{coupon_id}", "thanh-toan"})
    public String confirm(ModelMap modelMap, @ModelAttribute("objCustomer") User objCustomer,
                          @RequestParam("payments") int payments, @RequestParam("note") String note,
                          HttpSession session, @RequestParam(value = "msg", required = false) String msg,
                          @RequestParam(value = "otheraddress", required = false) String otheraddress,
                          @RequestParam(value = "otherphone", required = false) String otherphone,
                          @PathVariable(value = "coupon_id", required = false) Integer coupon_id,
                          RedirectAttributes ra) throws IOException {
        if (coupon_id == null) coupon_id = 0;
        if ("".equals(objCustomer.getFullname()) || "".equals(objCustomer.getEmail()) ||
                "".equals(objCustomer.getAddress()) || "".equals(objCustomer.getPhone())) {
            ra.addFlashAttribute("msg", "Vui lòng điền đầy đủ thông tin !");
            ra.addFlashAttribute("objCustomer", objCustomer);
            return "redirect:/thanh-toan/" + coupon_id;
        }
        if (msg == null) msg = "error";
        if (msg.equals("error") && payments == 2) {
            ra.addFlashAttribute("err", "Bạn chưa thanh toán, vui lòng kiểm tra lại !");
            ra.addFlashAttribute("objCustomer", objCustomer);
            return "redirect:/thanh-toan/" + coupon_id;
        }
        if (AuthUtil.checkLogin(session)) {
            User user = (User) session.getAttribute("userLogin");
            objCustomer.setFullname(user.getFullname());
            objCustomer.setEmail(user.getEmail());
            if ("".equals(otheraddress)) objCustomer.setAddress(user.getAddress());
            else objCustomer.setAddress(otheraddress);
            if ("".equals(otherphone)) objCustomer.setPhone(user.getPhone());
            else objCustomer.setPhone(otherphone);
        }
        session.setAttribute("objCustomer", objCustomer);
        session.setAttribute("payment", payments);
        session.setAttribute("note", note);
        if (coupon_id != null) {
            Coupon coupon = couponDAO.getItem(coupon_id);
            if (coupon != null) modelMap.addAttribute("coupon", coupon);
        }
        return "public.confirm";
    }

    @GetMapping({"confirm", "confirm/{coupon_id}"})
    public String confirm(HttpSession session, @PathVariable(value = "coupon_id", required = false) Integer coupon_id) throws IOException {
        User objCustomer = (User) session.getAttribute("objCustomer");
        Coupon coupon = null;
        if (coupon_id != null) {
            coupon = couponDAO.getItem(coupon_id);
        }
        int coupon_value = 0;
        if (coupon != null) coupon_value = coupon.getValue();
        int payment = (int) session.getAttribute("payment");
        int payment_status = 0;
        if (payment == 2) {
            payment_status = 1;
        }
        String note = (String) session.getAttribute("note");
        if (AuthUtil.checkLogin(session)) {
            User user = (User) session.getAttribute("userLogin");
            if (user.getAddress().equals(objCustomer.getAddress())) {
                orderDAO.addItem(user.getId(), payment, note, payment_status, coupon_value);
            } else {
                orderDAO.addItem(user.getId(), payment, note, objCustomer.getAddress(), objCustomer.getPhone(), payment_status, coupon_value);
            }
        } else {
            orderDAO.addItem(objCustomer, payment, note, payment_status, coupon_value);
        }
        //thêm dl vào bảng Detail_order
        int order_id = orderDAO.getMaxId();
        ArrayList<Detail_order> cart = (ArrayList<Detail_order>) session.getAttribute("cart");
        for (Detail_order item : cart) {
            orderDAO.addDetail_item(item, order_id);
            //trừ số lượng product
            productDAO.decAmount(item.getProduct(), item.getQuantity());
        }
        //trừ mã giảm giá
        if (coupon != null) couponDAO.decQuantity(coupon);
        //xoá session
        session.removeAttribute("cart");
        return "public.order-success";
    }

    @PostMapping("saveCustomer")
    public void save(HttpSession session, @RequestParam("afullname") String fullname,
                     @RequestParam("aemail") String email,
                     @RequestParam("aaddress") String address,
                     @RequestParam("aphone") String phone) {
        User objCustomer = new User(0, "", fullname, "", phone, email, address, null, 0);
        session.setAttribute("objCustomer", objCustomer);
    }

    @ModelAttribute
    public void commonObjects(ModelMap modelMap) {
        List<Category> listCat = catDAO.getItemsbyParent_id(0);
        modelMap.addAttribute("listCat", listCat);
        modelMap.addAttribute("catDAO", catDAO);
    }
}
