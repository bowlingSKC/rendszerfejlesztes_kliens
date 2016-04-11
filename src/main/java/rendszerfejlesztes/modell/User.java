package rendszerfejlesztes.modell;

import rendszerfejlesztes.Constants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class User {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private Integer privilage;
    private List<Ticket> tickets;
    private List<Subscription> subscriptions;

    public User() {

    }

    public User(Integer id) {
        this.id = id;
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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Subscription> getSubscriptions() {return subscriptions;}

    public void setSubscriptions(List<Subscription> subscriptions) {this.subscriptions = subscriptions;}

    public String toNiceString() {
        if(this .privilage == 1){
            return  "Name: " + name +
                    " Email: " + email +
                    " Privilage: user";
        }else{
            return  "Name: " + name +
                    " Email: " + email +
                    " Privilage: admin";
        }
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
