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

    public Sector(Integer id) {
        this.id = id;
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

    public boolean isSeatReserved(int col, int row) {
        for(Ticket ticket : tickets) {
            if( ticket.getCol() == col && ticket.getRow() == row ) {
                return true;
            }
        }
        return false;
    }

    public void showSeats() {
        for(int i = 1; i <= numOfCols; i++) {
            System.out.print("  " + i);
        }
        System.out.println();

        for(int i = 1; i <= numOfRows; i++) {
            System.out.print(i + " ");
            for(int j = 1; j <= numOfCols; j++) {
                if( isSeatReserved(j, i) ) {
                    System.out.print("X  ");
                } else {
                    System.out.print("O  ");
                }
            }
            System.out.println();
        }
    }
}
