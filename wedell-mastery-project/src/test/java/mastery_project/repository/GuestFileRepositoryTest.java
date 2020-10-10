package mastery_project.repository;

import mastery_project.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {
    private static final String SEED_GUEST_PATH = "data/test-data/guest-seed.csv";
    private static final String TEST_GUEST_PATH = "data/test-data/guest-test.csv";

    GuestFileRepository repository = new GuestFileRepository(TEST_GUEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(Paths.get(SEED_GUEST_PATH), Paths.get(TEST_GUEST_PATH), StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Guest> actual = repository.findAll();
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void shouldFindByEmail() {
        Guest guest = repository.findByEmail("slomas0@mediafire.com");
        assertNotNull(guest);
        assertEquals(1, guest.getId());
        assertEquals("Sullivan", guest.getFirstName());
        assertEquals("Lomas", guest.getLastName());
        assertEquals("slomas0@mediafire.com", guest.getEmail());
        assertEquals("(702) 7768761", guest.getPhoneNumber());
        assertEquals("NV", guest.getState());
    }

    @Test
    void shouldReturnNullForNoMatchingEmail() {
        assertNull(repository.findByEmail("test@test.com"));
    }

    @Test
    void shouldFindById() {
        Guest guest = repository.findById(1);
        assertNotNull(guest);
        assertEquals(1, guest.getId());
        assertEquals("Lomas", guest.getLastName());
    }

    @Test
    void shouldReturnNullForNoMatchingId() {
        assertNull(repository.findById(999));
    }

    @Test
    void shouldAdd() {
        Guest guest = new Guest();
        guest.setFirstName("Test");
        guest.setLastName("guest");
        guest.setEmail("test@test.com");
        guest.setState("MN");
        guest.setPhoneNumber("123-456-7890");
        guest = repository.addGuest(guest);
        assertEquals(3, guest.getId());
        assertEquals(3, repository.findAll().size());
    }

    @Test
    void shouldUpdate() {
        Guest guest = repository.findById(1);
        guest.setLastName("Test");
        assertTrue(repository.updateGuest(guest));
        assertEquals("Test", repository.findById(1).getLastName());
    }

    @Test
    void shouldNotUpdateForNoMatchingId() {
        Guest guest = new Guest();
        guest.setId(100);
        assertFalse(repository.updateGuest(guest));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteGuest(1));
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldNotDeleteForIdNotFound() {
        assertFalse(repository.deleteGuest(1000));
    }
}