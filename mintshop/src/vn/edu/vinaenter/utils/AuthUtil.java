package vn.edu.vinaenter.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import vn.edu.vinaenter.daos.CatDAO;
import vn.edu.vinaenter.models.Category;

public class AuthUtil {
    @Autowired
    private CatDAO catDAO;

    public static boolean checkLogin(HttpSession session) throws IOException {
        if (session.getAttribute("userLogin") == null) {
            return false;
        }
        return true;
    }
/*	public  void ShowCategory(List<Category> listcat,int parent_id,HttpServletRequest request, javax.servlet.jsp.JspWriter out) throws IOException {
		//lấy danh sách category con
		List<Category> list=catDAO.getItemsbyParent_id(1);
		System.out.println(catDAO.getItemsbyParent_id(parent_id).size());
		if (list.size()>0 && list !=null) {
			out.println("<ul>");
			for (Category item:list) {
				listcat.remove(item);
				out.println("<li class=\"\" style=\"margin-left: 20px;\">"+item.getName()+
						"<a href="+request.getContextPath() +"/admin/cat/edit?id="+item.getId() +" ><i class=\"fa fa-edit \"></i></a>\r\n" + 
						"<a href="+request.getContextPath() +"/admin/cat/del?id="+item.getId() +"  onclick=\"return confirm('Bạn có chắc muốn xoá?')\"><i class=\"fas fa-trash-alt\"></i></a>");
				ShowCategory(listcat, item.getId(), request,out);
				out.println("</li>");
			}
			out.println("</ul>");
		}
		System.out.println(parent_id);
	}*/
}
