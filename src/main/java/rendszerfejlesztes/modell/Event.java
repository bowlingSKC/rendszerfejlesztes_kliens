package rendszerfejlesztes.modell;

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

}
