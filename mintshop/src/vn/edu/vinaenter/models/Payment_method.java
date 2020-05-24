package vn.edu.vinaenter.models;

public class Payment_method {
    private int id;
    private String name;

    public Payment_method(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Payment_method() {
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
