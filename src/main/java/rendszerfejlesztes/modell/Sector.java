package rendszerfejlesztes.modell;

import java.util.List;

public class Sector {

    private Integer id;
    private Integer numOfRows;
    private Integer numOfCols;
    private Integer price;
    private Event event;
    private Integer depth;
    private List<Ticket> tickets;

    public Sector() {

    }

    public Sector(Integer numOfRows, Integer numOfCols, Integer price, Event event, Integer depth) {
        this.numOfRows = numOfRows;
        this.numOfCols = numOfCols;
        this.price = price;
        this.event = event;
        this.depth = depth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    public Integer getNumOfCols() {
        return numOfCols;
    }

    public void setNumOfCols(Integer numOfCols) {
        this.numOfCols = numOfCols;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
