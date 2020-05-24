package vn.edu.vinaenter.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Coupon {
    private int id;
    @NotEmpty(message = "Vui lòng nhập tên mã")
    private String name;
    @NotNull(message = "Vui lòng nhập số lượng")
    @Min(value = 0, message = "Số lượng không được nhỏ hơn 0")
    private int quantity;
    private int value;

    public Coupon(int id, String name, int quantity, int value) {
        super();
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.value = value;
    }

    public Coupon() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
