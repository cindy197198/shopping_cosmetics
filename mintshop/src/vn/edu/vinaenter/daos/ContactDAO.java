package vn.edu.vinaenter.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Contact;

@Repository
public class ContactDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Contact> getItems() {
        String sql = "SELECT cid,fullname,email,subject,content FROM contact ORDER BY cid DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Contact>(Contact.class));
    }

    public int addItem(Contact objContact) {
        String sql = "INSERT INTO contact(fullname,email,subject,content) VALUES(?,?,?,?)";
        return jdbcTemplate.update(sql, new Object[]{objContact.getFullname(),
                objContact.getEmail(),
                objContact.getSubject(),
                objContact.getContent()});
    }

    public int countItems() {
        String sql = "SELECT COUNT(*) total "
                + "FROM contact ";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Contact> getItemsPagination(int offset) {
        String sql = "SELECT id,fullname,email,subject,content,date_create FROM contact ORDER BY id DESC LIMIT ?,?";
        return jdbcTemplate.query(sql,
                new Object[]{offset, PageDefine.ADMIN_ROW_COUNT},
                new BeanPropertyRowMapper<Contact>(Contact.class));
    }

    public int delItem(int id_contact) {
        String sql = "DELETE FROM contact WHERE id=?";
        return jdbcTemplate.update(sql, new Object[]{id_contact});
    }

    public int countItemsByKeyWord(String keyword) {
        String sql = "SELECT COUNT(*) total "
                + "FROM contact "
                + " WHERE content LIKE ? or fullname LIKE ? or subject LIKE ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"}, Integer.class);
    }

    public List<Contact> getItemsPaginationByKeyWord(int offset, String keyword) {
        String sql = "SELECT id,fullname,email,subject,content,date_create"
                + " FROM contact "
                + " WHERE content LIKE ? or fullname LIKE ? or subject LIKE ? "
                + " ORDER BY id DESC LIMIT ?,?";
        return jdbcTemplate.query(sql, new Object[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", offset, PageDefine.ADMIN_ROW_COUNT},
                new BeanPropertyRowMapper<Contact>(Contact.class));
    }

    public Contact getItem(int id_contact) {
        String sql = "SELECT id,fullname,email,subject,content,date_create FROM contact WHERE id=?";
        List<Contact> list = jdbcTemplate.query(sql, new Object[]{id_contact}, new BeanPropertyRowMapper<Contact>(Contact.class));
        if (list.size() > 0 && list != null) return list.get(0);
        else return null;
    }

}
