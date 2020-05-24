package vn.edu.vinaenter.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Role;
import vn.edu.vinaenter.models.User;

@Repository
public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getItems() {
        String sql = "SELECT id,username,fullname,password FROM users ORDER BY id DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
    }

    public int addItem(User objUser) {
        String sql = "INSERT INTO users(username,fullname,password,roleId,phone,email,address) VALUES(?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, new Object[]{objUser.getUsername(),
                objUser.getFullname(),
                objUser.getPassword(),
                objUser.getRole().getId(),
                objUser.getPhone(),
                objUser.getEmail(),
                objUser.getAddress()});
    }

    public int delItem(int id_user) {
        String sql = "DELETE FROM users WHERE id=?";
        return jdbcTemplate.update(sql, new Object[]{id_user});
    }

    public User getItem(int id_user) {
        List<User> listUser = new ArrayList<User>();
        String sql = "SELECT u.id,username,fullname,password,u.roleId,name,active,phone,email,address  "
                + "FROM users AS u "
                + "INNER JOIN roles AS r "
                + "ON u.roleId=r.id "
                + "WHERE u.id=?";
        return jdbcTemplate.query(sql,
                new Object[]{id_user},
                new ResultSetExtractor<List<User>>() {
                    @Override
                    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listUser.add(new User(rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("fullname"),
                                    rs.getString("password"),
                                    rs.getString("phone"),
                                    rs.getString("email"),
                                    rs.getString("address"),
                                    new Role(rs.getInt("roleId"), rs.getString("name")),
                                    rs.getInt("active")));
                        }
                        return listUser;
                    }
                }).get(0);
    }

    public int editItem(User itemUser) {
        String sql = "UPDATE users SET fullname=?,password=?,roleId=?,phone=?,email=?,address=? WHERE id=?";
        return jdbcTemplate.update(sql, new Object[]{itemUser.getFullname(),
                itemUser.getPassword(),
                itemUser.getRole().getId(),
                itemUser.getPhone(),
                itemUser.getEmail(),
                itemUser.getAddress(),
                itemUser.getId()});
    }

    public int countItems() {
        String sql = "SELECT COUNT(*) total "
                + "FROM users ";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<User> getItemsPagination(int offset) {
        List<User> listUser = new ArrayList<User>();
        String sql = "SELECT u.id,username,fullname,password,u.roleId,name,active,phone,email,address "
                + "FROM users AS u "
                + "INNER JOIN roles AS r "
                + "ON u.roleId=r.id "
                + "ORDER BY u.id DESC LIMIT ?,?";
        return (List<User>) jdbcTemplate.query(sql,
                new Object[]{offset, PageDefine.ADMIN_ROW_COUNT},
                new ResultSetExtractor<List<User>>() {
                    @Override
                    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listUser.add(new User(rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("fullname"),
                                    rs.getString("password"),
                                    rs.getString("phone"),
                                    rs.getString("email"),
                                    rs.getString("address"),
                                    new Role(rs.getInt("roleId"), rs.getString("name")),
                                    rs.getInt("active")));
                        }
                        return listUser;
                    }
                });
    }

    public boolean checkItem(User objUser) {
        String sql = "SELECT id,username,fullname,password FROM users WHERE username=?";
        List<User> list = jdbcTemplate.query(sql, new Object[]{objUser.getUsername()}, new BeanPropertyRowMapper<User>(User.class));
        if (list.size() > 0) return false;
        return true;
    }

    public void active(int id, int i) {
        String sql = "UPDATE users SET active=? WHERE id=?";
        jdbcTemplate.update(sql, new Object[]{i, id});
    }

    public User getItem(String username) {
        List<User> listUser = new ArrayList<User>();
        String sql = "SELECT u.id,username,fullname,password,u.roleId,name,active,phone,email,address  "
                + "FROM users AS u "
                + "INNER JOIN roles AS r "
                + "ON u.roleId=r.id "
                + "WHERE username=?";
        return jdbcTemplate.query(sql,
                new Object[]{username},
                new ResultSetExtractor<List<User>>() {
                    @Override
                    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listUser.add(new User(rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("fullname"),
                                    rs.getString("password"),
                                    rs.getString("phone"),
                                    rs.getString("email"),
                                    rs.getString("address"),
                                    new Role(rs.getInt("roleId"), rs.getString("name")),
                                    rs.getInt("active")));
                        }
                        return listUser;
                    }
                }).get(0);
    }

    public int countItemsByKeyWord(String keyword) {
        String sql = "SELECT COUNT(*) total "
                + "FROM users "
                + " WHERE username LIKE ? or fullname LIKE ? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{"%" + keyword + "%", "%" + keyword + "%"}, Integer.class);
    }

    public List<User> getItemsPaginationByKeyWord(int offset, String keyword) {
        List<User> listUser = new ArrayList<User>();
        String sql = "SELECT u.id,username,fullname,password,u.roleId,name,active,phone,email,address "
                + "FROM users AS u "
                + "INNER JOIN roles AS r "
                + "ON u.roleId=r.id "
                + "WHERE username LIKE ? or fullname LIKE ? "
                + "ORDER BY u.id DESC LIMIT ?,?";
        return (List<User>) jdbcTemplate.query(sql,
                new Object[]{"%" + keyword + "%", "%" + keyword + "%", offset, PageDefine.ADMIN_ROW_COUNT},
                new ResultSetExtractor<List<User>>() {
                    @Override
                    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listUser.add(new User(rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("fullname"),
                                    rs.getString("password"),
                                    rs.getString("phone"),
                                    rs.getString("email"),
                                    rs.getString("address"),
                                    new Role(rs.getInt("roleId"), rs.getString("name")),
                                    rs.getInt("active")));
                        }
                        return listUser;
                    }
                });
    }

    public User checkLogin(String username, String password) {
        String sql = "SELECT id,username,fullname,password,roleId FROM users WHERE username=? && password=? && roleId=3";
        List<User> list = jdbcTemplate.query(sql, new Object[]{username, password}, new BeanPropertyRowMapper<User>(User.class));
        if (list.size() > 0) return getItem(list.get(0).getId());
        return null;
    }

}
