package rendszerfejlesztes.modell;


public class Subscription {
    private Integer id;
    private User user;
    private Event event;

    public Subscription() {

    }

    public Subscription(User user, Event event) {
        this.user = user;
        this.event = event;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", user=" + user +
                ", event=" + event +
                '}';
    }
}
