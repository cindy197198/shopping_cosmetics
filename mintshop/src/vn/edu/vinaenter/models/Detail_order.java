package vn.edu.vinaenter.models;

public class Detail_order {
    private int id;
    private int order_id;
    private Product product;
    private int quantity;

    public Detail_order(int id, int order_id, Product product, int quantity) {
        super();
        this.id = id;
        this.order_id = order_id;
        this.product = product;
        this.quantity = quantity;
    }

    public Detail_order() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Detail_order [id=" + id + ", order_id=" + order_id + ", product=" + product + ", quantity=" + quantity
                + "]";
    }

}
