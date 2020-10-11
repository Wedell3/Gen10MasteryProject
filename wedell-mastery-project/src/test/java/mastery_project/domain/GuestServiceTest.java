package mastery_project.domain;

import mastery_project.models.Guest;
import mastery_project.repository.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {
    GuestService service = new GuestService(new GuestRepositoryDouble());
    Guest newGuest;

    Guest makeGuest() {
        Guest guest = new Guest();
        guest.setFirstName("Guest");
        guest.setLastName("Test");
        guest.setPhoneNumber("(111) 111 1111");
        guest.setEmail("testGuest@test.com");
        guest.setState("MN");
        return guest;
    }

    @BeforeEach
    void setup() {
        service = new GuestService(new GuestRepositoryDouble());
        newGuest = makeGuest();
    }

    @Test
    void shouldFindAll() {
        List<Guest> actual = service.findAll();
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void shouldFindByEmail() {
        Guest guest = service.findByEmail("one@uno.com");
        assertNotNull(guest);
        assertEquals("one@uno.com", guest.getEmail());
    }

    @Test
    void shouldReturnNullForNoMatchingEmail() {
        assertNull(service.findByEmail("test@test.com"));
    }

    @Test
    void shouldAdd() {
        Result<Guest> result = service.addGuest(newGuest);
        assertNotNull(result);
        assertTrue(result.isSuccess());
        Guest guest = result.getPayload();
        assertNotNull(guest);
        assertEquals("Guest", guest.getFirstName());
    }

    @Test
    void shouldNotAddForNullGuest() {
        Result<Guest> result = service.addGuest(null);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("null"));
    }

    @Test
    void shouldNotAddForNullGuestFirstName() {
        newGuest.setFirstName(null);
        Result<Guest> result = service.addGuest(newGuest);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("first"));
    }

    @Test
    void shouldNotAddForEmptyGuestFirstName() {
        newGuest.setFirstName(" ");
        Result<Guest> result = service.addGuest(newGuest);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("first"));
    }

    @Test
    void shouldNotAddForNullGuestLastName() {
        newGuest.setLastName(null);
        Result<Guest> result = service.addGuest(newGuest);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("last"));
    }

    @Test
    void shouldNotAddForEmptyGuestLastName() {
        newGuest.setLastName(" ");
        Result<Guest> result = service.addGuest(newGuest);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("last"));
    }

    @Test
    void shouldNotAddForNullGuestState() {
        newGuest.setState(null);
        Result<Guest> result = service.addGuest(newGuest);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("state"));
    }

    @Test
    void shouldNotAddForEmptyGuestState() {
        newGuest.setState(" ");
        Result<Guest> result = service.addGuest(newGuest);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("state"));
    }

    @Test
    void shouldNotAddForNullGuestEmail() {
        newGuest.setEmail(null);
        Result<Guest> result = service.addGuest(newGuest);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("email"));
    }

    @Test
    void shouldNotAddForEmptyGuestEmail() {
        newGuest.setEmail(" ");
        Result<Guest> result = service.addGuest(newGuest);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("email"));
    }

    @Test
    void shouldNotAddForNullGuestPhoneNumber() {
        newGuest.setPhoneNumber(null);
        Result<Guest> result = service.addGuest(newGuest);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("phone"));
    }

    @Test
    void shouldNotAddForEmptyGuestPhoneNumber() {
        newGuest.setPhoneNumber(" ");
        Result<Guest> result = service.addGuest(newGuest);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("phone"));
    }

    @Test
    void shouldUpdate() {
        newGuest.setId(2);
        Result<Guest> result = service.updateGuest(newGuest);
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateForGuestWithNoId() {
        Result<Guest> result = service.updateGuest(newGuest);
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldDelete() {
        Result<Guest> result = service.deleteGuest(2);
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    void shoudlNotDeleteForNegativeId() {
        Result<Guest> result = service.deleteGuest(-1);
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }
}