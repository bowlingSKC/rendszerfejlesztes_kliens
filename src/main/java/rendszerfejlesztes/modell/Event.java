package rendszerfejlesztes.modell;

import rendszerfejlesztes.Constants;

import java.util.Date;
import java.util.List;

public class Event {

    private Integer id;
    private String name;
    private Date start;
    private Integer duration;
    private String description;
    private Location location;
    private Performer performer;
    private List<Sector> sectorList;
    private List<Subscription> subscriptions;
    private boolean seats;

    public Event() {
    }

    public Event(String name, Date start, Integer duration, String description) {
        this.name = name;
        this.start = start;
        this.duration = duration;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Performer getPerformer() {
        return performer;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }

    public List<Sector> getSectorList() {
        return sectorList;
    }

    public void setSectorList(List<Sector> sectorList) {
        this.sectorList = sectorList;
    }

    public boolean isSeats() {
        return seats;
    }

    public void setSeats(boolean seats) {
        this.seats = seats;
    }

    public List<Subscription> getSubscriptions() {return subscriptions;}

    public void setSubscriptions(List<Subscription> subscriptions) {this.subscriptions = subscriptions;}

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                ", location=" + location +
                '}';
    }

    public String printPath(){
        Date befejez = new Date();
        befejez.setTime(start.getTime() + 1000*60*duration);
        return "Esemeny: " + name + "\tKezdes: " + Constants.DATE_FORMAT.format(start) +
                "\tVarhato befejezes: " + Constants.DATE_FORMAT.format(befejez) +
                "\tHossz: " + duration + " perc\tHelyszin: " + location.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (name != null ? !name.equals(event.name) : event.name != null) return false;
        if (start != null ? !start.equals(event.start) : event.start != null) return false;

        return true;
    }

}
