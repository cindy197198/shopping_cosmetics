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

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.defines.MessageDefine;
import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Category;

@Controller
@RequestMapping("admincp/cat")
public class AdminCategoryController {
    @Autowired
    private CatDAO catDAO;

    @GetMapping({"{page}", ""})
    public String index(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        int totalRow = catDAO.countItems();
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Category> listCat = catDAO.getItemsPagination(offset);
        modelMap.addAttribute("listCat", listCat);
        modelMap.addAttribute("catDAO", catDAO);
        return "admin.cat.index";
    }

    @GetMapping("add")
    public String add(ModelMap modelMap) {
        List<Category> listCat = catDAO.getItems();
        modelMap.addAttribute("listCat", listCat);
        return "admin.cat.add";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("objCat") Category cat, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "admin.cat.add";
        } else {
            if (!catDAO.checkItem(cat)) {
                ra.addFlashAttribute("msg2", "Tên danh mục đã tồn tại");
                return "redirect:/admincp/cat";
            }
            if (catDAO.addItem(cat) > 0) {
                ra.addFlashAttribute("msg", MessageDefine.MSG_ADD_SUCCESS);
                return "redirect:/admincp/cat";
            }
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/cat";
        }
    }

    @GetMapping("del/{id_cat}")
    public String del(@PathVariable int id_cat, RedirectAttributes ra) {
        if (catDAO.delItem(id_cat) > 0) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_DEL_SUCCESS);
            return "redirect:/admincp/cat";
        }
        ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
        return "redirect:/admincp/cat";
    }

    @GetMapping({"edit/page-{page}/{id_cat}", "edit/{id_cat}"})
    public String edit(@PathVariable int id_cat, ModelMap modelMap, RedirectAttributes ra, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        Category objCat = catDAO.getItem(id_cat);
        if (objCat == null) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/cat";
        }
        List<Category> listCat = catDAO.getItems();
        modelMap.addAttribute("listCat", listCat);
        modelMap.addAttribute("objCat", objCat);
        modelMap.addAttribute("page", page);
        return "admin.cat.edit";
    }

    @PostMapping("edit/page-{page}/{id_cat}")
    public String edit(@Valid @ModelAttribute("objCat") Category cat, BindingResult br, @PathVariable int id_cat, @PathVariable int page, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "admin.cat.edit";
        } else {
            cat.setId(id_cat);
            if (!catDAO.checkItem(cat)) {
                ra.addFlashAttribute("msg2", "Tên danh mục đã tồn tại");
                return "redirect:/admincp/cat";
            }
            if (catDAO.editItem(cat) > 0) {
                ra.addFlashAttribute("msg", MessageDefine.MSG_EDIT_SUCCESS);
                return "redirect:/admincp/cat/" + page;
            }
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/cat/" + page;
        }
    }

    //tìm kiếm
    @PostMapping("tim-kiem")
    public String search(ModelMap modelMap,
                         @RequestParam("keyword") String keyword) {
        int page = 1;
        int totalRow = catDAO.countItemsByKeyWord(keyword);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Category> listCat = catDAO.getItemsPaginationByKeyWord(offset, keyword);
        modelMap.addAttribute("listCat", listCat);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        return "admin.cat.search";
    }

    @GetMapping("tim-kiem/{keyword}/page-{page}")
    public String search(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page,
                         @PathVariable String keyword) {
        if (page == null) page = 1;
        int totalRow = catDAO.countItemsByKeyWord(keyword);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Category> listCat = catDAO.getItemsPaginationByKeyWord(offset, keyword);
        modelMap.addAttribute("listCat", listCat);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        return "admin.cat.search";
    }
}
