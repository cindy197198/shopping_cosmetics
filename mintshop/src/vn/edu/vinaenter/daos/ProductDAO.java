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

import com.mysql.jdbc.PreparedStatement;

import vn.edu.vinaenter.defines.PageDefine;
import vn.edu.vinaenter.models.Category;
import vn.edu.vinaenter.models.Detail_order;
import vn.edu.vinaenter.models.Product;
import vn.edu.vinaenter.models.User;

@Repository
public class ProductDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> getItemsPagination(int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "ORDER BY p.id DESC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{offset, PageDefine.ADMIN_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public int countItems() {
        String sql = "SELECT COUNT(*) total "
                + "FROM categories AS c "
                + "INNER JOIN products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId ";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int addItem(Product objProduct) {
        String sql = "INSERT INTO products(name,catId,userId,price,newprice,detail,brand,description,date_create,picture,amount) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, new Object[]{objProduct.getName(),
                objProduct.getCat().getId(),
                objProduct.getUser().getId(),
                objProduct.getPrice(),
                objProduct.getNewprice(),
                objProduct.getDetail(),
                objProduct.getBrand(),
                objProduct.getDescription(),
                objProduct.getDate_create(),
                objProduct.getPicture(),
                objProduct.getAmount()});
    }

    public int delItem(int id_Product) {
        String sql = "DELETE FROM Products WHERE id=?";
        return jdbcTemplate.update(sql, new Object[]{id_Product});
    }

    public Product getItem(int id_Product) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE p.id=?";
        return jdbcTemplate.query(sql,
                new Object[]{id_Product},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                }).get(0);
    }

    public int editItem(Product objProduct) {
        String sql = "UPDATE Products SET name=?,catId=?,userId=?,price=?,newprice=?,detail=?,brand=?,description=?,picture=? WHERE id=?";
        return jdbcTemplate.update(sql, new Object[]{objProduct.getName(),
                objProduct.getCat().getId(),
                objProduct.getUser().getId(),
                objProduct.getPrice(),
                objProduct.getNewprice(),
                objProduct.getDetail(),
                objProduct.getBrand(),
                objProduct.getDescription(),
                objProduct.getPicture(),
                objProduct.getId()});
    }

    public int countItemsByKeyWord(String keyword) {
        String sql = "SELECT COUNT(*) total "
                + "FROM categories AS c "
                + "INNER JOIN products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE p.name LIKE ? or brand LIKE ? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{"%" + keyword + "%", "%" + keyword + "%"}, Integer.class);
    }

    public List<Product> getItemsPaginationByKeyWord(int offset, String keyword, int property_search) {
        List<Product> listProduct = new ArrayList<Product>();
        String tmp = "";
        switch (property_search) {
            case 1:
                tmp = "p.name";
                break;
            case 2:
                tmp = "u.username";
                break;
            case 3:
                tmp = "brand";
                break;
            case 4:
                tmp = "c.name";
                break;
            default:
                break;
        }
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE " + tmp + " LIKE ? ORDER BY p.id DESC LIMIT ?,? ";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{"%" + keyword + "%", offset, PageDefine.ADMIN_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public List<Product> getItemsNew(int catId) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "ORDER BY p.id DESC";
        return (List<Product>) jdbcTemplate.query(sql,
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            Product item = new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount"));
                            if (getParentCat(item.getCat().getId()) == catId) {
                                listProduct.add(item);
                            }
                        }
                        return listProduct;
                    }

                    private int getParentCat(int id) {
                        String sql = "SELECT parent_id FROM categories WHERE id=?";
                        int j = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
                        if (j == 0) return id;
                        else return getParentCat(j);
                    }
                });
    }

    public List<Product> getItemDeal(int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE newprice !=0 && amount>0 "
                + "ORDER BY p.id DESC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public List<Product> getRelatedItems(Product objProduct, int i) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE (p.catId=? or c.parent_id=? or brand=?) && p.id!=? && amount>0 "
                + "ORDER BY p.id DESC LIMIT ?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{objProduct.getCat().getId(), objProduct.getCat().getId(), objProduct.getBrand(), objProduct.getId(), i},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public int countItemsByCat(int cid) {
        String sql = "SELECT COUNT(*) total "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catid WHERE (p.catid=? or c.parent_id=?)&& p.amount>0 ";
        return jdbcTemplate.queryForObject(sql, new Object[]{cid, cid}, Integer.class);
    }

    public List<Product> getItemsPaginationByCat(int cid, int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE (p.catid=? or c.parent_id=?)&& p.amount>0 "
                + "ORDER BY p.id DESC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{cid, cid, offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public void editImage(int id_product, String str) {
        String sql = "UPDATE products SET picture=? WHERE id=?";
        jdbcTemplate.update(sql, new Object[]{str, id_product});

    }

    public void decAmount(Product item, int quantity) {
        String sql = "UPDATE products SET amount=amount-? WHERE id=?";
        jdbcTemplate.update(sql, new Object[]{quantity, item.getId()});

    }

    public int countItemsDeal() {
        String sql = "SELECT COUNT(*) total "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catid WHERE newprice !=0 && amount>0 ";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Product> getItemsPaginationByCatOrderASC(int cid, int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE (p.catid=? or c.parent_id=?)&& p.amount>0 "
                + "ORDER BY p.id ASC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{cid, cid, offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public List<Product> getItemsPaginationByCatOrderPriceDESC(int cid, int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE (p.catid=? or c.parent_id=?)&& p.amount>0 "
                + "ORDER BY p.price DESC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{cid, cid, offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public List<Product> getItemsPaginationByCatOrderPriceASC(int cid, int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE (p.catid=? or c.parent_id=?)&& p.amount>0 "
                + "ORDER BY p.price ASC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{cid, cid, offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public List<Product> getItemDealOrderASC(int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE newprice !=0 && amount>0 "
                + "ORDER BY p.id ASC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public List<Product> getItemDealOrderPriceDESC(int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE newprice !=0 && amount>0 "
                + "ORDER BY p.price DESC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public List<Product> getItemDealOrderPriceASC(int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE newprice !=0 && amount>0 "
                + "ORDER BY p.price ASC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public int countItemsByKeyWord(int cid, String keyword) {
        String sql = "SELECT COUNT(*) total "
                + "FROM categories AS c "
                + "INNER JOIN products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE p.name LIKE ? and (p.catId=? or c.parent_id=?) AND amount>0 ";
        return jdbcTemplate.queryForObject(sql, new Object[]{"%" + keyword + "%", cid, cid}, Integer.class);
    }

    public List<Product> getItemByKeyWord(int cid, String keyword, int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE p.name LIKE ? and (p.catId=? or c.parent_id=?) and amount>0 "
                + "ORDER BY p.id DESC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{"%" + keyword + "%", cid, cid, offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public int countItemsByBrand(int cid, String keyword) {
        String sql = "SELECT COUNT(*) total "
                + "FROM categories AS c "
                + "INNER JOIN products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE p.brand LIKE ? and (p.catId=? or c.parent_id=?) and amount>0";
        return jdbcTemplate.queryForObject(sql, new Object[]{"%" + keyword + "%", cid, cid}, Integer.class);
    }

    public List<Product> getItemByBrand(int cid, String keyword, int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE p.brand LIKE ? and (p.catId=? or c.parent_id=?) and amount>0 "
                + "ORDER BY p.id DESC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{"%" + keyword + "%", cid, cid, offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public int countItemsByPrice(int cid, int lowprice, int highprice) {
        String sql = "SELECT COUNT(*) total "
                + "FROM categories AS c "
                + "INNER JOIN products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE (p.catId=? or c.parent_id=?) and p.price>?  and p.price<? and amount>0";
        return jdbcTemplate.queryForObject(sql, new Object[]{cid, cid, lowprice, highprice}, Integer.class);
    }

    public List<Product> getItemByPrice(int cid, int lowprice, int highprice, int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE (p.catId=? or c.parent_id=?) and p.price>?  and p.price<? and amount>0 "
                + "ORDER BY p.id DESC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{cid, cid, lowprice, highprice, offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public List<Product> getItemByKeyWordAll(String keyword, int offset) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE (p.name LIKE ? or brand LIKE ?) and amount>0 "
                + "ORDER BY p.id DESC LIMIT ?,?";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{"%" + keyword + "%", "%" + keyword + "%", offset, PageDefine.PUBLIC_CAT_ROW_COUNT},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            listProduct.add(new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount")));
                        }
                        return listProduct;
                    }
                });
    }

    public List<Product> getItemsNew(int cat_id, String keyword) {
        List<Product> listProduct = new ArrayList<Product>();
        String sql = "SELECT p.id,p.name as pname,catId,userId,price,newprice,detail,brand,description,date_create,amount,picture,c.name AS catName,u.username "
                + "FROM categories AS c "
                + "INNER JOIN Products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId WHERE (p.name LIKE ? or brand LIKE ?) and amount>0 "
                + "ORDER BY p.id DESC";
        return (List<Product>) jdbcTemplate.query(sql,
                new Object[]{"%" + keyword + "%", "%" + keyword + "%"},
                new ResultSetExtractor<List<Product>>() {
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            Product item = new Product(rs.getInt("id"),
                                    rs.getString("pname"),
                                    new Category(rs.getInt("catId"), rs.getString("catName"), 0),
                                    new User(rs.getInt("userid"), rs.getString("username"), "", "", "", "", "", null, 1),
                                    rs.getString("picture"),
                                    rs.getDouble("price"),
                                    rs.getDouble("newprice"),
                                    rs.getString("description"),
                                    rs.getString("detail"),
                                    rs.getString("brand"),
                                    rs.getTimestamp("date_create"),
                                    rs.getInt("amount"));
                            if (getParentCat(item.getCat().getId()) == cat_id) {
                                listProduct.add(item);
                            }
                        }
                        return listProduct;
                    }

                    private int getParentCat(int id) {
                        String sql = "SELECT parent_id FROM categories WHERE id=?";
                        int j = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
                        if (j == 0) return id;
                        else return getParentCat(j);
                    }
                });
    }

    public int countItemsByKeyWord(String keyword, int property_search) {
        String tmp = "";
        switch (property_search) {
            case 1:
                tmp = "p.name";
                break;
            case 2:
                tmp = "u.username";
                break;
            case 3:
                tmp = "brand";
                break;
            case 4:
                tmp = "c.name";
                break;
            default:
                break;
        }
        String sql = "SELECT COUNT(*) total "
                + "FROM categories AS c "
                + "INNER JOIN products AS p "
                + "ON c.id=p.catId "
                + "INNER JOIN users AS u "
                + "ON u.id=p.userId "
                + "WHERE " + tmp + " LIKE ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{"%" + keyword + "%"}, Integer.class);
    }

}
