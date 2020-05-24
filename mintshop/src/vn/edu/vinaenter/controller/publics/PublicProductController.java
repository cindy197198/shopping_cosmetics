package vn.edu.vinaenter.controller.publics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.daos.ProductDAO;
import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Product;

@Controller
public class PublicProductController {
    @Autowired
    private CatDAO catDAO;
    @Autowired
    private ProductDAO productDAO;

    @GetMapping("chi-tiet/{pname}-{pid}.html")
    public String detail(ModelMap modelMap, @PathVariable("pid") int pid, @PathVariable("pname") String pname) {
        Product objProduct = productDAO.getItem(pid);
        if (!"".equals(objProduct.getPicture())) {
            String[] fileNames = objProduct.getPicture().split(" ");
            List<String> listFileNames = new ArrayList<>();
            for (int i = 0; i < fileNames.length; i++) {
                listFileNames.add(fileNames[i]);
            }
            modelMap.addAttribute("listFileNames", listFileNames);
        }
        modelMap.addAttribute("objProduct", objProduct);
        List<Product> relatedProducts = productDAO.getRelatedItems(objProduct, 4);
        modelMap.addAttribute("relatedProducts", relatedProducts);
        return "public.detail";
    }

    @ModelAttribute
    public void commonObjects(ModelMap modelMap) {
        List<Category> listCat = catDAO.getItemsbyParent_id(0);
        modelMap.addAttribute("listCat", listCat);
        List<Product> listDeal = productDAO.getItemDeal(0);
        modelMap.addAttribute("listDeal", listDeal);
        modelMap.addAttribute("catDAO", catDAO);
        modelMap.addAttribute("productDAO", productDAO);
    }

    @GetMapping({"danh-muc/{cname}-{cid}/{status-order}/page-{page}", "danh-muc/{cname}-{cid}"})
    public String index(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page,
                        @PathVariable("cid") int cid,
                        @PathVariable("cname") String cname,
                        @PathVariable(value = "status-order", required = false) Integer status_order) {
        if (page == null) page = 1;
        if (status_order == null) status_order = 1;
        int totalRow = 0;
        int sumPage = 0;
        List<Product> listProduct = null;
        switch (status_order) {
            case 1: {
                totalRow = productDAO.countItemsByCat(cid);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemsPaginationByCat(cid, offset);
            }
            break;
            case 2: {
                totalRow = productDAO.countItemsByCat(cid);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemsPaginationByCatOrderASC(cid, offset);
            }
            break;
            case 3: {
                totalRow = productDAO.countItemsByCat(cid);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemsPaginationByCatOrderPriceDESC(cid, offset);
            }
            break;
            case 4: {
                totalRow = productDAO.countItemsByCat(cid);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemsPaginationByCatOrderPriceASC(cid, offset);
            }
            break;
            default:
                break;
        }
        modelMap.addAttribute("listProduct", listProduct);
        Category cat = catDAO.getItem(cid);
        modelMap.addAttribute("status_order", status_order);
        modelMap.addAttribute("cat", cat);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        return "public.cat";
    }

    @PostMapping({"danh-muc/{cname}-{cid}/page-{page}", "danh-muc/{cname}-{cid}"})
    public String order(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page,
                        @PathVariable("cid") int cid,
                        @PathVariable("cname") String cname,
                        @RequestParam(value = "status-order", required = false) int status_order) {
        if (page == null) page = 1;
        int totalRow = 0;
        int sumPage = 0;
        List<Product> listProduct = null;
        switch (status_order) {
            case 1: {
                totalRow = productDAO.countItemsByCat(cid);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemsPaginationByCat(cid, offset);
            }
            break;
            case 2: {
                totalRow = productDAO.countItemsByCat(cid);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemsPaginationByCatOrderASC(cid, offset);
            }
            break;
            case 3: {
                totalRow = productDAO.countItemsByCat(cid);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemsPaginationByCatOrderPriceDESC(cid, offset);
            }
            break;
            case 4: {
                totalRow = productDAO.countItemsByCat(cid);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemsPaginationByCatOrderPriceASC(cid, offset);
            }
            break;
            default:
                break;
        }
        modelMap.addAttribute("listProduct", listProduct);
        Category cat = catDAO.getItem(cid);
        modelMap.addAttribute("status_order", status_order);
        modelMap.addAttribute("cat", cat);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        return "public.cat";
    }

    @GetMapping({"giam-gia/{status-order}/page-{page}", "giam-gia"})
    public String deal(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page,
                       @PathVariable(value = "status-order", required = false) Integer status_order) {
        if (page == null) page = 1;
        if (status_order == null) status_order = 1;
        int totalRow = productDAO.countItemsDeal();
        int sumPage = 0;
        List<Product> listProduct = null;
        switch (status_order) {
            case 1: {
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemDeal(offset);
            }
            break;
            case 2: {
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemDealOrderASC(offset);
            }
            break;
            case 3: {
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemDealOrderPriceDESC(offset);
            }
            break;
            case 4: {
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemDealOrderPriceASC(offset);
            }
            break;
            default:
                break;
        }
        modelMap.addAttribute("listProduct", listProduct);
        modelMap.addAttribute("status_order", status_order);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        return "public.deal";
    }

    @PostMapping({"giam-gia/{status-order}/page-{page}", "giam-gia"})
    public String deal2(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page,
                        @RequestParam(value = "status-order", required = false) int status_order) {
        if (page == null) page = 1;
        int totalRow = productDAO.countItemsDeal();
        int sumPage = 0;
        List<Product> listProduct = null;
        switch (status_order) {
            case 1: {
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemDeal(offset);
            }
            break;
            case 2: {
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemDealOrderASC(offset);
            }
            break;
            case 3: {
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemDealOrderPriceDESC(offset);
            }
            break;
            case 4: {
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemDealOrderPriceASC(offset);
            }
            break;
            default:
                break;
        }
        modelMap.addAttribute("listProduct", listProduct);
        modelMap.addAttribute("status_order", status_order);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        return "public.deal";
    }

