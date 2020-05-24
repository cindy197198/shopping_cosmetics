package vn.edu.vinaenter.ajax.publics;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.edu.vinaenter.daos.CouponDAO;
import vn.edu.vinaenter.daos.ProductDAO;
import vn.edu.vinaenter.models.Coupon;
import vn.edu.vinaenter.models.Detail_order;
import vn.edu.vinaenter.models.Product;

@Controller
public class PublicCartAjax {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CouponDAO couponDAO;

    @SuppressWarnings("unchecked")
    @GetMapping("addtocart/{product_id}/{quantity}")
    public void add(ModelMap modelMap, HttpSession session, @PathVariable int quantity,
                    @PathVariable int product_id, PrintWriter out, HttpServletRequest request) throws IOException {
        Product product = productDAO.getItem(product_id);
        Detail_order obj = new Detail_order(0, 0, product, quantity);
        ArrayList<Detail_order> cart = null;
        if (session.getAttribute("cart") == null) {
            cart = new ArrayList<>();
            cart.add(obj);
        } else {
            cart = (ArrayList<Detail_order>) session.getAttribute("cart");
            boolean check = false;
            for (Detail_order item : cart) {
                if (item.getProduct().getId() == product_id) {
                    item.setQuantity(quantity + item.getQuantity());
                    check = true;
                }
            }
            if (!check || cart.size() == 0) cart.add(obj);
        }
        session.setAttribute("cart", cart);
        out.print("<a class=\"dropdown-toggle\" data-toggle=\"dropdown\"\r\n" +
                "								aria-expanded=\"true\">\r\n" +
                "								<div class=\"header-btns-icon\">\r\n" +
                "									<i class=\"fa fa-shopping-cart\"></i><span class=\"qty\" id=\"qty\">\r\n" + cart.size() +
                "									</span>\r\n" +
                "								</div> <strong class=\"text-uppercase\">Giỏ hàng</strong> <br>\r\n" +
                "							</a>\r\n" +
                "							<div class=\"custom-menu\">\r\n" +
                "								<div id=\"shopping-cart\">\r\n" +
                "									<div class=\"shopping-cart-list\">\r\n" +
                "										<c:choose>\r\n");
        for (Detail_order item : cart) {
            out.print("<div class=\"product product-widget\">\r\n" +
                    "														<div class=\"product-thumb\">\r\n" +
                    "															<img\r\n" +
                    "																src=\"" + request.getContextPath() + "/fileUpload/" + item.getProduct().getPicture().substring(0, item.getProduct().getPicture().indexOf(" ")) + "\"\r\n" +
                    "																alt=\"\">\r\n" +
                    "														</div>\r\n" +
                    "														<div class=\"product-body\">\r\n" +
                    "															<h3 class=\"product-price\">$ \r\n" + (item.getProduct().getNewprice() != 0 ? item.getProduct().getNewprice() : item.getProduct().getPrice()) +
                    "																<span class=\"qty\">x\r\n" + item.getQuantity() +
                    "																	</span>\r\n" +
                    "															</h3>\r\n" +
                    "															<h2 class=\"product-name\">\r\n" +
                    "																<a href=\"#\">" + item.getProduct().getName() + "</a>\r\n" +
                    "															</h2>\r\n" +
                    "														</div>\r\n" +
                    "													</div>");
        }
        out.println("<div class=\"shopping-cart-btns\">\r\n" +
                "											<a href=\"" + request.getContextPath() + "/gio-hang\"><button\r\n" +
                "													class=\"main-btn\" style=\"font-size: 12px;\">Xem giỏ hàng </button></a>\r\n" +
                "											<a href=\"" + request.getContextPath() + "/thanh-toan\"><button class=\"primary-btn\" style=\"font-size: 12px;\">\r\n" +
                "												Thanh toán <i class=\"fa fa-arrow-circle-right\"></i>\r\n" +
                "											</button></a>\r\n" +
                "										</div></div></div></div>");

    }

