package vn.edu.vinaenter.controller.admins;

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

import vn.edu.vinaenter.daos.CouponDAO;
import vn.edu.vinaenter.defines.MessageDefine;
import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Coupon;

@Controller
@RequestMapping("admincp/coupon")
public class AdminCouponController {
    @Autowired
    private CouponDAO couponDAO;

    @GetMapping({"{page}", ""})
    public String index(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        int totalRow = couponDAO.countItems();
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Coupon> listCoupon = couponDAO.getItemsPagination(offset);
        modelMap.addAttribute("listCoupon", listCoupon);
        return "admin.coupon.index";
    }

    @GetMapping("add")
    public String add(ModelMap modelMap) {
        return "admin.coupon.add";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("objCoupon") Coupon coupon, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "admin.coupon.add";
        } else {
            if (!couponDAO.checkItem(coupon)) {
                ra.addFlashAttribute("msg2", "Tên mã giảm giá đã tồn tại");
                return "redirect:/admincp/coupon";
            }
            if (couponDAO.addItem(coupon) > 0) {
                ra.addFlashAttribute("msg", MessageDefine.MSG_ADD_SUCCESS);
                return "redirect:/admincp/coupon";
            }
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/coupon";
        }
    }

    @GetMapping("del/page-{page}/{id_coupon}")
    public String del(@PathVariable int id_coupon, RedirectAttributes ra, @PathVariable(value = "page", required = false) Integer page) {
        if (couponDAO.delItem(id_coupon) > 0) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_DEL_SUCCESS);
            return "redirect:/admincp/coupon/" + page;
        }
        ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
        return "redirect:/admincp/coupon/" + page;
    }

    @GetMapping("edit/page-{page}/{id_coupon}")
    public String edit(@PathVariable int id_coupon, ModelMap modelMap, RedirectAttributes ra, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        Coupon objCoupon = couponDAO.getItem(id_coupon);
        if (objCoupon == null) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/coupon";
        }
        modelMap.addAttribute("objCoupon", objCoupon);
        modelMap.addAttribute("page", page);
        return "admin.coupon.edit";
    }

    @PostMapping("edit/page-{page}/{id_coupon}")
    public String edit(@Valid @ModelAttribute("objCoupon") Coupon coupon, BindingResult br, @PathVariable int id_coupon, @PathVariable int page, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "admin.coupon.edit";
        } else {
            coupon.setId(id_coupon);
            if (couponDAO.editItem(coupon) > 0) {
                ra.addFlashAttribute("msg", MessageDefine.MSG_EDIT_SUCCESS);
                return "redirect:/admincp/coupon/" + page;
            }
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/coupon/" + page;
        }
    }

    //tìm kiếm
    @PostMapping("tim-kiem")
    public String search(ModelMap modelMap,
                         @RequestParam("keyword") String keyword) {
        int page = 1;
        int totalRow = couponDAO.countItemsByKeyWord(keyword);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Coupon> listCoupon = couponDAO.getItemsPaginationByKeyWord(offset, keyword);
        modelMap.addAttribute("listCoupon", listCoupon);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        return "admin.coupon.search";
    }

    @GetMapping("tim-kiem/{keyword}/page-{page}")
    public String search(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page,
                         @PathVariable String keyword) {
        if (page == null) page = 1;
        int totalRow = couponDAO.countItemsByKeyWord(keyword);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Coupon> listCoupon = couponDAO.getItemsPaginationByKeyWord(offset, keyword);
        modelMap.addAttribute("listCoupon", listCoupon);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        return "admin.coupon.search";
    }
}
