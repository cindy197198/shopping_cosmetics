package vn.edu.vinaenter.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Detail_order;
import vn.edu.vinaenter.models.Order;
import vn.edu.vinaenter.models.Order_status;
import vn.edu.vinaenter.models.Payment_method;
import vn.edu.vinaenter.models.Product;
import vn.edu.vinaenter.models.User;

@Repository
public class OrderDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Order> getItems() {
        List<Order> listOrder = new ArrayList<Order>();
        String sql = "SELECT o.id,customer_id,payment_id,payment_status,coupon_value,date_create,status,o.fullname as oFullname,o.address as oAddress,o.email as oEmail,o.phone as oPhone "
                + ",u.phone as userPhone,username,u.fullname as userFullname,u.address as userAddress,u.email as userEmail,p.name AS paymethod,os.name AS statusName "
                + "FROM orders AS o "
                + "LEFT JOIN users AS u "
                + "ON o.customer_id=u.id "
                + "INNER JOIN payments AS p "
                + "ON p.id=o.payment_id "
                + "INNER JOIN order_status AS os "
                + "ON os.id=o.status "
                + "ORDER BY o.id DESC";
        return (List<Order>) jdbcTemplate.query(sql,
                new ResultSetExtractor<List<Order>>() {
                    @Override
                    public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            User user = null;
                            if (rs.getInt("customer_id") == 0) {
                                user = new User(0, "", rs.getString("oFullname"), "", rs.getString("oPhone"), rs.getString("oEmail"), rs.getString("oAddress"), null, 1);
                            } else {
                                if (!"".equals(rs.getString("oAddress"))) {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("oPhone"), rs.getString("userEmail"), rs.getString("oAddress"), null, 1);
                                } else {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("userPhone"), rs.getString("userEmail"), rs.getString("userAddress"), null, 1);
                                }
                            }
                            listOrder.add(new Order(rs.getInt("id"), 0,
                                    user,
                                    new Payment_method(rs.getInt("payment_id"), rs.getString("paymethod")),
                                    rs.getInt("payment_status"),
                                    rs.getInt("coupon_value"),
                                    rs.getTimestamp("date_create"),
                                    new Order_status(rs.getInt("status"), rs.getString("statusName")), "",
                                    null)
                            );
                        }
                        return listOrder;
                    }
                });
    }

    public int countItems() {
        String sql = "SELECT COUNT(*) total "
                + "FROM orders";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Order> getItemsPagination(int offset) {
        List<Order> listOrder = new ArrayList<Order>();
        String sql = "SELECT o.id,customer_id,payment_id,payment_status,coupon_value,date_create,status,o.fullname as oFullname,o.address as oAddress,o.email as oEmail,o.phone as oPhone"
                + ",u.phone as userPhone,username,u.fullname as userFullname,u.address as userAddress,u.email as userEmail,p.name AS paymethod,os.name AS statusName "
                + "FROM orders AS o "
                + "left JOIN users AS u "
                + "ON o.customer_id=u.id "
                + "INNER JOIN payments AS p "
                + "ON p.id=o.payment_id "
                + "INNER JOIN order_status AS os "
                + "ON os.id=o.status "
                + "ORDER BY o.id DESC LIMIT ?,?";
        return (List<Order>) jdbcTemplate.query(sql, new Object[]{offset, PageDefine.ADMIN_ROW_COUNT},
                new ResultSetExtractor<List<Order>>() {
                    @Override
                    public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            User user = null;
                            if (rs.getInt("customer_id") == 0) {
                                user = new User(0, "", rs.getString("oFullname"), "", rs.getString("oPhone"), rs.getString("oEmail"), rs.getString("oAddress"), null, 1);
                            } else {
                                if (!"".equals(rs.getString("oAddress"))) {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("oPhone"), rs.getString("userEmail"), rs.getString("oAddress"), null, 1);
                                } else {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("userPhone"), rs.getString("userEmail"), rs.getString("userAddress"), null, 1);
                                }
                            }
                            listOrder.add(new Order(rs.getInt("id"), 0,
                                    user,
                                    new Payment_method(rs.getInt("payment_id"), rs.getString("paymethod")),
                                    rs.getInt("payment_status"),
                                    rs.getInt("coupon_value"),
                                    rs.getTimestamp("date_create"),
                                    new Order_status(rs.getInt("status"), rs.getString("statusName")), "",
                                    null)
                            );
                        }
                        return listOrder;
                    }
                });
    }

    public List<Detail_order> getListOrderDetail(int id) {
        List<Detail_order> listDetail = new ArrayList<Detail_order>();
        String sql2 = "SELECT d.id,order_id,product_id,quantity,p.name,price,newprice "
                + "FROM detail_order AS d "
                + "INNER JOIN products AS p "
                + "ON d.product_id=p.id "
                + "INNER JOIN orders AS o "
                + "ON o.id= d.order_id "
                + "WHERE order_id=? ORDER BY d.id DESC";
        return (List<Detail_order>) jdbcTemplate.query(sql2, new Object[]{id},
                new ResultSetExtractor<List<Detail_order>>() {
                    @Override
                    public List<Detail_order> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listDetail.add(new Detail_order(rs.getInt("id"),
                                    rs.getInt("order_id"),
                                    new Product(rs.getInt("product_id"), rs.getString("name"), null, null, "", rs.getDouble("price"), rs.getDouble("newprice"), "", "", "", null, 0),
                                    rs.getInt("quantity")));

                        }
                        return listDetail;
                    }
                });
    }

    public Order getItem(int id_order) {
        List<Order> listOrder = new ArrayList<Order>();
        String sql = "SELECT o.id,customer_id,payment_id,payment_status,coupon_value,date_create,status,o.fullname as oFullname,o.address as oAddress,o.email as oEmail,o.phone as oPhone"
                + ",u.phone as userPhone,username,u.fullname as userFullname,u.address as userAddress,u.email as userEmail,p.name AS paymethod,os.name AS statusName "
                + "FROM orders AS o "
                + "LEFT JOIN users AS u "
                + "ON o.customer_id=u.id "
                + "INNER JOIN payments AS p "
                + "ON p.id=o.payment_id "
                + "INNER JOIN order_status AS os "
                + "ON os.id=o.status "
                + "WHERE o.id=?";
        return jdbcTemplate.query(sql, new Object[]{id_order},
                new ResultSetExtractor<List<Order>>() {
                    @Override
                    public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            User user = null;
                            if (rs.getInt("customer_id") == 0) {
                                user = new User(0, "", rs.getString("oFullname"), "", rs.getString("oPhone"), rs.getString("oEmail"), rs.getString("oAddress"), null, 1);
                            } else {
                                if (!"".equals(rs.getString("oAddress"))) {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("oPhone"), rs.getString("userEmail"), rs.getString("oAddress"), null, 1);
                                } else {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("userPhone"), rs.getString("userEmail"), rs.getString("userAddress"), null, 1);
                                }
                            }
                            listOrder.add(new Order(rs.getInt("id"), 0,
                                    user,
                                    new Payment_method(rs.getInt("payment_id"), rs.getString("paymethod")),
                                    rs.getInt("payment_status"),
                                    rs.getInt("coupon_value"),
                                    rs.getTimestamp("date_create"),
                                    new Order_status(rs.getInt("status"), rs.getString("statusName")), "",
                                    null)
                            );
                        }
                        return listOrder;
                    }
                }).get(0);
    }

    public int delItem(int id_order) {
        String sql1 = "DELETE FROM detail_order WHERE order_id=?";
        jdbcTemplate.update(sql1, new Object[]{id_order});
        String sql2 = "DELETE FROM orders WHERE id=?";
        return jdbcTemplate.update(sql2, new Object[]{id_order});
    }

    public int addItem(int id, int payment, String note, int payment_status, int coupon_value) {
        String sql = "INSERT INTO orders(customer_id,payment_id,note,fullname,email,address,phone,payment_status,coupon_value) VALUES(?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, new Object[]{id, payment, note, "", "", "", "", payment_status, coupon_value});

    }

    public int addItem(int id, int payment, String note, String address, String phone, int payment_status, int coupon_value) {
        String sql = "INSERT INTO orders(customer_id,payment_id,note,fullname,email,address,phone,payment_status,coupon_value) VALUES(?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, new Object[]{id, payment, note, "", "", address, phone, payment_status, coupon_value});
    }

    public int addItem(User objCustomer, int payment, String note, int payment_status, int coupon_value) {
        String sql = "INSERT INTO orders(payment_id,note,fullname,email,address,phone,payment_status,coupon_value) VALUES(?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, new Object[]{payment, note, objCustomer.getFullname(), objCustomer.getEmail(),
                objCustomer.getAddress(), objCustomer.getPhone(), payment_status, coupon_value});

    }

    public int getMaxId() {
        String sql = "SELECT MAX(id) FROM orders";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int addDetail_item(Detail_order item, int order_id) {
        String sql = "INSERT INTO detail_order(order_id,product_id,quantity) VALUES(?,?,?)";
        return jdbcTemplate.update(sql, new Object[]{order_id, item.getProduct().getId(), item.getQuantity()});
    }

    public List<Order> getItemByUser(User user) {
        List<Order> listOrder = new ArrayList<Order>();
        String sql = "SELECT o.id,customer_id,payment_id,note,payment_status,coupon_value,date_create,status,o.fullname as oFullname,o.address as oAddress,o.email as oEmail,o.phone as oPhone"
                + ",u.phone as userPhone,username,u.fullname as userFullname,u.address as userAddress,u.email as userEmail,p.name AS paymethod,os.name AS statusName "
                + "FROM orders AS o "
                + "left JOIN users AS u "
                + "ON o.customer_id=u.id "
                + "INNER JOIN payments AS p "
                + "ON p.id=o.payment_id "
                + "INNER JOIN order_status AS os "
                + "ON os.id=o.status WHERE customer_id=? "
                + "ORDER BY o.id DESC ";
        return (List<Order>) jdbcTemplate.query(sql, new Object[]{user.getId()},
                new ResultSetExtractor<List<Order>>() {
                    @Override
                    public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            User user = null;
                            if (rs.getInt("customer_id") == 0) {
                                user = new User(0, "", rs.getString("oFullname"), "", rs.getString("oPhone"), rs.getString("oEmail"), rs.getString("oAddress"), null, 1);
                            } else {
                                if (!"".equals(rs.getString("oAddress"))) {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("oPhone"), rs.getString("userEmail"), rs.getString("oAddress"), null, 1);
                                } else {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("userPhone"), rs.getString("userEmail"), rs.getString("userAddress"), null, 1);
                                }
                            }
                            listOrder.add(new Order(rs.getInt("id"), 0,
                                    user,
                                    new Payment_method(rs.getInt("payment_id"), rs.getString("paymethod")),
                                    rs.getInt("payment_status"),
                                    rs.getInt("coupon_value"),
                                    rs.getTimestamp("date_create"),
                                    new Order_status(rs.getInt("status"), rs.getString("statusName")), rs.getString("note"),
                                    null)
                            );
                        }
                        return listOrder;
                    }
                });
    }

    public int editItem(Order objOrder) {
        String sql = "UPDATE orders SET payment_status=?,status=?  WHERE id=?";
        return jdbcTemplate.update(sql, new Object[]{objOrder.getPayment_status(), objOrder.getStatus().getId(), objOrder.getId()});

    }

    public List<Order> getItemInDay(Calendar c) {
        List<Order> listOrder = new ArrayList<Order>();
        String sql = "SELECT o.id,customer_id,payment_id,payment_status,coupon_value,date_create,status,o.fullname as oFullname,o.address as oAddress,o.email as oEmail,o.phone as oPhone"
                + ",u.phone as userPhone,username,u.fullname as userFullname,u.address as userAddress,u.email as userEmail,p.name AS paymethod,os.name AS statusName "
                + "FROM orders AS o "
                + "left JOIN users AS u "
                + "ON o.customer_id=u.id "
                + "INNER JOIN payments AS p "
                + "ON p.id=o.payment_id "
                + "INNER JOIN order_status AS os "
                + "ON os.id=o.status "
                + "WHERE status=3 AND DAY(date_create)=? AND MONTH(date_create)=? AND YEAR(date_create)=? "
                + "ORDER BY o.id DESC ";
        return (List<Order>) jdbcTemplate.query(sql, new Object[]{c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR)},
                new ResultSetExtractor<List<Order>>() {
                    @Override
                    public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            User user = null;
                            if (rs.getInt("customer_id") == 0) {
                                user = new User(0, "", rs.getString("oFullname"), "", rs.getString("oPhone"), rs.getString("oEmail"), rs.getString("oAddress"), null, 1);
                            } else {
                                if (!"".equals(rs.getString("oAddress"))) {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("oPhone"), rs.getString("userEmail"), rs.getString("oAddress"), null, 1);
                                } else {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("userPhone"), rs.getString("userEmail"), rs.getString("userAddress"), null, 1);
                                }
                            }
                            listOrder.add(new Order(rs.getInt("id"), 0,
                                    user,
                                    new Payment_method(rs.getInt("payment_id"), rs.getString("paymethod")),
                                    rs.getInt("payment_status"),
                                    rs.getInt("coupon_value"),
                                    rs.getTimestamp("date_create"),
                                    new Order_status(rs.getInt("status"), rs.getString("statusName")), "",
                                    null)
                            );
                        }
                        return listOrder;
                    }
                });
    }

    public List<Order> getItemInMonth(Calendar c) {
        List<Order> listOrder = new ArrayList<Order>();
        String sql = "SELECT o.id,customer_id,payment_id,payment_status,coupon_value,date_create,status,o.fullname as oFullname,o.address as oAddress,o.email as oEmail,o.phone as oPhone"
                + ",u.phone as userPhone,username,u.fullname as userFullname,u.address as userAddress,u.email as userEmail,p.name AS paymethod,os.name AS statusName "
                + "FROM orders AS o "
                + "left JOIN users AS u "
                + "ON o.customer_id=u.id "
                + "INNER JOIN payments AS p "
                + "ON p.id=o.payment_id "
                + "INNER JOIN order_status AS os "
                + "ON os.id=o.status "
                + "WHERE status=3 AND MONTH(date_create)=? AND YEAR(date_create)=? "
                + "ORDER BY o.id DESC ";
        return (List<Order>) jdbcTemplate.query(sql, new Object[]{c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR)},
                new ResultSetExtractor<List<Order>>() {
                    @Override
                    public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            User user = null;
                            if (rs.getInt("customer_id") == 0) {
                                user = new User(0, "", rs.getString("oFullname"), "", rs.getString("oPhone"), rs.getString("oEmail"), rs.getString("oAddress"), null, 1);
                            } else {
                                if (!"".equals(rs.getString("oAddress"))) {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("oPhone"), rs.getString("userEmail"), rs.getString("oAddress"), null, 1);
                                } else {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("userPhone"), rs.getString("userEmail"), rs.getString("userAddress"), null, 1);
                                }
                            }
                            listOrder.add(new Order(rs.getInt("id"), 0,
                                    user,
                                    new Payment_method(rs.getInt("payment_id"), rs.getString("paymethod")),
                                    rs.getInt("payment_status"),
                                    rs.getInt("coupon_value"),
                                    rs.getTimestamp("date_create"),
                                    new Order_status(rs.getInt("status"), rs.getString("statusName")), "",
                                    null)
                            );
                        }
                        return listOrder;
                    }
                });
    }

    public List<Order> getItemInYear(Integer year) {
        List<Order> listOrder = new ArrayList<Order>();
        String sql = "SELECT o.id,customer_id,payment_id,payment_status,coupon_value,date_create,status,o.fullname as oFullname,o.address as oAddress,o.email as oEmail,o.phone as oPhone"
                + ",u.phone as userPhone,username,u.fullname as userFullname,u.address as userAddress,u.email as userEmail,p.name AS paymethod,os.name AS statusName "
                + "FROM orders AS o "
                + "left JOIN users AS u "
                + "ON o.customer_id=u.id "
                + "INNER JOIN payments AS p "
                + "ON p.id=o.payment_id "
                + "INNER JOIN order_status AS os "
                + "ON os.id=o.status "
                + "WHERE status=3 AND YEAR(date_create)=? "
                + "ORDER BY o.id DESC ";
        return (List<Order>) jdbcTemplate.query(sql, new Object[]{year},
                new ResultSetExtractor<List<Order>>() {
                    @Override
                    public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            User user = null;
                            if (rs.getInt("customer_id") == 0) {
                                user = new User(0, "", rs.getString("oFullname"), "", rs.getString("oPhone"), rs.getString("oEmail"), rs.getString("oAddress"), null, 1);
                            } else {
                                if (!"".equals(rs.getString("oAddress"))) {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("oPhone"), rs.getString("userEmail"), rs.getString("oAddress"), null, 1);
                                } else {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("userPhone"), rs.getString("userEmail"), rs.getString("userAddress"), null, 1);
                                }
                            }
                            listOrder.add(new Order(rs.getInt("id"), 0,
                                    user,
                                    new Payment_method(rs.getInt("payment_id"), rs.getString("paymethod")),
                                    rs.getInt("payment_status"),
                                    rs.getInt("coupon_value"),
                                    rs.getTimestamp("date_create"),
                                    new Order_status(rs.getInt("status"), rs.getString("statusName")), "",
                                    null)
                            );
                        }
                        return listOrder;
                    }
                });
    }

    public int countItemsByKeyWord(String keyword, int property_search) {
        String tmp = "";
        switch (property_search) {
            case 1:
                tmp = " u.fullname LIKE ? or o.fullname LIKE ? ";
                break;
            case 2:
                tmp = " u.address LIKE ? or o.address LIKE ?";
                break;
            default:
                break;
        }
        String sql = "SELECT COUNT(*) total "
                + "FROM orders AS o "
                + "LEFT JOIN users AS u "
                + "ON o.customer_id=u.id "
                + "INNER JOIN payments AS p "
                + "ON p.id=o.payment_id "
                + "INNER JOIN order_status AS os "
                + "ON os.id=o.status "
                + "WHERE " + tmp;
        return jdbcTemplate.queryForObject(sql, new Object[]{"%" + keyword + "%", "%" + keyword + "%"}, Integer.class);
    }

    public List<Order> getItemsPaginationByKeyWord(int offset, String keyword, int property_search) {
        String tmp = "";
        switch (property_search) {
            case 1:
                tmp = " u.fullname LIKE ? or o.fullname LIKE ? ";
                break;
            case 2:
                tmp = " u.address LIKE ? or o.address LIKE ?";
                break;
            default:
                break;
        }
        List<Order> listOrder = new ArrayList<Order>();
        String sql = "SELECT o.id,customer_id,payment_id,payment_status,coupon_value,date_create,status,o.fullname as oFullname,o.address as oAddress,o.email as oEmail,o.phone as oPhone"
                + ",u.phone as userPhone,username,u.fullname as userFullname,u.address as userAddress,u.email as userEmail,p.name AS paymethod,os.name AS statusName "
                + "FROM orders AS o "
                + "left JOIN users AS u "
                + "ON o.customer_id=u.id "
                + "INNER JOIN payments AS p "
                + "ON p.id=o.payment_id "
                + "INNER JOIN order_status AS os "
                + "ON os.id=o.status "
                + "WHERE " + tmp
                + "ORDER BY o.id DESC LIMIT ?,?";
        return (List<Order>) jdbcTemplate.query(sql, new Object[]{"%" + keyword + "%", "%" + keyword + "%", offset, PageDefine.ADMIN_ROW_COUNT},
                new ResultSetExtractor<List<Order>>() {
                    @Override
                    public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            User user = null;
                            if (rs.getInt("customer_id") == 0) {
                                user = new User(0, "", rs.getString("oFullname"), "", rs.getString("oPhone"), rs.getString("oEmail"), rs.getString("oAddress"), null, 1);
                            } else {
                                if (!"".equals(rs.getString("oAddress"))) {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("oPhone"), rs.getString("userEmail"), rs.getString("oAddress"), null, 1);
                                } else {
                                    user = new User(rs.getInt("customer_id"), rs.getString("username"), rs.getString("userFullname"), "", rs.getString("userPhone"), rs.getString("userEmail"), rs.getString("userAddress"), null, 1);
                                }
                            }
                            listOrder.add(new Order(rs.getInt("id"), 0,
                                    user,
                                    new Payment_method(rs.getInt("payment_id"), rs.getString("paymethod")),
                                    rs.getInt("payment_status"),
                                    rs.getInt("coupon_value"),
                                    rs.getTimestamp("date_create"),
                                    new Order_status(rs.getInt("status"), rs.getString("statusName")), "",
                                    null)
                            );
                        }
                        return listOrder;
                    }
                });
    }
}
