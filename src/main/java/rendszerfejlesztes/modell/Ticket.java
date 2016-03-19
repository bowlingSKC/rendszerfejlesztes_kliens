package rendszerfejlesztes.modell;

import rendszerfejlesztes.Constants;
import rendszerfejlesztes.service.TicketManager;
import rendszerfejlesztes.service.impl.TicketManagerImpl;

import java.util.Date;

public class Ticket {


    private Integer id;
    private Sector sector;
    private User user;
    private Date bookedTime;
    private boolean paid;
    private Integer row;
    private Integer col;
    private Integer status;

    public Ticket() {

    }

    public Ticket(Sector sector, User user, Date bookedTime, boolean paid, Integer row, Integer col, Integer status) {
        this.sector = sector;
        this.user = user;
        this.bookedTime = bookedTime;
        this.paid = paid;
        this.row = row;
        this.col = col;
        this.status = status;
    }

    public Ticket(Sector sector, User user, Integer row, Integer col) {
        this.sector = sector;
        this.user = user;
        this.bookedTime = new Date();
        this.paid = false;
        this.row = row;
        this.col = col;
        this.status = Constants.TICKET_OPTIONAL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(Date bookedTime) {
        this.bookedTime = bookedTime;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
