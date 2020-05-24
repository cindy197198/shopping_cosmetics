package vn.edu.vinaenter.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vn.edu.vinaenter.models.Role;

@Repository
public class RoleDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Role> getItems() {
        String sql = "SELECT id,name FROM roles ORDER BY id DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));
    }
}
