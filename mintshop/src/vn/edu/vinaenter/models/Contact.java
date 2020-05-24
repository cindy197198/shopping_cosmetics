package vn.edu.vinaenter.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class Contact {
    private int id;
    @NotEmpty(message = "Vui lòng nhập họ tên")
    private String fullname;
    @NotEmpty(message = "Vui lòng nhập email")
    @Email(message = "Email không đúng định dạng")
    private String email;
    @NotEmpty(message = "Vui lòng nhập chủ đề")
    private String subject;
    @NotEmpty(message = "Vui lòng nhập nội dung")
    private String content;
    private String date_create;

    public Contact(int id, String fullname, String email, String subject, String content, String date_create) {
        super();
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.subject = subject;
        this.content = content;
        this.date_create = date_create;
    }

    public Contact() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
