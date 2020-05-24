package vn.edu.vinaenter.controller.publics;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.daos.ContactDAO;
import vn.edu.vinaenter.defines.MessageDefine;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Contact;

@Controller
@RequestMapping("lien-he")
public class PublicContactController {
    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private CatDAO catDAO;

    @GetMapping
    public String contact(ModelMap modelMap) {
        return "public.contact";
    }

    @PostMapping
    public String contact(ModelMap modelMap, @Valid @ModelAttribute("objContact") Contact objContact, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return "public.contact";
        }
        if (contactDAO.addItem(objContact) > 0) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_SEND_SUCCESS);
        } else {
            ra.addFlashAttribute("msg", MessageDefine.MSG_SEND_ERROR);
        }
        return "redirect:/lien-he";
    }

    @ModelAttribute
    public void commonObjects(ModelMap modelMap) {
        List<Category> listCat = catDAO.getItemsbyParent_id(0);
        modelMap.addAttribute("listCat", listCat);
        modelMap.addAttribute("catDAO", catDAO);
    }
}
