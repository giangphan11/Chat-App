package phanbagiang.com.chatapp.model;

import java.io.Serializable;

public class User {
    private String id;
    private String image;
    private String name;
    private String mail;
    private String status;

    public User() {
    }

    public User(String id, String image, String name, String mail, String status) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.mail = mail;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
