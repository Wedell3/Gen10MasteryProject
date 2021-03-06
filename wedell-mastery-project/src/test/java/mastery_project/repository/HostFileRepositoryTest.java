package mastery_project.repository;

import mastery_project.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {
    private static final String SEED_HOST_PATH = "data/test-data/host-seed.csv";
    private static final String TEST_HOST_PATH = "data/test-data/host-test.csv";

    HostFileRepository repository = new HostFileRepository(TEST_HOST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(Paths.get(SEED_HOST_PATH), Paths.get(TEST_HOST_PATH), StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Host> actual = repository.findAll();
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void shouldFindHostByEmail() {
        Host host = repository.findByEmail("eyearnes0@sfgate.com");
        assertNotNull(host);
        assertEquals("3edda6bc-ab95-49a8-8962-d50b53f84b15", host.getId());
        assertEquals("Yearnes", host.getLastName());
        assertEquals("eyearnes0@sfgate.com",host.getEmail());
        assertEquals("(806) 1783815", host.getPhoneNumber());
        assertEquals("3 Nova Trail", host.getAddress());
        assertEquals("Amarillo", host.getCity());
        assertEquals("TX", host.getState());
        assertEquals(79182, host.getPostalCode());
        assertEquals(new BigDecimal(340), host.getStandardRate());
        assertEquals(new BigDecimal(425), host.getWeekendRate());
    }

    @Test
    void shouldReturnNullForNoMatchingEmail() {
        assertNull(repository.findByEmail("test@test.com"));
    }

    @Test
    void shouldFindById() {
        Host host = repository.findById("a0d911e7-4fde-4e4a-bdb7-f047f15615e8");
        assertNotNull(host);
        assertEquals("a0d911e7-4fde-4e4a-bdb7-f047f15615e8", host.getId());
        assertEquals("Rhodes", host.getLastName());
        assertEquals("krhodes1@posterous.com",host.getEmail());
        assertEquals("(478) 7475991", host.getPhoneNumber());
        assertEquals("7262 Morning Avenue", host.getAddress());
        assertEquals("Macon", host.getCity());
        assertEquals("GA", host.getState());
        assertEquals(31296, host.getPostalCode());
        assertEquals(new BigDecimal(295), host.getStandardRate());
        assertEquals(new BigDecimal("368.75"), host.getWeekendRate());
    }

    @Test
    void shouldReturnNullForNoMatchingId() {
        assertNull(repository.findById("test-test-test"));
    }

    @Test
    void shouldAdd() {
        Host host = new Host();
        host.setLastName("Test");
        host.setId("test");
        host.setEmail("test@test.com");
        host.setStandardRate(new BigDecimal(100));
        host.setWeekendRate(new BigDecimal(300));
        host.setPostalCode(55555);
        host.setState("MN");
        host.setCity("St. Paul");
        host.setAddress("123 Main St.");
        host.setPhoneNumber("123-456-7890");
        repository.addHost(host);
        List<Host> actual = repository.findAll();
        assertEquals(3, actual.size());
        assertEquals("Test", actual.get(2).getLastName());
        assertNotNull(actual.get(2).getId());
    }

    @Test
    void shouldUpdate() {
        Host host = repository.findByEmail("eyearnes0@sfgate.com");
        host.setLastName("Test");
        assertTrue(repository.updateHost(host));
        assertEquals("Test", repository.findAll().get(0).getLastName());
    }

    @Test
    void shouldNotUpdateForNoMatchingId() {
        Host host = new Host();
        host.setId("test");
        assertFalse(repository.updateHost(host));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteHost("eyearnes0@sfgate.com"));
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldNotDeleteForNoMatchingEmail() {
        assertFalse(repository.deleteHost("test@test.com"));
    }

}