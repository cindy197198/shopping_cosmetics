package vn.edu.vinaenter.models;

import org.hibernate.validator.constraints.NotEmpty;

public class Category {
    private int id;
    @NotEmpty(message = "Tên danh mục không được rỗng")
    private String name;
    private int parent_id;

    public Category(int id, String name, int parent_id) {
        super();
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
    }

    public Category() {
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", parent_id=" + parent_id + "]";
    }
}
