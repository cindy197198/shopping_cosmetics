package vn.edu.vinaenter.controller.admins;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.daos.OrderDAO;
import vn.edu.vinaenter.defines.MessageDefine;
import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Contact;
import vn.edu.vinaenter.models.Detail_order;
import vn.edu.vinaenter.models.Order;
import vn.edu.vinaenter.models.Order_status;
import vn.edu.vinaenter.models.Product;
import vn.edu.vinaenter.models.User;

@Controller
@RequestMapping("admincp/order")
public class AdminOrderController {
    @Autowired
    private OrderDAO orderDAO;

    @GetMapping({"{page}", ""})
    public String index(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        int totalRow = orderDAO.countItems();
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        page = page > sumPage ? sumPage : page;
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Order> listOrder = orderDAO.getItemsPagination(offset);
        //tính tổng tiền của các đơn hàng
        for (Order item : listOrder) {
            List<Detail_order> listDetail = orderDAO.getListOrderDetail(item.getId());
            double total = 0;
            for (Detail_order obj : listDetail) {
                if (obj.getProduct().getNewprice() != 0) total += obj.getProduct().getNewprice() * obj.getQuantity();
                else total += obj.getProduct().getPrice() * obj.getQuantity();
            }
            if (item.getCoupon_value() > 0)
                item.setTotal((double) Math.round((total * (1 - item.getCoupon_value() * 1.0 / 100)) * 100) / 100);
            else item.setTotal(total);

        }
        modelMap.addAttribute("listOrder", listOrder);
        return "admin.order.index";
    }

    @GetMapping("view/page-{page}/{id_order}")
    public String view(ModelMap modelMap, @PathVariable int id_order, @PathVariable int page, RedirectAttributes ra) {
        List<Order_status> listStatus = new ArrayList<>();
        listStatus.add(new Order_status(1, "Đang chờ xác nhận"));
        listStatus.add(new Order_status(2, "Đã gửi"));
        listStatus.add(new Order_status(3, "Hoàn tất"));
        listStatus.add(new Order_status(4, "Từ chối"));
        Order objOrder = orderDAO.getItem(id_order);
        if (objOrder == null) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/order/" + page;
        }
        List<Detail_order> listDetail = orderDAO.getListOrderDetail(id_order);
        objOrder.setListProduct(listDetail);
        modelMap.addAttribute("listStatus", listStatus);
        modelMap.addAttribute("objOrder", objOrder);
        modelMap.addAttribute("page", page);
        return "admin.order.detail";
    }

    @PostMapping("view/page-{page}/{id_order}")
    public String edit(ModelMap modelMap, @PathVariable int id_order, @PathVariable int page, @ModelAttribute("order") Order objOrder
            , RedirectAttributes ra) {
        objOrder.setId(id_order);
        orderDAO.editItem(objOrder);
        return "redirect:/admincp/order/view/page-" + page + "/" + id_order;
    }

    @GetMapping("del/page-{page}/{id_order}")
    public String del(@PathVariable int id_order, @PathVariable int page, RedirectAttributes ra) {
        if (orderDAO.delItem(id_order) > 0) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_DEL_SUCCESS);
            return "redirect:/admincp/order/" + page;
        }
        ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
        return "redirect:/admincp/order/" + page;
    }

    //tìm kiếm
    @PostMapping("tim-kiem")
    public String search(ModelMap modelMap, @RequestParam("property-search") int property_search,
                         @RequestParam("keyword") String keyword) {
        int page = 1;
        int totalRow = orderDAO.countItemsByKeyWord(keyword, property_search);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Order> listOrder = orderDAO.getItemsPaginationByKeyWord(offset, keyword, property_search);
        //tính tổng tiền của các đơn hàng
        for (Order item : listOrder) {
            List<Detail_order> listDetail = orderDAO.getListOrderDetail(item.getId());
            double total = 0;
            for (Detail_order obj : listDetail) {
                if (obj.getProduct().getNewprice() != 0) total += obj.getProduct().getNewprice() * obj.getQuantity();
                else total += obj.getProduct().getPrice() * obj.getQuantity();
            }
            if (item.getCoupon_value() > 0)
                item.setTotal((double) Math.round((total * (1 - item.getCoupon_value() * 1.0 / 100)) * 100) / 100);
            else item.setTotal(total);

        }
        modelMap.addAttribute("listOrder", listOrder);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        modelMap.addAttribute("property_search", property_search);
        return "admin.order.search";
    }

    @GetMapping("tim-kiem/{property_search}/{keyword}/page-{page}")
    public String search(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page,
                         @PathVariable String keyword, @PathVariable int property_search) {
        if (page == null) page = 1;
        int totalRow = orderDAO.countItemsByKeyWord(keyword, property_search);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Order> listOrder = orderDAO.getItemsPaginationByKeyWord(offset, keyword, property_search);
        //tính tổng tiền của các đơn hàng
        for (Order item : listOrder) {
            List<Detail_order> listDetail = orderDAO.getListOrderDetail(item.getId());
            double total = 0;
            for (Detail_order obj : listDetail) {
                if (obj.getProduct().getNewprice() != 0) total += obj.getProduct().getNewprice() * obj.getQuantity();
                else total += obj.getProduct().getPrice() * obj.getQuantity();
            }
            if (item.getCoupon_value() > 0)
                item.setTotal((double) Math.round((total * (1 - item.getCoupon_value() * 1.0 / 100)) * 100) / 100);
            else item.setTotal(total);

        }
        modelMap.addAttribute("listOrder", listOrder);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        modelMap.addAttribute("property_search", property_search);
        return "admin.order.search";
    }

}
