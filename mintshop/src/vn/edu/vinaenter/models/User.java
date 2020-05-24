package vn.edu.vinaenter.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {
    private int id;
    @NotEmpty(message = "Tên đăng nhập không được rỗng")
    private String username;
    @NotEmpty(message = "Họ tên không được rỗng")
    private String fullname;
    //@NotEmpty(message="Mật khẩu không được rỗng")
    private String password;
    private String phone;
    @Email
    @NotEmpty(message = "Email không được rỗng")
    private String email;
    @NotEmpty(message = "Địa chỉ không được rỗng")
    private String address;
    private Role role;
    private int active;

    public User(int id, String username, String fullname, String password, String phone, String email, String address,
                Role role, int active) {
        super();
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.active = active;
    }

    public User() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", fullname=" + fullname + ", password=" + password
                + ", phone=" + phone + ", email=" + email + ", address=" + address + ", role=" + role + ", active="
                + active + "]";
    }
}
