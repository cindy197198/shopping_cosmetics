package vn.edu.vinaenter.controller.admins;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vinaenter.daos.ContactDAO;
import vn.edu.vinaenter.defines.MessageDefine;
import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Contact;

@Controller
@RequestMapping("admincp/contact")
public class AdminContactController {
    @Autowired
    private ContactDAO contactDAO;

    @GetMapping({"{page}", ""})
    public String index(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        int totalRow = contactDAO.countItems();
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Contact> listContact = contactDAO.getItemsPagination(offset);
        modelMap.addAttribute("listContact", listContact);
        return "admin.contact.index";
    }

    @GetMapping("del/page-{page}/{id_contact}")
    public String del(@PathVariable int id_contact, @PathVariable int page, RedirectAttributes ra) {
        if (contactDAO.delItem(id_contact) > 0) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_DEL_SUCCESS);
            return "redirect:/admincp/contact/" + page;
        }
        ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
        return "redirect:/admincp/contact/" + page;
    }

    @GetMapping("view/page-{page}/{id_contact}")
    public String view(ModelMap modelMap, @PathVariable int id_contact, @PathVariable int page, RedirectAttributes ra) {
        Contact objContact = contactDAO.getItem(id_contact);
        if (objContact == null) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/contact/" + page;
        }
        modelMap.addAttribute("objContact", objContact);
        modelMap.addAttribute("page", page);
        return "admin.contact.detail";
    }

    //tìm kiếm
    @PostMapping("tim-kiem")
    public String search(ModelMap modelMap,
                         @RequestParam("keyword") String keyword) {
        int page = 1;
        int totalRow = contactDAO.countItemsByKeyWord(keyword);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Contact> listContact = contactDAO.getItemsPaginationByKeyWord(offset, keyword);
        modelMap.addAttribute("listContact", listContact);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        return "admin.contact.search";
    }

    @GetMapping("tim-kiem/{keyword}/page-{page}")
    public String search(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page,
                         @PathVariable String keyword) {
        if (page == null) page = 1;
        int totalRow = contactDAO.countItemsByKeyWord(keyword);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Contact> listContact = contactDAO.getItemsPaginationByKeyWord(offset, keyword);
        modelMap.addAttribute("listContact", listContact);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        return "admin.contact.search";
    }
}
