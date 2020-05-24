package vn.edu.vinaenter.daos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Contact;

@Repository
public class CatDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> getItems() {
        String sql = "SELECT id,name,parent_id FROM categories ORDER BY id DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }

    public int addItem(Category objCat) {
        String sql = "INSERT INTO categories(name,parent_id) VALUES(?,?)";
        return jdbcTemplate.update(sql, new Object[]{objCat.getName(), objCat.getParent_id()});
    }

    public int delItem(int id_cat) {
        String sql = "DELETE FROM categories WHERE id=?";
        return jdbcTemplate.update(sql, new Object[]{id_cat});
    }

    public Category getItem(int id_cat) {
        String sql = "SELECT id,name,parent_id FROM categories WHERE id=?";
        List<Category> list = jdbcTemplate.query(sql, new Object[]{id_cat}, new BeanPropertyRowMapper<Category>(Category.class));
        if (list.size() > 0 && list != null) return list.get(0);
        else return null;
    }

    public int editItem(Category objCat) {
        String sql = "UPDATE categories SET name=?,parent_id=? WHERE id=?";
        return jdbcTemplate.update(sql, new Object[]{objCat.getName(), objCat.getParent_id(), objCat.getId()});
    }

    public int countItems() {
        String sql = "SELECT COUNT(*) total "
                + "FROM categories WHere parent_id=0";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Category> getItemsPagination(int offset) {
        String sql = "SELECT id,name,parent_id FROM categories where parent_id=0 ORDER BY id DESC LIMIT ?,?";
        return jdbcTemplate.query(sql,
                new Object[]{offset, PageDefine.ADMIN_ROW_COUNT},
                new BeanPropertyRowMapper<Category>(Category.class));
    }

    public boolean checkItem(Category objCat) {
        String sql = "SELECT id,name,parent_id FROM categories WHERE name=? && parent_id=?";
        List<Category> list = jdbcTemplate.query(sql, new Object[]{objCat.getName(), objCat.getParent_id()}, new BeanPropertyRowMapper<Category>(Category.class));
        if (list.size() > 0) return false;
        return true;
    }

    public int countItems(Category cat) {
        String sql = "SELECT COUNT(*) total "
                + "FROM categories AS c "
                + "INNER JOIN lands AS l "
                + "ON l.cid=c.id "
                + " WHERE c.cid=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{cat.getId()}, Integer.class);
    }

    public List<Category> getItemsHotCat(int catRowCount) {
        String sql = "SELECT c.cid,cname "
                + "FROM categories AS c "
                + "INNER JOIN lands AS l "
                + "ON c.cid=l.cid GROUP BY cname "
                + "ORDER BY SUM(count_views)  DESC LIMIT ?";
        return jdbcTemplate.query(sql,
                new Object[]{catRowCount},
                new BeanPropertyRowMapper<Category>(Category.class));
    }

    public int countItemsByKeyWord(String keyword) {
        String sql = "SELECT COUNT(*) total "
                + "FROM categories "
                + " WHERE name LIKE ? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{"%" + keyword + "%"}, Integer.class);
    }

    public List<Category> getItemsPaginationByKeyWord(int offset, String keyword) {
        String sql = "SELECT id,name,parent_id FROM categories WHERE name LIKE ? ORDER BY id DESC LIMIT ?,?";
        return jdbcTemplate.query(sql, new Object[]{"%" + keyword + "%", offset, PageDefine.ADMIN_ROW_COUNT}, new BeanPropertyRowMapper<Category>(Category.class));
    }

    public List<Category> getItemsbyParent_id(int id) {
        String sql = "SELECT id,name,parent_id FROM categories WHERE parent_id=? ";
        return jdbcTemplate.query(sql,
                new Object[]{id},
                new BeanPropertyRowMapper<Category>(Category.class));
    }

    public void showCategory(List<Category> listcat, int parent_id, HttpServletRequest request, javax.servlet.jsp.JspWriter out) throws IOException {
        CatDAO cateDAO = new CatDAO();
        //lấy danh sách category con
        String sql = "SELECT id,name,parent_id FROM categories WHERE parent_id=? ORDER BY id DESC";
        List<Category> list = jdbcTemplate.query(sql,
                new Object[]{parent_id},
                new BeanPropertyRowMapper<Category>(Category.class));
        if (list.size() > 0 && list != null) {
            out.println("<ul>");
            for (Category item : list) {
                listcat.remove(item);
                out.println("<li class=\"\" style=\"margin-left: 20px;\">" + item.getName() +
                        "	<a href=" + request.getContextPath() + "/admincp/cat/edit/" + item.getId() + " ><i class=\"fa fa-edit \"></i></a>\r\n" +
                        "	<a href=" + request.getContextPath() + "/admincp/cat/del/" + item.getId() + "  onclick=\"return confirm('Bạn có chắc muốn xoá?')\"><i class=\"fas fa-trash-alt\"></i></a>");
                showCategory(listcat, item.getId(), request, out);
                out.println("</li>");
            }
            out.println("</ul>");
        }
    }

    public List<Category> getItemsChild() {
        String sql = "SELECT id,name,parent_id FROM categories WHERE id NOT IN(SELECT parent_id FROM categories ) ORDER BY id DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }
}
