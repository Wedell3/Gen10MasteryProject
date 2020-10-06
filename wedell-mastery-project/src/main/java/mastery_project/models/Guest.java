package mastery_project.models;

public class Guest {
    //Fields
    private String firstName;
    private String lastName;
    private String email;
    private String state;
    private int id;
    private String phoneNumber;

    //Constructors
    public Guest() {
    }

    public Guest(String firstName, String lastName, String email) {
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
    public String getState() {
        return state;
    }
    public int getId() {
        return id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
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
    public void setState(String state) {
        this.state = state;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
