package mastery_project.domain;

import mastery_project.models.Host;
import mastery_project.models.Reservation;
import mastery_project.repository.DataException;
import mastery_project.repository.ReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble  implements ReservationRepository {
    List<Reservation> reservations = makeReservations();
    @Override
    public List<Reservation> findByHost(Host host) throws DataException {
        return reservations;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return reservation.getId() == 1;
    }

    @Override
    public boolean delete(Host host, int id) throws DataException {
        return reservations.get(0).getId() == id;
    }

    @Override
    public BigDecimal calculateCost(Reservation reservation) {
        return new BigDecimal(300);
    }

    private ArrayList<Reservation> makeReservations() {
        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();
        r1.setId(1);
        ArrayList<Reservation> rs = new ArrayList();
        r1.setStartDate(LocalDate.of(3000, 2,1));
        r1.setEndDate(LocalDate.of(3000, 2,10));
        r2.setStartDate(LocalDate.of(3000, 10,1));
        r2.setEndDate(LocalDate.of(3000, 10,10));
        rs.add(r1);
        rs.add(r2);
        return rs;
    }
}
