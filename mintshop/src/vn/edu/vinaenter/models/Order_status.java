package vn.edu.vinaenter.models;

public class Order_status {
    private int id;
    private String name;

    public Order_status(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Order_status() {
        super();
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

}
