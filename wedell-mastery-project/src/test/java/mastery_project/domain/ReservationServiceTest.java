package mastery_project.domain;

import mastery_project.models.Guest;
import mastery_project.models.Host;
import mastery_project.models.Reservation;
import mastery_project.repository.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {
    ReservationService service;
    Host host = makeHost();
    Guest guest = makeGuest();
    LocalDate startDate = LocalDate.of(3020, 10, 30);
    LocalDate endDate = LocalDate.of(3020, 11, 2);


    private Host makeHost() {
        Host host = new Host();
        host.setId("test-id");
        host.setEmail("host@host.com");
        host.setLastName("HOST");
        host.setPhoneNumber("(123) 456 7890");
        host.setAddress("123 Main St.");
        host.setCity("St. Paul");
        host.setState("MN");
        host.setStandardRate(new BigDecimal(100));
        host.setPostalCode(55555);
        host.setWeekendRate(new BigDecimal(200));
        return host;
    }

    private Guest makeGuest() {
        Guest guest = new Guest();
        guest.setEmail("one@uno.com");
        guest.setPhoneNumber("(098) 765 4321");
        guest.setLastName("GUEST");
        guest.setFirstName("The");
        guest.setState("MN");
        guest.setId(555);
        return guest;
    }
    @BeforeEach
    void setup() {
        service = new ReservationService(new GuestRepositoryDouble(), new HostRepositoryDouble(), new ReservationRepositoryDouble());
        host = makeHost();
        guest = makeGuest();
        startDate = LocalDate.of(3020, 10, 30);
        endDate = LocalDate.of(3020, 11, 2);
    }

    @Test
    void shouldFindByHost() throws DataException {
        List<Reservation> actual = service.findByHost(host);
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void shouldCalculateRate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        assertEquals(new BigDecimal(300), service.calculateCost(reservation).getPayload().getCost());
    }

    @Test
    void shouldNotCalculateForNullStandardRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setStandardRate(null);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        assertNull(service.calculateCost(reservation).getPayload());
    }

    @Test
    void shouldNotCalculateForNegativeStandardRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setStandardRate(new BigDecimal(-1));
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        assertNull(service.calculateCost(reservation).getPayload());
    }

    @Test
    void shouldNotCalculateForZeroStandardRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setStandardRate(new BigDecimal(0));
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        assertNull(service.calculateCost(reservation).getPayload());
    }
    @Test
    void shouldNotCalculateForNullWeekendRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setWeekendRate(null);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        assertNull(service.calculateCost(reservation).getPayload());
    }

    @Test
    void shouldNotCalculateForNegativeWeekendRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setWeekendRate(new BigDecimal(-1));
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        assertNull(service.calculateCost(reservation).getPayload());
    }

    @Test
    void shouldNotCalculateForZeroWeekendRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setWeekendRate(new BigDecimal(0));
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        assertNull(service.calculateCost(reservation).getPayload());
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    //Nulls
    @Test
    void shouldNotAddForNullHost() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(null);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Host"));
    }

    @Test
    void shouldNotAddForNullGuest() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(null);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("null"));
    }

    @Test
    void shouldNotAddForNullEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(null);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("date"));
    }

    @Test
    void shouldNotAddForNullStartDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(null);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("start"));
    }

    @Test
    void shouldNotAddForNonExistentHost() throws DataException {
        Reservation reservation = new Reservation();
        host.setEmail("fail@email.com");
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Host"));
    }

    @Test
    void shouldNotAddForNullStandardRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setStandardRate(null);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("positive"));
    }

    @Test
    void shouldNotAddForNegativeStandardRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setStandardRate(new BigDecimal(-1));
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("positive"));
    }

    @Test
    void shouldNotAddForZeroStandardRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setStandardRate(BigDecimal.ZERO);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("positive"));
    }

    @Test
    void shouldNotAddForNullWeekendRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setWeekendRate(null);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("positive"));
    }

    @Test
    void shouldNotAddForNegativeWeekendRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setWeekendRate(new BigDecimal(-1));
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("positive"));
    }

    @Test
    void shouldNotAddForZeroWeekendRate() throws DataException {
        Reservation reservation = new Reservation();
        host.setWeekendRate(BigDecimal.ZERO);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("positive"));
    }

    @Test
    void shouldNotAddForNullHostEmail() throws DataException {
        Reservation reservation = new Reservation();
        host.setEmail(null);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("email"));
    }

    @Test
    void shouldNotAddForEmptyHostEmail() throws DataException {
        Reservation reservation = new Reservation();
        host.setEmail("");
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("email"));
    }

    @Test
    void shouldNotAddForBlankHostEmail() throws DataException {
        Reservation reservation = new Reservation();
        host.setEmail("  ");
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("email"));
    }

    @Test
    void shouldNotAddForNonExistentGuest() throws DataException {
        Reservation reservation = new Reservation();
        guest.setEmail("fail@email.com");
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Guest"));
    }

    @Test
    void shouldNotAddForNullGuestEmail() throws DataException {
        Reservation reservation = new Reservation();
        guest.setEmail(null);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("email"));
    }

    @Test
    void shouldNotAddForEmptyGuestEmail() throws DataException {
        Reservation reservation = new Reservation();
        guest.setEmail("");
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("email"));
    }

    @Test
    void shouldNotAddForBlankGuestEmail() throws DataException {
        Reservation reservation = new Reservation();
        guest.setEmail("   ");
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("email"));
    }

    //Date validations
    @Test
    void shouldNotAddForStartDateInPast() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        startDate = LocalDate.of(1000,1,1);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Start"));
    }

    @Test
    void shouldNotAddForEndDateInPast() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        endDate = LocalDate.of(1000,1,1);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("End"));
    }

    @Test
    void shouldNotAddForStartDateAfterEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        startDate = LocalDate.of(3020,12,1);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("before"));
    }

    @Test
    void shouldNotAddForOverLappingReservationDatesOutside() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        endDate = LocalDate.of(3000, 2, 20);
        reservation.setEndDate(endDate);
        startDate = LocalDate.of(3000,1,1);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("overlap"));
    }

    @Test
    void shouldNotAddForOverLappingReservationDatesInside() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        endDate = LocalDate.of(3000, 2, 8);
        reservation.setEndDate(endDate);
        startDate = LocalDate.of(3000,2,5);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Start"));
    }

    @Test
    void shouldNotAddForStartDateInsidePreviousReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        endDate = LocalDate.of(3000, 2, 20);
        reservation.setEndDate(endDate);
        startDate = LocalDate.of(3000,2,5);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Start"));
    }

    @Test
    void shouldNotAddForEndDateInsidePreviousReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        endDate = LocalDate.of(3000, 2, 5);
        reservation.setEndDate(endDate);
        startDate = LocalDate.of(3000,1,1);
        reservation.setStartDate(startDate);
        Result<Reservation> result = service.addReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("End"));
    }



    //UPDATE METHOD TESTS
    @Test
    void shouldUpdate() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        reservation.setId(1);
        Result<Reservation> result = service.updateReservation(reservation);
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateForNullReservation() throws DataException{
        Result<Reservation> result = service.updateReservation(null);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("null"));
    }

    @Test
    void shouldNotUpdateForNoMatchingReservationId() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        reservation.setId(11111);
        Result<Reservation> result = service.updateReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Update"));
    }

    @Test
    void shouldNotUpdateForNullHost() throws DataException{
        Reservation reservation = new Reservation();
        reservation.setHost(null);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        reservation.setId(1);
        Result<Reservation> result = service.updateReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Host"));
    }

    @Test
    void shouldNotUpdateForNullHostId() throws DataException{
        Reservation reservation = new Reservation();
        host.setId(null);
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        reservation.setId(1);
        Result<Reservation> result = service.updateReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("id"));
    }

    @Test
    void shouldNotUpdateForEmptyHostId() throws DataException{
        Reservation reservation = new Reservation();
        host.setId(" ");
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        reservation.setId(1);
        Result<Reservation> result = service.updateReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("id"));
    }

    @Test
    void shouldNotUpdateForStartDateInThePast() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setEndDate(endDate);
        startDate = LocalDate.of(1000,1,1);
        reservation.setStartDate(startDate);
        reservation.setId(1);
        Result<Reservation> result = service.updateReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Start"));
    }

    @Test
    void shouldNotUpdateForEndDateInThePast() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        endDate = LocalDate.of(1000,1,1);
        reservation.setEndDate(endDate);
        reservation.setStartDate(startDate);
        reservation.setId(1);
        Result<Reservation> result = service.updateReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("End"));
    }

    @Test
    void shouldNotUpdateForOverLappingExistingReservationDatesOutside() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        endDate = LocalDate.of(3000,11,1);
        reservation.setEndDate(endDate);
        startDate = LocalDate.of(3000,9,1);
        reservation.setStartDate(startDate);
        reservation.setId(1);
        Result<Reservation> result = service.updateReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("overlap"));
    }

    @Test
    void shouldNotUpdateForOverLappingExistingReservationDatesInside() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        endDate = LocalDate.of(3000,10,8);
        reservation.setEndDate(endDate);
        startDate = LocalDate.of(3000,10,4);
        reservation.setStartDate(startDate);
        reservation.setId(1);
        Result<Reservation> result = service.updateReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("previous"));
    }

    @Test
    void shouldNotUpdateForStartDateInsideExistingReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        endDate = LocalDate.of(3000,11,1);
        reservation.setEndDate(endDate);
        startDate = LocalDate.of(3000,10,4);
        reservation.setStartDate(startDate);
        reservation.setId(1);
        Result<Reservation> result = service.updateReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("previous"));
    }

    @Test
    void shouldNotUpdateForEndDateInsideExistingReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setGuest(guest);
        endDate = LocalDate.of(3000,10,4);
        reservation.setEndDate(endDate);
        startDate = LocalDate.of(3000,9,1);
        reservation.setStartDate(startDate);
        reservation.setId(1);
        Result<Reservation> result = service.updateReservation(reservation);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("previous"));
    }


    //Delete Test Methods
    @Test
    void shouldDelete() throws DataException {
        Result<Reservation> result = service.deleteReservation(host,1);
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteForNoMatchingID() throws DataException {
        Result<Reservation> result = service.deleteReservation(host,1111);
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotDeleteForNullHost() throws DataException {
        Result<Reservation> result = service.deleteReservation(null, 1);
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotDeleteForNullHostID() throws DataException {
        host.setId(null);
        Result<Reservation> result = service.deleteReservation(host, 1);
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotDeleteForEmptyHostID() throws DataException {
        host.setId("  ");
        Result<Reservation> result = service.deleteReservation(host, 1);
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }

}