package mastery_project.domain;

import mastery_project.models.Guest;
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
        Host host = makeHost();
        Guest guest = makeGuest();
        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();
        r1.setId(1);
        ArrayList<Reservation> rs = new ArrayList();
        r1.setStartDate(LocalDate.of(3000, 2,1));
        r1.setEndDate(LocalDate.of(3000, 2,10));
        r2.setStartDate(LocalDate.of(3000, 10,1));
        r2.setEndDate(LocalDate.of(3000, 10,10));
        r1.setHost(host);
        r1.setGuest(guest);
        r2.setHost(host);
        r2.setGuest(guest);
        rs.add(r1);
        rs.add(r2);
        return rs;
    }

    Host makeHost() {
        Host host = new Host();
        host.setLastName("Test");
        host.setEmail("host@test.com");
        host.setWeekendRate(new BigDecimal(200));
        host.setStandardRate(new BigDecimal(100));
        return host;
    }

    Guest makeGuest() {
        Guest guest = new Guest();
        guest.setEmail("guest@test.com");
        guest.setFirstName("guest");
        guest.setLastName("tester");
        guest.setId(1);
        return guest;
    }
}
