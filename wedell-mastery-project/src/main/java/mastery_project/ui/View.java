package mastery_project.ui;

import mastery_project.domain.Result;
import mastery_project.models.Guest;
import mastery_project.models.Host;
import mastery_project.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class View {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void displayWelcome() {
        displayHeader("Welcome to the Don't Wreck My House Management Application");
    }

    public void displayGoodbye() {
        displayHeader("Shutting Down");
        System.out.println("Good Bye");
    }

    public MainMenuOption getMenuSelection() {
        displayMainMenu();
        int value = readInt(String.format("Enter a number [%s-%s]: ", 0, MainMenuOption.values().length - 1), 0, MainMenuOption.values().length - 1);
        return MainMenuOption.getMainMenuOptionFromValue(value);
    }

    public void displayMainMenu() {
        System.out.println();
        displayHeader("Main Menu");
        for(MainMenuOption option : MainMenuOption.values()) {
            System.out.printf("[%s] %s%n", option.getValue(), option.getMessage());
        }
    }

    public ManageAccountsOption getAccountsOption() {
        displayAccountManagementMenu();
        int value = readInt(String.format("Enter a number [%s-%s]: ", 0, ManageAccountsOption.values().length - 1), 0, ManageAccountsOption.values().length - 1);
        return ManageAccountsOption.getOptionFromValue(value);
    }

    public void displayAccountManagementMenu() {
        System.out.println();
        displayHeader("Account Management Menu");
        for(ManageAccountsOption option : ManageAccountsOption.values()) {
            System.out.printf("[%s] %s%n", option.getValue(), option.getMessage());
        }
    }

    public void displayHeader(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    public void displayAccountNotFound(Host host, Guest guest) {
        if(guest == null) {
            System.out.println("Guest not found in system");
        }
        if(host == null) {
            System.out.println("Host not found in system");
        }
    }

    public void displayAccountNotFound(Host host) {
        displayAccountNotFound(host, new Guest());
    }

    public void displayAccountNotFound(Guest guest) {
        displayAccountNotFound(new Host(), guest);
    }

    public void displayReservations(List<Reservation> reservations, Host host) {
        if(host == null){
            return;
        }
        displayHeader(host.getLastName() + ": " + host.getCity() + " " + host.getState());
        displayHeader(reservationLineFormat("ID  ", "Check In - Check Out  ", "        Guest Name", "            Guest Email"));
        if(reservations.size() == 0) {
            System.out.println("Open Availability");
        }else {
            for (Reservation r : reservations) {
                System.out.println(reservationToLine(r));
            }
        }
        System.out.println();
    }

    public void displayHosts(List<Host> hosts) {
        if(hosts.size() == 0) {
            System.out.println("No Hosts Registered");
        }else {
            displayHeader(makeHostHeader());
            for(Host h : hosts) {
                System.out.println(hostToLine(h));
            }
        }
    }

    public void displayGuests(List<Guest> guests) {
        if(guests.size() == 0) {
            System.out.println("No Guests Registered");
        } else {
            displayHeader(makeGuestHeader());
            for(Guest g : guests) {
                System.out.println(guestToLine(g));
            }
        }
    }

    public void displayResult(Result<Reservation> result, String operation){
        if(result.isSuccess()) {
            displayHeader("SUCCESS");
            System.out.println("Reservation " + result.getPayload().getId() + " " + operation + ".");
        }
        else {
            displayHeader("Failure");
            for(String message : result.getErrorMessages()) {
                System.out.println(message);
            }
        }
        System.out.println();
    }

    public void displayResult(Result<Reservation> result, int id, String operation){
        if(result.isSuccess()) {
            displayHeader("SUCCESS");
            System.out.println("Reservation " + id + " " + operation + ".");
        }
        else {
            displayHeader("Failure");
            for(String message : result.getErrorMessages()) {
                System.out.println(message);
            }
        }
        System.out.println();
    }

    public void displayHostResult(Result<Host> result, String operation) {
        if(result.isSuccess()) {
            displayHeader("SUCCESS");
            System.out.println("Host " + operation);
        } else {
            displayHeader("FAILURE");
            for(String message : result.getErrorMessages()) {
                System.out.println(message);
            }
        }
    }

    public void displayGuestResult(Result<Guest> result, String operation) {
        if(result.isSuccess()) {
            displayHeader("SUCCESS");
            System.out.println("Guest " + operation);
        } else {
            displayHeader("FAILURE");
            for(String message : result.getErrorMessages()) {
                System.out.println(message);
            }
        }
    }

    public int getAccountType() {
        System.out.println();
        System.out.println("[0] Go Back");
        System.out.println("[1] Host");
        System.out.println("[2] Guest");
        return readInt("Select Account Type [0-2]: ", 0, 2);
    }

    public String getEmail(String person) {
        return readRequiredString(person +" Email: ");
    }

    public Reservation makeReservation() {
        Reservation reservation = new Reservation();
        reservation.setStartDate(readDate("Start"));
        reservation.setEndDate(readDate("End"));
        System.out.println();
        return reservation;
    }

    public Host makeHost() {
        Host host = new Host();
        host.setLastName(readRequiredString("Last Name: "));
        host.setEmail(readRequiredString("Email: "));
        host.setPhoneNumber(readRequiredString("Phone Number: "));
        host.setAddress(readRequiredString("Address: "));
        host.setCity(readRequiredString("City: "));
        host.setState(readRequiredString("State Abbreviation: "));
        host.setPostalCode(readInt("Postal Code: ", 0, 99999));
        host.setStandardRate(new BigDecimal(readInt("Standard Rate: ", 0, 1000000)));
        host.setWeekendRate(new BigDecimal(readInt("Weekend Rate: ", 0, 1000000)));
        return host;
    }

    public Guest makeGuest() {
        Guest guest = new Guest();
        guest.setFirstName(readRequiredString("First Name: "));
        guest.setLastName(readRequiredString("Last Name: "));
        guest.setEmail(readRequiredString("Email: "));
        guest.setPhoneNumber(readRequiredString("Phone Number: "));
        guest.setState(readRequiredString("State Abbreviation: "));
        return guest;
    }

    public boolean confirmReservation(Reservation reservation, BigDecimal cost) {
        displayHeader("Summary");
        System.out.println("Start " + dateToString(reservation.getStartDate()));
        System.out.println("End " + dateToString(reservation.getEndDate()));
        System.out.printf("Total: $%.2f%n", cost);
        String confirmation = readRequiredString("Confirm [y/n]: ");
        return confirmation.equalsIgnoreCase("y");
    }

    public Reservation selectReservation(List<Reservation> reservations) {
        if(reservations.size() == 0) {
            System.out.println("Guest has no reservation with Host");
            return null;
        }
        System.out.println("Enter 0 to exit");
        while (true) {
            int id = readInt("Reservation ID: ");
            if(id == 0) {
                return null;
            }
            for (Reservation r : reservations) {
                if (id == r.getId()) {
                    return r;
                }
            }
        }
    }

    public void editReservation(Reservation reservation) {
        displayHeader(String.format("Editing Reservation %s", reservation.getId()));
        reservation.setStartDate(updateDate("Start (" + dateToString(reservation.getStartDate()) + "): ", reservation.getStartDate()));
        reservation.setEndDate(updateDate("End (" + dateToString(reservation.getEndDate()) + "): ", reservation.getEndDate()));
    }

    public void updateHost(Host host) {
        host.setLastName(updateString("Last Name ", host.getLastName()));
        host.setEmail(updateString("Email ", host.getEmail()));
        host.setPhoneNumber(updateString("Phone Number ", host.getPhoneNumber()));
        host.setAddress(updateString("Address ", host.getAddress()));
        host.setCity(updateString("City ", host.getCity()));
        host.setState(updateString("State Abbreviation ", host.getState()));
        host.setPostalCode(updateInt("Postal Code ", host.getPostalCode()));
        host.setStandardRate(updateRate("Standard Rate ", host.getStandardRate()));
        host.setWeekendRate(updateRate("Weekend Rate ", host.getWeekendRate()));
    }

    public void updateGuest(Guest guest) {
        guest.setFirstName(updateString("First Name ", guest.getFirstName()));
        guest.setLastName(updateString("Last Name ", guest.getLastName()));
        guest.setEmail(updateString("Email ", guest.getEmail()));
        guest.setPhoneNumber(updateString("Phone Number ", guest.getPhoneNumber()));
        guest.setState(updateString("State Abbreviation ", guest.getState()));
    }

    private String updateString(String prompt, String currentValue) {
        String input = readString(prompt + "(" +currentValue + "): ");
        if(input.isBlank()) {
            return currentValue;
        } else {
            return input;
        }
    }

    private int updateInt(String prompt, int currentValue) {
        do{
            String input = readString(prompt + "(" +currentValue + "): ");
            if(input.isBlank()) {
                return currentValue;
            }
            try{
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Enter a numeric value");
            }
        } while (true);
    }

    private BigDecimal updateRate(String prompt, BigDecimal currentValue) {
        do{
            String input = readString(prompt + "(" +currentValue + "): ");
            if(input.isBlank()) {
                return currentValue;
            }
            try{
                 new BigDecimal(input);
            } catch (NumberFormatException ex) {
                System.out.println("Enter a numeric value");
            }
        } while (true);
    }

    private LocalDate updateDate(String prompt, LocalDate date) {
        do {
            String input = readString(prompt);
            if(input.isEmpty()){
                return date;
            }
            try {
                return LocalDate.parse(input, format);
            } catch (DateTimeParseException ex) {
                System.out.println("Enter a valid date");
            }
        } while (true);
    }

    private LocalDate readDate(String prompt) {
        do {
            String date = readRequiredString(prompt + " (MM/dd/yyyy): ");
            try {
                return LocalDate.parse(date, format);
            } catch (DateTimeParseException ex) {
                System.out.println("Enter a valid date");
            }
        } while (true);
    }

    private String readString(String prompt) {
        Scanner console = new Scanner(System.in);
        System.out.print(prompt);
        return console.nextLine();
    }

    private String readRequiredString(String prompt) {
        do{
            String input =  readString(prompt);
            if(!input.isBlank()) {
                return input;
            }
            System.out.println("Field is required");
        } while (true);
    }

    private int readInt(String prompt) {
        do{
            try{
                return Integer.parseInt(readString(prompt));
            } catch(NumberFormatException ex) {
                System.out.println("Enter a numeric value");
            }
        } while (true);
    }

    private int readInt(String prompt, int min, int max) {
        int number = -1;
        do{
            try{
                number = Integer.parseInt(readString(prompt));
            } catch(NumberFormatException ex) {
                System.out.printf("Enter a number between %s and %s%n", min, max);
            }
        } while (number < min || number > max);
        return number;
    }

    private String reservationToLine(Reservation reservation) {
        Guest guest = reservation.getGuest();
        String id = String.format("%2d", reservation.getId());
        String dates = " " + dateToString(reservation.getStartDate()) + " - " + dateToString(reservation.getEndDate()) + " ";
        String guestName = "Guest: " + guest.getFirstName() + " " + guest.getLastName();
        String email = "Email: " + guest.getEmail();
        return reservationLineFormat(id, dates, guestName, email);
    }

    private String hostToLine(Host host) {
        String address = host.getAddress() + ", " + host.getCity() + ", " + host.getState();
        return hostLineFormat(host.getId(), host.getLastName(), host.getEmail(), address, host.getPostalCode(), host.getStandardRate(), host.getWeekendRate());
    }

    private String guestToLine(Guest guest) {
        String name = guest.getFirstName() + " " + guest.getLastName();
        return guestLineFormat(guest.getId(), name, guest.getEmail(), guest.getPhoneNumber(), guest.getState());
    }

    private String reservationLineFormat(String id, String dates, String name, String email) {
        return String.format("| %2s | %25s | %-27s | %-35s |", id, dates, name, email);
    }

    private String hostLineFormat(String id, String lastName, String email, String address, int postalCode, BigDecimal standardRate, BigDecimal weekendRate) {
        return String.format("| %36s | %18s | %38s | %48s | %9d | %4.2f | %4.2f", id, lastName, email, address, postalCode, standardRate.doubleValue(), weekendRate.doubleValue());
    }

    private String guestLineFormat(int id, String name , String email,String phone, String state) {
        return String.format("| %4d | %27s | %36s | %14s | %5s |", id, name, email, phone, state);
    }

    private String makeHostHeader() {
        return String.format("| %36s | %18s | %38s | %48s | %9s | %6s | %6s","ID", "Last Name", "Email", "Address", "Postal Code", "S-Rate", "W-Rate");
    }

    private String makeGuestHeader() {
        return String.format("| %4s | %27s | %36s | %14s | %5s |", "ID", "Name", "Email", "Phone", "State");
    }

    private String dateToString(LocalDate date) {
        return String.format("%02d/%02d/%04d", date.getMonthValue(), date.getDayOfMonth(), date.getYear());
    }
}