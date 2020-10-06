package mastery_project.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    //Fields
    Host host;
    Guest guest;
    LocalDate startDate;
    LocalDate endDate;
    BigDecimal cost;
    int id;

    //Getters
    public Host getHost() {
        return host;
    }
    public Guest getGuest() {
        return guest;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public BigDecimal getCost() {
        return cost;
    }
    public int getId() {
        return id;
    }

    //Setters
    public void setHost(Host host) {
        this.host = host;
    }
    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    public void setId(int id) {
        this.id = id;
    }
}
