package vn.edu.vinaenter.models;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int id;
    private double total;
    private User customer;
    private Payment_method pay_method;
    private int payment_status;
    private Timestamp date_create;
    private Order_status status;
    private String note;
    private List<Detail_order> listProduct;
    private int coupon_value;

    public Order(int id, double total, User customer, Payment_method pay_method, int payment_status, int coupon_value, Timestamp date_create,
                 Order_status status, String note, List<Detail_order> listProduct) {
        super();
        this.id = id;
        this.total = total;
        this.customer = customer;
        this.pay_method = pay_method;
        this.payment_status = payment_status;
        this.date_create = date_create;
        this.status = status;
        this.note = note;
        this.listProduct = listProduct;
        this.coupon_value = coupon_value;
    }

    public Order() {
        super();
    }

    public int getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(int coupon_value) {
        this.coupon_value = coupon_value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Detail_order> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Detail_order> listProduct) {
        this.listProduct = listProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Payment_method getPay_method() {
        return pay_method;
    }

    public void setPay_method(Payment_method pay_method) {
        this.pay_method = pay_method;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }

    public Timestamp getDate_create() {
        return date_create;
    }

    public void setDate_create(Timestamp date_create) {
        this.date_create = date_create;
    }

    public Order_status getStatus() {
        return status;
    }

    public void setStatus(Order_status status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", customer=" + customer + ", pay_method=" + pay_method + ", payment_status="
                + payment_status + ", date_create=" + date_create + ", status=" + status + "]";
    }

}
