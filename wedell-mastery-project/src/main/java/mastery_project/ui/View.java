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

    public MainMenuOption getMenuSelection() {
        displayMainMenu();
        int value = readInt(String.format("Enter a number [%s-%s]: ", 0, MainMenuOption.values().length - 1), 0, MainMenuOption.values().length - 1);
        return MainMenuOption.getMainMenuOptionFromValue(value);
    }

    public void displayMainMenu() {
        for(MainMenuOption option : MainMenuOption.values()) {
            System.out.printf("[%s] %s", option.getValue(), option.getMessage());
        }
        System.out.println();
    }

    public void displayHeader(String message) {
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    public void displayReservations(List<Reservation> reservations, Host host) {
        displayHeader(host.getLastName() + ": " + host.getCity() + " " + host.getState());
        if(reservations.size() == 0) {
            System.out.println("Open Availability");
        }else {
            for (Reservation r : reservations) {
                System.out.println(reservationToLine(r));
            }
        }
    }

    public void displayResult(Result<Reservation> result, String operation){
        if(result.isSuccess()) {
            displayHeader("SUCCESS");
            System.out.println("Reservation " + result.getPayload().getId() + operation + ".");
        }
        else {
            displayHeader("Failure");
            for(String message : result.getErrorMessages()) {
                System.out.println(message);
            }
        }
        System.out.println();
    }

    public void displayResult(Result<Reservation> result,int id, String operation){
        if(result.isSuccess()) {
            displayHeader("SUCCESS");
            System.out.println("Reservation " + id + operation + ".");
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
        return reservation;
    }

    public boolean confirmReservation(Reservation reservation, BigDecimal cost) {
        System.out.println("Start " + dateToString(reservation.getStartDate()));
        System.out.println("End " + dateToString(reservation.getEndDate()));
        System.out.printf("Total: $%.2f%n", cost);
        String confirmation = readRequiredString("Confirm [y/n]: ");
        return confirmation.equalsIgnoreCase("y");
    }

    public Reservation selectReservation(List<Reservation> reservations) {
        while (true) {
            if(reservations.size() == 0) {
                System.out.println("Guest has no reservation with Host");
                return null;
            }
            System.out.println("Enter 0 to exit");
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
            String input = readString(prompt + " (MM/dd/yyyy): ");
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
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(reservation.getId()).append(",");
        sb.append(dateToString(reservation.getStartDate())).append(" - ").append(dateToString(reservation.getEndDate())).append(",");
        sb.append("Guest: ").append(guest.getFirstName()).append(" ").append(guest.getLastName()).append(",");
        sb.append("Email: ").append(guest.getEmail());
        return sb.toString();
    }

    private String dateToString(LocalDate date) {
        return String.format("%2s/%2s/%4s", date.getMonthValue(), date.getDayOfMonth(), date.getYear());
    }
}