package vn.edu.vinaenter.controller.admins;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.daos.ProductDAO;
import vn.edu.vinaenter.daos.UserDAO;
import vn.edu.vinaenter.defines.FileDefine;
import vn.edu.vinaenter.defines.MessageDefine;
import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Product;
import vn.edu.vinaenter.models.User;

@Controller
@RequestMapping("admincp/product")
public class AdminProductController {
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CatDAO catDAO;

    @GetMapping({"{page}", ""})
    public String index(Principal principal, ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        int totalRow = productDAO.countItems();
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Product> listProduct = productDAO.getItemsPagination(offset);
        modelMap.addAttribute("listProduct", listProduct);
        return "admin.product.index";
    }

    @GetMapping("add")
    public String add(ModelMap modelMap) {
        List<Category> listCat = catDAO.getItemsChild();
        modelMap.addAttribute("listCat", listCat);
        return "admin.product.add";
    }

    @PostMapping("add")
    public String add(ModelMap modelMap, @Valid @ModelAttribute("objproduct") Product objProduct, BindingResult br,
                      @RequestParam("pictureReview") ArrayList<MultipartFile> files, RedirectAttributes ra, Principal principal) throws IllegalStateException, IOException {
        //b1. kiểm tra dữ liệu đầu vào- validation
        if (br.hasErrors()) {
            List<Category> listCat = catDAO.getItemsChild();
            modelMap.addAttribute("listCat", listCat);
            return "admin.product.add";
        }
        //b2. xử lí thêm file và kiểm tra định dạng file jpg,png,jpeg...
        //upload
        //ArrayList<CommonsMultipartFile> files = objRegister.getPicture();
        //ArrayList<String> fileNames = new ArrayList<String>();
        String fileNames = "";
        for (MultipartFile obj : files) {
            String fileName = obj.getOriginalFilename();
            //kiểm tra định dạng
            String tmp = FilenameUtils.getExtension(fileName);
            if (!"".equals(fileName)) {
                if (!(tmp.equalsIgnoreCase("jpg") || tmp.equalsIgnoreCase("png") || tmp.equalsIgnoreCase("jpeg"))) {
                    return "redirect:/admincp/product/add?msg=fileError";
                }
                fileName = FileDefine.rename(fileName);
                fileNames += fileName + " ";
                String dirPath = servletContext.getRealPath("") + FileDefine.DIR_UPLOAD;
                File createDir = new File(dirPath);
                if (!createDir.exists()) {
                    createDir.mkdirs();
                }
                obj.transferTo(new File(dirPath + File.separator + fileName));
            }
        }
        objProduct.setPicture(fileNames);

        //b3. Thêm dữ liệu vào DB
        User userLogin = (User) userDAO.getItem(principal.getName());
        objProduct.setUser(userLogin);
        if (productDAO.addItem(objProduct) > 0) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_ADD_SUCCESS);
            return "redirect:/admincp/product";
        }
        ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
        return "admin.product.add";
    }

    @GetMapping("del/{id_product}")
    public String del(@PathVariable int id_product, RedirectAttributes ra) {
        if (productDAO.delItem(id_product) > 0) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_DEL_SUCCESS);
            return "redirect:/admincp/product";
        }
        ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
        return "redirect:/admincp/product";
    }

    @GetMapping("edit/page-{page}/{id_product}")
    public String edit(@PathVariable int id_product, ModelMap modelMap, RedirectAttributes ra, @PathVariable int page) {
        Product objProduct = productDAO.getItem(id_product);
        if (objProduct == null) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/product";
        }
        List<Category> listCat = catDAO.getItemsChild();
        modelMap.addAttribute("listCat", listCat);
        modelMap.addAttribute("objProduct", objProduct);
        if (!"".equals(objProduct.getPicture())) {
            String[] fileNames = objProduct.getPicture().split(" ");
            List<String> listFileNames = new ArrayList<>();
            for (int i = 0; i < fileNames.length; i++) {
                listFileNames.add(fileNames[i]);
            }
            modelMap.addAttribute("listFileNames", listFileNames);
        }
        modelMap.addAttribute("page", page);
        return "admin.product.edit";
    }

    @PostMapping("edit/page-{page}/{id_product}")
    public String edit(ModelMap modelMap, @Valid @ModelAttribute("objproduct") Product objProduct, BindingResult br,
                       @PathVariable int id_product, RedirectAttributes ra, @PathVariable int page,
                       @RequestParam("pictureReview") ArrayList<MultipartFile> files
            , Principal principal) throws IllegalStateException, IOException {
        if (br.hasErrors()) {
            List<Category> listCat = catDAO.getItemsChild();
            modelMap.addAttribute("listCat", listCat);
            return "admin.product.edit";
        }
        Product itemProduct = productDAO.getItem(id_product);
        //b2. xử lí thêm file và kiểm tra định dạng file jpg,png,jpeg...
        String fileNames = "";
        for (MultipartFile obj : files) {
            String fileName = obj.getOriginalFilename();
            //kiểm tra định dạng
            String tmp = FilenameUtils.getExtension(fileName);
            if (!"".equals(fileName)) {
                if (!(tmp.equalsIgnoreCase("jpg") || tmp.equalsIgnoreCase("png") || tmp.equalsIgnoreCase("jpeg"))) {
                    return "redirect:/admincp/product/edit/" + id_product;
                }
                fileName = FileDefine.rename(fileName);
                fileNames += fileName + " ";
                String dirPath = servletContext.getRealPath("") + FileDefine.DIR_UPLOAD;
                File createDir = new File(dirPath);
                if (!createDir.exists()) {
                    createDir.mkdirs();
                }
                obj.transferTo(new File(dirPath + File.separator + fileName));
            }
        }
        if (!"".equals(fileNames)) objProduct.setPicture(fileNames + itemProduct.getPicture());
        else objProduct.setPicture(itemProduct.getPicture());
        //b3. Thêm dữ liệu vào DB
        User userLogin = (User) userDAO.getItem(principal.getName());
        objProduct.setUser(userLogin);
        objProduct.setId(id_product);
        if (productDAO.editItem(objProduct) > 0) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_EDIT_SUCCESS);
            return "redirect:/admincp/product/" + page;
        }
        ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
        return "redirect:/admincp/product/" + page;
    }

    //tìm kiếm
    @PostMapping("tim-kiem")
    public String search(ModelMap modelMap, @RequestParam("property-search") int property_search,
                         @RequestParam("keyword") String keyword) {
        int page = 1;
        int totalRow = productDAO.countItemsByKeyWord(keyword, property_search);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Product> listProduct = productDAO.getItemsPaginationByKeyWord(offset, keyword, property_search);
        modelMap.addAttribute("listProduct", listProduct);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        modelMap.addAttribute("property_search", property_search);
        return "admin.product.search";
    }

    @GetMapping("tim-kiem/{property_search}/{keyword}/page-{page}")
    public String search(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page,
                         @PathVariable String keyword, @PathVariable int property_search) {
        if (page == null) page = 1;
        int totalRow = productDAO.countItemsByKeyWord(keyword, property_search);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.ADMIN_ROW_COUNT);
        int offset = (page - 1) * PageDefine.ADMIN_ROW_COUNT;
        List<Product> listProduct = productDAO.getItemsPaginationByKeyWord(offset, keyword, property_search);
        modelMap.addAttribute("listProduct", listProduct);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("totalRow", totalRow);
        modelMap.addAttribute("property_search", property_search);
        return "admin.product.search";
    }

    @GetMapping("edit/delImg/page-{page}/{id_product}/{pos}")
    public String delImage(@PathVariable int id_product, ModelMap modelMap, RedirectAttributes ra, @PathVariable int pos, @PathVariable int page) {
        Product objProduct = productDAO.getItem(id_product);
        if (objProduct == null) {
            ra.addFlashAttribute("msg", MessageDefine.MSG_ERROR);
            return "redirect:/admincp/product";
        }
        List<Category> listCat = catDAO.getItems();
        modelMap.addAttribute("listCat", listCat);
        modelMap.addAttribute("objProduct", objProduct);
        String[] fileNames = objProduct.getPicture().split(" ");
        List<String> listFileNames = new ArrayList<>();
        for (int i = 0; i < fileNames.length; i++) {
            listFileNames.add(fileNames[i]);
        }
        listFileNames.remove(pos);
        String str = "";
        for (String i : listFileNames) str += i + " ";
        productDAO.editImage(id_product, str);
        modelMap.addAttribute("listFileNames", listFileNames);
        modelMap.addAttribute("page", page);
        return "admin.product.edit";
    }
}
