package vn.edu.vinaenter.controller.admins;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import vn.edu.vinaenter.daos.RoleDAO;
import vn.edu.vinaenter.daos.UserDAO;
import vn.edu.vinaenter.defines.MessageDefine;
import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Role;
import vn.edu.vinaenter.models.User;

@Controller
@RequestMapping("admincp/user")
public class AdminUserController {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private vn.edu.vinaenter.validator.PhoneValidator phoneValidator = new vn.edu.vinaenter.validator.PhoneValidator();

    @GetMapping({"{page}", ""})
    public String index(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null)
            page = 1;
        int totalRow = userDAO.countItems();
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<User> listUser = userDAO.getItemsPagination(offset);
        modelMap.addAttribute("listUsers", listUser);
        return "admin.user.index";
    }

    @GetMapping("active/{id}")
    public void active(@PathVariable int id) {
        User itemUser = userDAO.getItem(id);
        if (itemUser != null) {
            if (itemUser.getActive() == 1) {
                userDAO.active(id, 0);
            } else {
                userDAO.active(id, 1);
            }
        }
    }

    @GetMapping("add")
    public String add(ModelMap modelMap) {
        List<Role> listRole = roleDAO.getItems();
        modelMap.addAttribute("listRole", listRole);
        return "admin.user.add";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("objUser") User objUser, BindingResult br, ModelMap modelMap,
                      RedirectAttributes ra) {
        phoneValidator.validate(objUser, br);
        if (br.hasErrors()) {
            List<Role> listRole = roleDAO.getItems();
            modelMap.addAttribute("listRole", listRole);
            return "admin.user.add";
        } else {
            if ("".equals(objUser.getPassword())) {
                ra.addFlashAttribute("msg", "Mật khẩu không được rỗng");
                ra.addFlashAttribute("objUser", objUser);
                return "redirect:/admincp/user/add";
            }
            if (!userDAO.checkItem(objUser)) {
                ra.addFlashAttribute("msg", "Username đã tồn tại");
                ra.addFlashAttribute("objUser", objUser);
                return "redirect:/admincp/user/add";
            }
            // objUser.setPassword(passwordEncoder.encode(objUser.getPassword()));
            if (userDAO.addItem(objUser) > 0) {
                ra.addFlashAttribute("msg", MessageDefine.MSG_ADD_SUCCESS);
                return "redirect:/admincp/user";
            }
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/user";
        }
    }

    @GetMapping("del/page-{page}/{id_user}")
    public String del(@PathVariable int id_user, @PathVariable int page, RedirectAttributes ra) {
        if (userDAO.delItem(id_user) > 0) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_DEL_SUCCESS);
            return "redirect:/admincp/user/" + page;
        }
        ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
        return "redirect:/admincp/user/" + page;
    }

    @GetMapping("edit/page-{page}/{id_user}")
    public String edit(@PathVariable int id_user, @PathVariable int page, ModelMap modelMap, RedirectAttributes ra) {
        List<Role> listRole = roleDAO.getItems();
        modelMap.addAttribute("listRole", listRole);
        User objUser = userDAO.getItem(id_user);
        if (objUser == null) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/user/" + page;
        }
        modelMap.addAttribute("objUser", objUser);
        modelMap.addAttribute("page", page);
        return "admin.user.edit";
    }

    @PostMapping("edit/page-{page}/{id_user}")
    public String edit(ModelMap modelMap, @Valid @ModelAttribute("objUser") User objUser, BindingResult br,
                       @PathVariable int id_user, @PathVariable int page, RedirectAttributes ra) {
        if (br.hasErrors()) {
            List<Role> listRole = roleDAO.getItems();
            modelMap.addAttribute("listRole", listRole);
            return "admin.user.edit";
        }
        User itemUser = userDAO.getItem(id_user);
        if (itemUser != null) {
            objUser.setId(id_user);
            if ("".equals(objUser.getPassword()))
                objUser.setPassword(itemUser.getPassword());
            // selse objUser.setPassword(passwordEncoder.encode(objUser.getPassword()));
            if (userDAO.editItem(objUser) > 0) {
                ra.addFlashAttribute("msg", MessageDefine.MSG_EDIT_SUCCESS);
                return "redirect:/admincp/user/" + page;
            }
        }
        ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
        return "redirect:/admincp/user/" + page;
    }

    // tìm kiếm
    @PostMapping("tim-kiem")
    public String search(ModelMap modelMap, @RequestParam("keyword") String keyword) {
        int page = 1;
        int totalRow = userDAO.countItemsByKeyWord(keyword);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<User> listUser = userDAO.getItemsPaginationByKeyWord(offset, keyword);
        modelMap.addAttribute("listUsers", listUser);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        return "admin.user.search";
    }

    @GetMapping("tim-kiem/{keyword}/page-{page}")
    public String search(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page,
                         @PathVariable String keyword) {
        if (page == null) page = 1;
        int totalRow = userDAO.countItemsByKeyWord(keyword);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<User> listUser = userDAO.getItemsPaginationByKeyWord(offset, keyword);
        modelMap.addAttribute("listUsers", listUser);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        return "admin.user.search";
    }

    @GetMapping("edit-profile/{username}")
    public String edit(@PathVariable String username, ModelMap modelMap, RedirectAttributes ra) {
        User objUser = userDAO.getItem(username);
        if (objUser == null) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/user/edit-profile/" + username;
        }
        modelMap.addAttribute("objUser", objUser);
        return "admin.user.editprofile";
    }

    @PostMapping("edit-profile/{username}")
    public String edit(@ModelAttribute("objUser") User objUser, BindingResult br, @PathVariable String username, RedirectAttributes ra) {
        User itemUser = userDAO.getItem(username);
        if (itemUser != null) {
            objUser.setId(itemUser.getId());
            if ("".equals(objUser.getFullname())) objUser.setFullname(itemUser.getFullname());
            if ("".equals(objUser.getPassword())) objUser.setPassword(itemUser.getPassword());
            else objUser.setPassword(passwordEncoder.encode(objUser.getPassword()));
            objUser.setRole(itemUser.getRole());
            if (userDAO.editItem(objUser) > 0) {
                ra.addFlashAttribute("msg", "Lưu thành công!");
                return "redirect:/admincp/user/edit-profile/" + username;
            }
        }
        ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
        return "redirect:/admincp/user/edit-profile/" + username;
    }
}