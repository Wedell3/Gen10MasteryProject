package mastery_project.repository;

import mastery_project.models.Guest;
import mastery_project.models.Host;
import mastery_project.models.Reservation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class ReservationFileRepository implements ReservationRepository {
    private final String reservationDirectory;
    private static final String DELIMITER = ",";
    private static final String DATE_DELIMITER = "/";

    public ReservationFileRepository(String reservationDirectory) {
        this.reservationDirectory = reservationDirectory;
    }

    @Override
    public List<Reservation> findByHost(Host host) throws DataException {
        ArrayList<Reservation> reservations = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(reservationDirectory + host.getId() + ".csv"))){
            reader.readLine(); //skip header
            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                Reservation reservation = deserialize(line);
                reservations.add(reservation);
            }
        } catch (IOException ex) {
            throw new DataException("No reservations found for host", ex);
        }
        return reservations;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException{
        List<Reservation> reservations = findByHost(reservation.getHost());
        //set reservation ID
        reservations.add(reservation);
        writeAll(reservations, reservation.getHost());
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException{
        List<Reservation> reservations = findByHost(reservation.getHost());
        for(int index = 0; index < reservations.size(); index++) {
            if(reservation.getId() == reservations.get(index).getId()) {
                reservations.set(index, reservation);
                writeAll(reservations, reservation.getHost());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Host host, int id) throws DataException {
        List<Reservation> reservations = findByHost(host);
        for(int index = 0; index < reservations.size(); index++) {
            if(id == reservations.get(index).getId()) {
                reservations.remove(index);
                writeAll(reservations, host);
                return true;
            }
        }
        return false;
    }

    private void writeAll(List<Reservation> reservations, Host host) {
        if(reservations == null || reservations.size() == 0 || host == null){
            return;
        }
        try(PrintWriter writer = new PrintWriter(reservationDirectory + host.getId() + ".csv")){
            writer.println(makeHeader());
            for(Reservation r : reservations) {
                writer.println(serialize(r));
            }
        } catch (IOException ex) {
        }
    }

    private Reservation deserialize(String line) {
        String[] fields = line.split(DELIMITER);
        Reservation reservation = new Reservation();
        Guest guest = new Guest();
        Host host = new Host();
        reservation.setId(Integer.parseInt(fields[0]));
        host.setEmail(fields[1]);
        guest.setEmail(fields[2]);
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setStartDate(deserializeDate(fields[3]));
        reservation.setEndDate(deserializeDate(fields[4]));
        reservation.setCost(new BigDecimal(fields[5]));
        return reservation;
    }

    private LocalDate deserializeDate(String line) {
        String[] dateValues =  line.split(DATE_DELIMITER);
        if(dateValues.length != 3) {
            return null;
        }
        try {
            return LocalDate.of(Integer.parseInt(dateValues[2]), Integer.parseInt(dateValues[1]), Integer.parseInt(dateValues[0]));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private String serialize(Reservation reservation) {
        StringBuilder sb = new StringBuilder();
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate =  reservation.getEndDate();
        sb.append(reservation.getId()).append(DELIMITER);
        sb.append(reservation.getHost().getEmail()).append(DELIMITER);
        sb.append(reservation.getGuest().getEmail()).append(DELIMITER);
        sb.append(startDate.getMonthValue()).append(DATE_DELIMITER).append(startDate.getDayOfMonth()).append(DATE_DELIMITER).append(startDate.getYear()).append(DELIMITER);
        sb.append(endDate.getMonthValue()).append(DATE_DELIMITER).append(endDate.getDayOfMonth()).append(DATE_DELIMITER).append(endDate.getYear()).append(DELIMITER);
        sb.append(reservation.getCost());
        return sb.toString();
    }

    private String makeHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("id").append(DELIMITER);
        sb.append("host_email").append(DELIMITER);
        sb.append("guest_email").append(DELIMITER);
        sb.append("start_date").append(DELIMITER);
        sb.append("end_date").append(DELIMITER);
        sb.append("cost");
        return sb.toString();

    }
}
