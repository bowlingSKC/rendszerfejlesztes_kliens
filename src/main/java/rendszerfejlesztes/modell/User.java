package rendszerfejlesztes.modell;

import rendszerfejlesztes.Constants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class User {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private Integer privilage;

    public User() {

    }

    public User(String name, String email, String password) {
        this(name, email, password, Constants.USER_PRIVILAGE_ID);
    }

    public User(String name, String email, String password, Integer privilage) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.privilage = privilage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPrivilage() {
        return privilage;
    }

    public void setPrivilage(Integer privilage) {
        this.privilage = privilage;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", privilage=" + privilage +
                '}';
    }
}