    @PostMapping("tim-kiem/{cname}-{cid}")
    public String search(ModelMap modelMap, @RequestParam(value = "status-search", required = false) int status_search,
                         @RequestParam("keyword") String keyword, @PathVariable("cid") int cid) {
        int page = 1;
        int totalRow = 0;
        int sumPage = 0;
        List<Product> listProduct = null;
        switch (status_search) {
            case 1: {
                totalRow = productDAO.countItemsByKeyWord(cid, keyword);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemByKeyWord(cid, keyword, offset);
            }
            break;
            case 2: {
                totalRow = productDAO.countItemsByBrand(cid, keyword);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemByBrand(cid, keyword, offset);
            }
            break;
            default:
                break;
        }
        modelMap.addAttribute("listProduct", listProduct);
        modelMap.addAttribute("status_search", status_search);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        Category cat = catDAO.getItem(cid);
        modelMap.addAttribute("cat", cat);
        return "public.search";
    }

    @GetMapping("tim-kiem/{cname}-{cid}/{status-search}/{keyword}/page-{page}")
    public String search(ModelMap modelMap, @PathVariable(value = "status-search", required = false) Integer status_search,
                         @PathVariable("keyword") String keyword, @PathVariable("cid") int cid, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        int totalRow = 0;
        int sumPage = 0;
        List<Product> listProduct = null;
        switch (status_search) {
            case 1: {
                totalRow = productDAO.countItemsByKeyWord(cid, keyword);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemByKeyWord(cid, keyword, offset);
            }
            break;
            case 2: {
                totalRow = productDAO.countItemsByBrand(cid, keyword);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemByBrand(cid, keyword, offset);
            }
            break;
            default:
                break;
        }
        modelMap.addAttribute("listProduct", listProduct);
        modelMap.addAttribute("status_search", status_search);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        Category cat = catDAO.getItem(cid);
        modelMap.addAttribute("cat", cat);
        return "public.search";
    }

    @GetMapping({"price/{cid}/{low}-{high}", "price/{cid}/{low}-{high}/page-{page}"})
    public String price(ModelMap modelMap, @PathVariable(value = "page", required = false) Integer page, @PathVariable("cid") int cid,
                        @PathVariable("low") String low, @PathVariable("high") String high) {
        if (page == null) page = 1;
        int lowprice = Integer.parseInt(low);
        int highprice = Integer.parseInt(high);
        int totalRow = productDAO.countItemsByPrice(cid, lowprice, highprice);
        int sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
        if (page > sumPage || page < 1) page = 1;
        int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
        List<Product> listProduct = productDAO.getItemByPrice(cid, lowprice, highprice, offset);
        modelMap.addAttribute("listProduct", listProduct);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("lowprice", lowprice);
        modelMap.addAttribute("highprice", highprice);
        Category cat = catDAO.getItem(cid);
        modelMap.addAttribute("cat", cat);
        return "public.price";
    }

    @PostMapping("tim-kiem")
    public String search(ModelMap modelMap,
                         @RequestParam("key_search") String keyword, @RequestParam("property_search") int cat_id) {
        int page = 1;
        int totalRow = 0;
        int sumPage = 0;
        List<Product> listProduct = null;
        switch (cat_id) {
            case 0: {
                totalRow = productDAO.countItemsByKeyWord(keyword);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemByKeyWordAll(keyword, offset);
            }
            break;
            default: {
                listProduct = productDAO.getItemsNew(cat_id, keyword);
                totalRow = listProduct.size();
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                modelMap.addAttribute("offset", offset);
            }
            break;
        }
        modelMap.addAttribute("listProduct", listProduct);
        modelMap.addAttribute("property_search", cat_id);
        modelMap.addAttribute("key_search", keyword);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        Category cat = catDAO.getItem(cat_id);
        if (cat != null) modelMap.addAttribute("cat", cat);
        return "public.search2";
    }

    @GetMapping("tim-kiem/{property_search}/{keyword}/page-{page}")
    public String search2(ModelMap modelMap, @PathVariable("property_search") int cat_id,
                          @PathVariable("keyword") String keyword, @PathVariable("page") int page) {
        int totalRow = 0;
        int sumPage = 0;
        List<Product> listProduct = null;
        switch (cat_id) {
            case 0: {
                totalRow = productDAO.countItemsByKeyWord(keyword);
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                listProduct = productDAO.getItemByKeyWordAll(keyword, offset);
            }
            break;
            default: {
                listProduct = productDAO.getItemsNew(cat_id, keyword);
                totalRow = listProduct.size();
                sumPage = (int) Math.ceil((float) totalRow / PageDefine.PUBLIC_CAT_ROW_COUNT);
                if (page > sumPage || page < 1) page = 1;
                int offset = (page - 1) * PageDefine.PUBLIC_CAT_ROW_COUNT;
                modelMap.addAttribute("offset", offset);
            }
            break;
        }
        modelMap.addAttribute("listProduct", listProduct);
        modelMap.addAttribute("property_search", cat_id);
        modelMap.addAttribute("key_search", keyword);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("sumPage", sumPage);
        modelMap.addAttribute("page", page);
        Category cat = catDAO.getItem(cat_id);
        if (cat != null) modelMap.addAttribute("cat", cat);
        return "public.search2";
    }
}
