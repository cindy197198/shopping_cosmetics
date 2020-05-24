package vn.edu.vinaenter.models;

public class Role {
    private int id;
    private String name;

    public Role(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Role() {
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

    @Override
    public String toString() {
        return "Role [roleId=" + id + ", name=" + name + "]";
    }

}
