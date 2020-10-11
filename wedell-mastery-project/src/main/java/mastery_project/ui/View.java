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

    private DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");

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
        for(MainMenuOption option : MainMenuOption.values()) {
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

    private String reservationLineFormat(String id, String dates, String name, String email) {
        return String.format("| %2s | %25s | %-27s | %-35s |", id, dates, name, email);
    }

    private String dateToString(LocalDate date) {
        return String.format("%02d/%02d/%04d", date.getMonthValue(), date.getDayOfMonth(), date.getYear());
    }
}