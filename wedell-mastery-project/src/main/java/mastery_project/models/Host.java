package mastery_project.models;

import java.math.BigDecimal;

public class Host {
    //Fields
    private String lastName;
    private String email;
    private String id;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private int postalCode;
    private BigDecimal standardRate;
    private BigDecimal weekendRate;

    //Constructors
    public Host() {
    }

    public Host(String lastName, String email){
        this.lastName = lastName;
        this.email = email;
    }

    //Getters
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getId() {
        return id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public int getPostalCode() {
        return postalCode;
    }
    public BigDecimal getStandardRate() {
        return standardRate;
    }
    public BigDecimal getWeekendRate() {
        return weekendRate;
    }

    //Setters
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
    public void setStandardRate(BigDecimal standardRate) {
        this.standardRate = standardRate;
    }
    public void setWeekendRate(BigDecimal weekendRate) {
        this.weekendRate = weekendRate;
    }
}
