package vn.edu.vinaenter.models;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Product {
    private int id;
    @NotNull(message = "Vui lòng nhập giá sản phẩm")
    @Min(value = 0, message = "Giá sản phẩm không được nhỏ hơn 0")
    private double price;
    private double newprice;
    @NotEmpty(message = "Tên sản phẩm không được rỗng")
    private String name;
    private Category cat;
    private User user;
    private String picture;
    @NotEmpty(message = "Mô tả không được rỗng")
    private String description;
    @NotEmpty(message = "Chi tiết không được rỗng")
    private String detail;
    private String brand;
    private Timestamp date_create;
    @NotNull(message = "Vui lòng nhập số lượng")
    @Min(value = 0, message = "Số lượng không được nhỏ hơn 0")
    private int amount;

    public Product(int id, String name, Category cat, User user, String picture, double price, double newprice,
                   String description, String detail, String brand, Timestamp date_create, int amount) {
        super();
        this.id = id;
        this.name = name;
        this.cat = cat;
        this.user = user;
        this.picture = picture;
        this.price = price;
        this.newprice = newprice;
        this.description = description;
        this.detail = detail;
        this.brand = brand;
        this.date_create = date_create;
        this.amount = amount;
    }

    public Product() {
        super();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getNewprice() {
        return newprice;
    }

    public void setNewprice(double newprice) {
        this.newprice = newprice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Timestamp getDate_create() {
        return date_create;
    }

    public void setDate_create(Timestamp date_create) {
        this.date_create = date_create;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", price=" + price + ", newprice=" + newprice + ", name=" + name + ", cat=" + cat
                + ", user=" + user + ", picture=" + picture + ", description=" + description + ", detail=" + detail
                + ", brand=" + brand + ", date_create=" + date_create + "]";
    }

}