    @GetMapping("changeqty/{quantity}/{coupon_id}")
    public void change(ModelMap modelMap, HttpSession session, @PathVariable("quantity") String s,
                       PrintWriter out, HttpServletRequest request,
                       @PathVariable("coupon_id") int coupon_id) {
        String[] listQty = s.split(",");
        int[] list = new int[listQty.length];
        for (int i = 0; i < listQty.length; i++) {
            list[i] = Integer.parseInt(listQty[i]);
        }
        ArrayList<Detail_order> cart = (ArrayList<Detail_order>) session.getAttribute("cart");
        for (int i = 0; i < list.length; i++) {
            cart.get(i).setQuantity(list[i]);
        }
        session.setAttribute("cart", cart);
        double price = 0, total = 0;
        for (int i = 0; i < cart.size(); i++) {
            Detail_order item = cart.get(i);
            out.print("<tr>\r\n" +
                    "						<td class=\"thumb\"><img\r\n" +
                    "							src=\"" + request.getContextPath() + "/fileUpload/" + item.getProduct().getPicture().substring(0, item.getProduct().getPicture().indexOf(" ")) + "\" alt=\"\"></td>\r\n" +
                    "						<td class=\"details\"><a href=\"#\">" + item.getProduct().getName() + "</a></td>\r\n" +
                    "						<td class=\"price text-center\">");
            if (item.getProduct().getNewprice() != 0) {
                price = item.getProduct().getNewprice();
                out.println("<span id=\"price\" class=\"price1\">	<strong> $ " + item.getProduct().getNewprice() + " </strong></span>\r\n" +
                        "									<br>\r\n" +
                        "									<del class=\"font-weak\">\r\n" +
                        "										<small>$ " + item.getProduct().getPrice() + " </small>\r\n" +
                        "									</del>");
            } else {
                price = item.getProduct().getPrice();
                out.println("<span id=\"price\" class=\"price1\">	<strong>$ " + item.getProduct().getPrice() + " </strong></span>\r\n");
            }
            out.println("<td class=\"qty text-center\"><input class=\"quantity\" id=\"quantity\" name=\"quantity\"\r\n" +
                    "							type=\"number\" value=\"" + item.getQuantity() + "\" min=\"1\" max=\"" + item.getProduct().getAmount() + "\"></td>\r\n" +
                    "						<td class=\"total text-center\"><span id=\"sum\" class=\"total\" >$ " + item.getQuantity() * price + " </span></td>\r\n" +
                    "						<td class=\"text-right\"><button class=\"main-btn icon-btn\" onclick=\"return delcart(" + i + "," + coupon_id + ")\">\r\n" +
                    "								<i class=\"fa fa-close\"></i>\r\n" +
                    "							</button></td>\r\n" +
                    "					</tr>");
            total += item.getQuantity() * price;
        }
        out.print("<tr>\r\n" +
                "							<th class=\"empty\" colspan=\"3\"></th>\r\n" +
                "							<th>TỔNG</th>\r\n" +
                "							<th colspan=\"2\" class=\"total\"><p>$ " + total + " </p></th>\r\n" +
                "						</tr>");
        Coupon coupon = couponDAO.getItem(coupon_id);
        if (coupon != null) {
            modelMap.addAttribute("coupon", coupon);
            out.println("<tr>\r\n" +
                    "								<th class=\"empty\" colspan=\"3\"></th>\r\n" +
                    "								<th>GIẢM CÒN</th>\r\n" +
                    "							<th colspan=\"2\" class=\"total\"><p>$" + (double) Math.round((total * (1 - coupon.getValue() * 1.0 / 100)) * 100) / 100 + "</p></th>\r\n" +
                    "						</tr>");
        }
    }

    @GetMapping("delcart/{pos}/{coupon_id}")
    public void change(ModelMap modelMap, HttpSession session, @PathVariable("pos") int pos, PrintWriter out, HttpServletRequest request,
                       @PathVariable("coupon_id") int coupon_id) {
        ArrayList<Detail_order> cart = (ArrayList<Detail_order>) session.getAttribute("cart");
        cart.remove(pos);
        session.setAttribute("cart", cart);
        double price = 0, total = 0;
        for (int i = 0; i < cart.size(); i++) {
            Detail_order item = cart.get(i);
            out.print("<tr>\r\n" +
                    "						<td class=\"thumb\"><img\r\n" +
                    "							src=\"" + request.getContextPath() + "/fileUpload/" + item.getProduct().getPicture().substring(0, item.getProduct().getPicture().indexOf(" ")) + "\" alt=\"\"></td>\r\n" +
                    "						<td class=\"details\"><a href=\"#\">" + item.getProduct().getName() + "</a></td>\r\n" +
                    "						<td class=\"price text-center\">");
            if (item.getProduct().getNewprice() != 0) {
                price = item.getProduct().getNewprice();
                out.println("<span id=\"price\" class=\"price1\">	<strong>$ " + item.getProduct().getNewprice() + " </strong></span>\r\n" +
                        "									<br>\r\n" +
                        "									<del class=\"font-weak\">\r\n" +
                        "										<small>$ " + item.getProduct().getPrice() + " </small>\r\n" +
                        "									</del>");
            } else {
                price = item.getProduct().getPrice();
                out.println("<span id=\"price\" class=\"price1\">	<strong>$ " + item.getProduct().getPrice() + " </strong></span>\r\n");
            }
            out.println("<td class=\"qty text-center\"><input class=\"quantity\" id=\"quantity\" name=\"quantity\"\r\n" +
                    "							type=\"number\" value=\"" + item.getQuantity() + "\" min=\"1\" max=\"" + item.getProduct().getAmount() + "\"></td>\r\n" +
                    "						<td class=\"total text-center\"><span id=\"sum\" class=\"total\" >$ " + item.getQuantity() * price + " </span></td>\r\n" +
                    "						<td class=\"text-right\"><button class=\"main-btn icon-btn\" onclick=\"return delcart(" + i + "," + coupon_id + ")\">\r\n" +
                    "								<i class=\"fa fa-close\"></i>\r\n" +
                    "							</button></td>\r\n" +
                    "					</tr>");
            total += item.getQuantity() * price;
        }
        out.print("<tr>\r\n" +
                "							<th class=\"empty\" colspan=\"3\"></th>\r\n" +
                "							<th>TỔNG</th>\r\n" +
                "							<th colspan=\"2\" class=\"total\"><p>$ " + total + " </p></th>\r\n" +
                "						</tr>");
        Coupon coupon = couponDAO.getItem(coupon_id);
        if (coupon != null) {
            modelMap.addAttribute("coupon", coupon);
            out.println("<tr>\r\n" +
                    "								<th class=\"empty\" colspan=\"3\"></th>\r\n" +
                    "								<th>GIẢM CÒN</th>\r\n" +
                    "							<th colspan=\"2\" class=\"total\"><p>$" + (double) Math.round((total * (1 - coupon.getValue() * 1.0 / 100)) * 100) / 100 + "</p></th>\r\n" +
                    "						</tr>");
        }
    }
}
