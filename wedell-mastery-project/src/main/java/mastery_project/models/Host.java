package mastery_project.models;

import java.math.BigDecimal;

public class Host {
    //Fields
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private BigDecimal weekdayRate;
    private BigDecimal weekendRate;

    //Constructors
    public Host() {
    }

    public Host(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    //Getters
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getId() {
        return id;
    }
    public BigDecimal getWeekdayRate() {
        return weekdayRate;
    }
    public BigDecimal getWeekendRate() {
        return weekendRate;
    }

    //Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setWeekdayRate(BigDecimal weekdayRate) {
        this.weekdayRate = weekdayRate;
    }
    public void setWeekendRate(BigDecimal weekendRate) {
        this.weekendRate = weekendRate;
    }
}
