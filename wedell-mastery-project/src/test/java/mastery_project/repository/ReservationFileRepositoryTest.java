package mastery_project.repository;

import mastery_project.models.Guest;
import mastery_project.models.Host;
import mastery_project.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {
    static final String SEED_DATA_PATH = "data/test-data/reservation-repo-seed.csv";
    static final String TEST_DATA_PATH = "data/test-data/";
    ReservationFileRepository repository = new ReservationFileRepository(TEST_DATA_PATH);
    Host host = new Host();
    Guest guest = new Guest();
    //"2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c,Rosenkranc,irosenkranc8w@reverbnation.com,(970) 7391162,7 Kennedy Plaza,Greeley,CO,80638,180,225"

    void makeHost(Host host) {
        host.setId("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c");
        host.setLastName("Rosenkranc");
        host.setEmail("irosenkranc8w@reverbnation.com");
        host.setPhoneNumber("(970) 7391162");
        host.setAddress("7 Kennedy Plaza");
        host.setCity("Greeley");
        host.setState("CO");
        host.setPostalCode(80638);
        host.setStandardRate(new BigDecimal(180));
        host.setWeekendRate(new BigDecimal(225));
    }

    void makeGuest(Guest guest) {
        guest.setId(99999);
        guest.setFirstName("The");
        guest.setLastName("Test");
        guest.setEmail("test@test.com");
        guest.setState("MN");
        guest.setPhoneNumber("TEST PHONE");
    }

    @BeforeEach
    void setUp() throws IOException {
        Files.copy(Paths.get(SEED_DATA_PATH), Paths.get(TEST_DATA_PATH + "2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c.csv"), StandardCopyOption.REPLACE_EXISTING);
        makeHost(host);
        makeGuest(guest);
    }

    @Test
    void shouldFindByHost() throws DataException {
        List<Reservation> reservations = repository.findByHost(host);
        assertNotNull(reservations);
        assertEquals(13, reservations.size());
    }

    @Test
    void shouldAddReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setStartDate(LocalDate.of(2020, 10, 6));
        reservation.setEndDate(LocalDate.of(2020,10,7));
        reservation = repository.add(reservation);
        assertNotNull(reservation);
        assertEquals(new BigDecimal(180), reservation.getCost());
        assertEquals(14, reservation.getId());
        assertEquals(14, repository.findByHost(host).size());
    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = repository.findByHost(host).get(0);
        reservation.setHost(host);
        LocalDate updatedEndDate = reservation.getEndDate().plusDays(2);
        reservation.setEndDate(updatedEndDate);
        assertTrue(repository.update(reservation));
        assertEquals(updatedEndDate, repository.findByHost(host).get(0).getEndDate());
        assertEquals(new BigDecimal(1170), repository.findByHost(host).get(0).getCost());
    }

    @Test
    void shouldNotUpdateForNonExisitngReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setId(50);
        assertFalse(repository.update(reservation));
    }

    @Test
    void shouldDeleteExistingReservation() throws DataException {
        assertTrue(repository.delete(host, 1));
        assertEquals(12, repository.findByHost(host).size());
        assertEquals(2, repository.findByHost(host).get(0).getId());
    }

    @Test
    void shouldNotDeleteForResercationIdNotFound() throws DataException {
        assertFalse(repository.delete(host, 50));
        assertEquals(13, repository.findByHost(host).size());
    }
}