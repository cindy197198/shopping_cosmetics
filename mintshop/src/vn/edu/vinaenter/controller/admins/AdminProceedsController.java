package vn.edu.vinaenter.controller.admins;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.daos.OrderDAO;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Detail_order;
import vn.edu.vinaenter.models.Order;
import vn.edu.vinaenter.models.Product;

@Controller
@RequestMapping("admincp/proceeds")
public class AdminProceedsController {
    @Autowired
    private CatDAO catDAO;
    @Autowired
    private OrderDAO orderDAO;

    @ModelAttribute
    public void commonObjects(ModelMap modelMap) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        String dateFormat = formatter.format(date);
        modelMap.addAttribute("dateFormat1", dateFormat);
        formatter = new SimpleDateFormat("yyyy");
        modelMap.addAttribute("dateFormat2", formatter.format(date));
    }

    @GetMapping
    public String index(ModelMap modelMap) {
        //hiển thị doanh thu ngày hiện tai
        Date date = new Date();
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Date da = formatter.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        List<Order> list = (List<Order>) orderDAO.getItemInDay(c);
        double sum = 0;
        List<Detail_order> listProduct = new ArrayList<>();

        //tính tổng tiền của các đơn hàng
        for (Order item : list) {
            List<Detail_order> listDetail = orderDAO.getListOrderDetail(item.getId());
            double total = 0;
            for (Detail_order obj : listDetail) {
                if (obj.getProduct().getNewprice() != 0) total += obj.getProduct().getNewprice() * obj.getQuantity();
                else total += obj.getProduct().getPrice() * obj.getQuantity();
                boolean check = false;
                if (listProduct.size() > 0) {
                    for (Detail_order i : listProduct) {
                        if (i.getProduct().getId() == obj.getProduct().getId()) {
                            i.setQuantity(i.getQuantity() + obj.getQuantity());
                            check = true;
                        }
                    }
                    if (!check) listProduct.add(obj);
                } else listProduct.add(obj);
            }
            if (item.getCoupon_value() > 0)
                total = (double) Math.round((total * (1 - item.getCoupon_value() * 1.0 / 100)) * 100) / 100;
            sum += total;
        }
        modelMap.addAttribute("sum", sum);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        modelMap.addAttribute("date", formatter.format(date));
        modelMap.addAttribute("listProduct", listProduct);
        return "admin.proceeds.index";
    }

    @PostMapping
    public String month(ModelMap modelMap, @RequestParam("status") int status, RedirectAttributes ra,
                        @RequestParam("date") String date,
                        @RequestParam("month") String month,
                        @RequestParam("year") Integer year) throws ParseException {
        switch (status) {
            case 1: {
                if ("".equals(date)) {
                    ra.addFlashAttribute("msg", "Bạn chưa chọn ngày!");
                    return "redirect:/admincp/proceeds";
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date da = formatter.parse(date);
                Calendar c = Calendar.getInstance();
                c.setTime(da);
                List<Order> list = (List<Order>) orderDAO.getItemInDay(c);
                double sum = 0;
                List<Detail_order> listProduct = new ArrayList<>();

                //tính tổng tiền của các đơn hàng
                for (Order item : list) {
                    List<Detail_order> listDetail = orderDAO.getListOrderDetail(item.getId());
                    double total = 0;
                    for (Detail_order obj : listDetail) {
                        if (obj.getProduct().getNewprice() != 0)
                            total += obj.getProduct().getNewprice() * obj.getQuantity();
                        else total += obj.getProduct().getPrice() * obj.getQuantity();
                        boolean check = false;
                        if (listProduct.size() > 0) {
                            for (Detail_order i : listProduct) {
                                if (i.getProduct().getId() == obj.getProduct().getId()) {
                                    i.setQuantity(i.getQuantity() + obj.getQuantity());
                                    check = true;
                                }
                            }
                            if (!check) listProduct.add(obj);
                        } else listProduct.add(obj);
                    }
                    if (item.getCoupon_value() > 0)
                        total = (double) Math.round((total * (1 - item.getCoupon_value() * 1.0 / 100)) * 100) / 100;
                    sum += total;
                }
                modelMap.addAttribute("sum", sum);
                formatter = new SimpleDateFormat("dd-MM-yyyy");
                modelMap.addAttribute("date", formatter.format(da));
                modelMap.addAttribute("listProduct", listProduct);
            }
            break;
            case 2: {
                if ("".equals(month)) {
                    ra.addFlashAttribute("msg", "Bạn chưa chọn tháng!");
                    return "redirect:/admincp/proceeds";
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
                Date da = formatter.parse(month);
                Calendar c = Calendar.getInstance();
                c.setTime(da);
                List<Order> list = (List<Order>) orderDAO.getItemInMonth(c);
                double sum = 0;
                List<Detail_order> listProduct = new ArrayList<>();

                //tính tổng tiền của các đơn hàng
                for (Order item : list) {
                    List<Detail_order> listDetail = orderDAO.getListOrderDetail(item.getId());
                    double total = 0;
                    for (Detail_order obj : listDetail) {
                        if (obj.getProduct().getNewprice() != 0)
                            total += obj.getProduct().getNewprice() * obj.getQuantity();
                        else total += obj.getProduct().getPrice() * obj.getQuantity();
                        boolean check = false;
                        if (listProduct.size() > 0) {
                            for (Detail_order i : listProduct) {
                                if (i.getProduct().getId() == obj.getProduct().getId()) {
                                    i.setQuantity(i.getQuantity() + obj.getQuantity());
                                    check = true;
                                }
                            }
                            if (!check) listProduct.add(obj);
                        } else listProduct.add(obj);
                    }
                    if (item.getCoupon_value() > 0)
                        total = (double) Math.round((total * (1 - item.getCoupon_value() * 1.0 / 100)) * 100) / 100;
                    sum += total;
                }
                modelMap.addAttribute("sum", sum);
                formatter = new SimpleDateFormat("MM-yyyy");
                modelMap.addAttribute("date", formatter.format(da));
                modelMap.addAttribute("listProduct", listProduct);
            }
            break;
            case 3: {
                if (year == null) {
                    ra.addFlashAttribute("msg", "Bạn chưa chọn năm!");
                    return "redirect:/admincp/proceeds";
                }
                List<Order> list = (List<Order>) orderDAO.getItemInYear(year);
                double sum = 0;
                List<Detail_order> listProduct = new ArrayList<>();

                //tính tổng tiền của các đơn hàng
                for (Order item : list) {
                    List<Detail_order> listDetail = orderDAO.getListOrderDetail(item.getId());
                    double total = 0;
                    for (Detail_order obj : listDetail) {
                        if (obj.getProduct().getNewprice() != 0)
                            total += obj.getProduct().getNewprice() * obj.getQuantity();
                        else total += obj.getProduct().getPrice() * obj.getQuantity();
                        boolean check = false;
                        if (listProduct.size() > 0) {
                            for (Detail_order i : listProduct) {
                                if (i.getProduct().getId() == obj.getProduct().getId()) {
                                    i.setQuantity(i.getQuantity() + obj.getQuantity());
                                    check = true;
                                }
                            }
                            if (!check) listProduct.add(obj);
                        } else listProduct.add(obj);
                    }
                    if (item.getCoupon_value() > 0)
                        total = (double) Math.round((total * (1 - item.getCoupon_value() * 1.0 / 100)) * 100) / 100;
                    sum += total;
                }
                modelMap.addAttribute("sum", sum);
                modelMap.addAttribute("date", year);
                modelMap.addAttribute("listProduct", listProduct);
            }
            default:
                break;
        }
        return "admin.proceeds.index";
    }
}
