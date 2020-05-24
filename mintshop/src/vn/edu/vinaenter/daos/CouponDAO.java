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
import vn.edu.vinaenter.models.Coupon;

@Repository
public class CouponDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> getItems() {
        String sql = "SELECT id,name,parent_id FROM categories ORDER BY id DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }

    public int addItem(Coupon coupon) {
        String sql = "INSERT INTO coupons(name,quantity,value) VALUES(?,?,?)";
        return jdbcTemplate.update(sql, new Object[]{coupon.getName(), coupon.getQuantity(), coupon.getValue()});
    }

    public int delItem(int id_coupon) {
        String sql = "DELETE FROM coupons WHERE id=?";
        return jdbcTemplate.update(sql, new Object[]{id_coupon});
    }

    public Coupon getItem(int id_coupon) {
        String sql = "SELECT id,name,quantity,value FROM coupons WHERE id=?";
        List<Coupon> list = jdbcTemplate.query(sql, new Object[]{id_coupon}, new BeanPropertyRowMapper<Coupon>(Coupon.class));
        if (list.size() > 0 && list != null) return list.get(0);
        else return null;
    }

    public int editItem(Coupon coupon) {
        String sql = "UPDATE coupons SET name=?,quantity=?,value=? WHERE id=?";
        return jdbcTemplate.update(sql, new Object[]{coupon.getName(), coupon.getQuantity(), coupon.getValue(), coupon.getId()});
    }

    public int countItems() {
        String sql = "SELECT COUNT(*) total "
                + "FROM coupons ";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Coupon> getItemsPagination(int offset) {
        String sql = "SELECT id,name,quantity,value FROM coupons ORDER BY id DESC LIMIT ?,?";
        return jdbcTemplate.query(sql,
                new Object[]{offset, PageDefine.ADMIN_ROW_COUNT},
                new BeanPropertyRowMapper<Coupon>(Coupon.class));
    }

    public boolean checkItem(Coupon coupon) {
        String sql = "SELECT id,name,quantity,value FROM coupons WHERE name=? ";
        List<Coupon> list = jdbcTemplate.query(sql, new Object[]{coupon.getName()}, new BeanPropertyRowMapper<Coupon>(Coupon.class));
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
                + "FROM coupons "
                + " WHERE name LIKE ? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{"%" + keyword + "%"}, Integer.class);
    }

    public List<Coupon> getItemsPaginationByKeyWord(int offset, String keyword) {
        String sql = "SELECT id,name,quantity,value FROM coupons WHERE name LIKE ? ORDER BY id DESC LIMIT ?,?";
        return jdbcTemplate.query(sql, new Object[]{"%" + keyword + "%", offset, PageDefine.ADMIN_ROW_COUNT}, new BeanPropertyRowMapper<Coupon>(Coupon.class));
    }

    public Coupon getItem(String namecoupon) {
        String sql = "SELECT id,name,quantity,value FROM coupons WHERE name=? and quantity>0";
        List<Coupon> list = jdbcTemplate.query(sql, new Object[]{namecoupon}, new BeanPropertyRowMapper<Coupon>(Coupon.class));
        if (list.size() > 0 && list != null) return list.get(0);
        else return null;
    }

    public int decQuantity(Coupon coupon) {
        String sql = "UPDATE coupons SET quantity=quantity-1 WHERE name=?";
        return jdbcTemplate.update(sql, new Object[]{coupon.getName()});

    }
}
