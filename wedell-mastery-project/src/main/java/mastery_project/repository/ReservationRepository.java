package mastery_project.repository;

import mastery_project.models.Host;
import mastery_project.models.Reservation;

import java.math.BigDecimal;
import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHost(Host host) throws DataException;

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean delete(Host host, int id) throws DataException;

    BigDecimal calculateCost(Reservation reservation);
}
