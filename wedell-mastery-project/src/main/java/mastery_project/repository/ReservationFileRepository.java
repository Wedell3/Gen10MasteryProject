package mastery_project.repository;


import mastery_project.models.Guest;
import mastery_project.models.Host;
import mastery_project.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReservationFileRepository implements ReservationRepository {
    private final String reservationDirectory;
    private static final String DELIMITER = ",";
    private static final String DATE_DELIMITER = "-";

    public ReservationFileRepository(String reservationDirectory) {
        this.reservationDirectory = reservationDirectory;
    }

    @Override
    public List<Reservation> findByHost(Host host) throws DataException {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String path = reservationDirectory + host.getId() + ".csv";
        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            reader.readLine(); //skip header
            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                Reservation reservation = deserialize(line);
                reservations.add(reservation);
            }
        }catch (FileNotFoundException ex) {
            return reservations;
        }
        catch (IOException ex) {
            throw new DataException(ex.getMessage(), ex);
        }
        return reservations;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException{
        List<Reservation> reservations = findByHost(reservation.getHost());
        reservation.setId(getNextId(reservations));
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

    public BigDecimal calculateCost(Reservation reservation) {
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();
        LocalDate date = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());
        BigDecimal standardRate = reservation.getHost().getStandardRate();
        BigDecimal weekendRate =  reservation.getHost().getWeekendRate();
        BigDecimal cost = new BigDecimal(0);
        do{
            if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                cost = cost.add(weekendRate);
            } else {
                cost = cost.add(standardRate);
            }
            date = date.plusDays(1);
        } while (date.isBefore(endDate));
        return cost;
    }

    private void writeAll(List<Reservation> reservations, Host host) {
        if(reservations == null || host == null){
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
        reservation.setId(Integer.parseInt(fields[0]));
        reservation.setStartDate(deserializeDate(fields[1]));
        reservation.setEndDate(deserializeDate(fields[2]));
        guest.setId(Integer.parseInt(fields[3]));
        reservation.setCost(new BigDecimal(fields[4]));
        reservation.setGuest(guest);
        return reservation;
    }

    private LocalDate deserializeDate(String line) {
        String[] dateValues =  line.split(DATE_DELIMITER);
        if(dateValues.length != 3) {
            return null;
        }
        try {
            return LocalDate.of(Integer.parseInt(dateValues[0]), Integer.parseInt(dateValues[1]), Integer.parseInt(dateValues[2]));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private String serialize(Reservation reservation) {
        StringBuilder sb = new StringBuilder();
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate =  reservation.getEndDate();
        sb.append(reservation.getId()).append(DELIMITER);
        sb.append(startDate.getYear()).append(DATE_DELIMITER).append(startDate.getMonthValue()).append(DATE_DELIMITER).append(startDate.getDayOfMonth()).append(DELIMITER);
        sb.append(endDate.getYear()).append(DATE_DELIMITER).append(endDate.getMonthValue()).append(DATE_DELIMITER).append(endDate.getDayOfMonth()).append(DELIMITER);
        sb.append(reservation.getGuest().getId()).append(DELIMITER);
        sb.append(reservation.getCost());
        return sb.toString();
    }

    private String makeHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("id").append(DELIMITER);
        sb.append("start_date").append(DELIMITER);
        sb.append("end_date").append(DELIMITER);
        sb.append("guest_id").append(DELIMITER);
        sb.append("total");
        return sb.toString();
    }

    private int getNextId(List<Reservation> reservations) {
        int nextId = 0;
        for(Reservation r : reservations) {
            if(r.getId() > nextId){
                nextId = r.getId();
            }
        }
        return nextId + 1;
    }
}
