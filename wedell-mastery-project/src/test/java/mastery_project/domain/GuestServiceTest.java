package mastery_project.domain;

import mastery_project.models.Guest;
import mastery_project.repository.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {
    GuestService service = new GuestService(new GuestRepositoryDouble());

    @BeforeEach
    void setup() {
        service = new GuestService(new GuestRepositoryDouble());
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
}