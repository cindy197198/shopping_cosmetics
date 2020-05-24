package vn.edu.vinaenter.controller.auth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.daos.UserDAO;
import vn.edu.vinaenter.defines.MessageDefine;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Role;
import vn.edu.vinaenter.models.User;
import vn.edu.vinaenter.utils.StringUtil;

@Controller
public class AuthController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CatDAO catDAO;
    @Autowired
    private UserDAO userDAO;
    private vn.edu.vinaenter.validator.PhoneValidator phoneValidator = new vn.edu.vinaenter.validator.PhoneValidator();

    @GetMapping("admin-login")
    public String login() {
        return "auth.login";
    }

    @GetMapping("test")
    @ResponseBody
    public String testpassword() {
        return passwordEncoder.encode("vne0211");
    }

    @GetMapping("admincp/auth/404")
    public String error404() {
        return "error.403";
    }

    @GetMapping("admincp/auth/403")
    public String error403() {
        return "error.403";
    }

    @GetMapping("customer-login")
    public String customerlogin() {
        return "auth.customer_login";
    }

    @GetMapping("register")
    public String register() {
        return "auth.register";
    }

    @PostMapping("register")
    public String register(ModelMap modelMap, @Valid @ModelAttribute("objUser") User objUser, BindingResult br, RedirectAttributes ra) {
        phoneValidator.validate(objUser, br);
        if (br.hasErrors()) {
            return "auth.register";
        } else {
            if ("".equals(objUser.getPassword())) {
                ra.addFlashAttribute("msg", "Mật khẩu không được rỗng");
                ra.addFlashAttribute("objUser", objUser);
                return "redirect:/register";
            }
            if (!userDAO.checkItem(objUser)) {
                ra.addFlashAttribute("msg", "Username đã tồn tại");
                ra.addFlashAttribute("objUser", objUser);
                return "redirect:/register";
            }
            objUser.setPassword(passwordEncoder.encode(objUser.getPassword()));
            objUser.setRole(new Role(3, ""));
            if (userDAO.addItem(objUser) > 0) {
                return "auth.register_success";
            }
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/register";
        }
    }

    @PostMapping("edit-account")
    public String edit(ModelMap modelMap, @Valid @ModelAttribute("objUser") User objUser, BindingResult br, RedirectAttributes ra, HttpSession session) {
        phoneValidator.validate(objUser, br);
        if (br.hasErrors()) {
            return "auth.register";
        } else {
            User user = (User) session.getAttribute("userLogin");
            if ("".equals(objUser.getPassword())) {
                objUser.setPassword(user.getPassword());
            }
            objUser.setRole(user.getRole());
            objUser.setPassword(passwordEncoder.encode(objUser.getPassword()));
            User user2 = userDAO.getItem(user.getUsername());
            objUser.setId(user2.getId());
            if (userDAO.editItem(objUser) > 0) {
                ra.addFlashAttribute("msg", "Đã lưu thông tin thay đổi");
                session.setAttribute("userLogin", objUser);
                return "redirect:/my-account";
            }
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/my-account";
        }
    }

    @ModelAttribute
    public void commonObjects(ModelMap modelMap) {
        List<Category> listCat = catDAO.getItemsbyParent_id(0);
        modelMap.addAttribute("listCat", listCat);
        modelMap.addAttribute("catDAO", catDAO);
    }
}
