package mastery_project.domain;

import mastery_project.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {
    HostService service = new HostService(new HostRepositoryDouble());
    Host newHost;

    @BeforeEach
    void setUp() {
            service = new HostService(new HostRepositoryDouble());
            newHost = makeHost();
    }

    Host makeHost() {
        Host host = new Host();
        host.setLastName("TestHost");
        host.setId("TestId");
        host.setEmail("TestHost@test.com");
        host.setAddress("123 Main St.");
        host.setCity("Stillwater");
        host.setState("MN");
        host.setPhoneNumber("(111) 111 1111");
        host.setPostalCode(55555);
        host.setWeekendRate(new BigDecimal(100));
        host.setStandardRate(new BigDecimal(50));
        return host;
    }

    @Test
    void shouldFindAll() {
        List<Host> actual = service.findAll();
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void shouldFindByEmail() {
        Host host = service.findByEmail("host@host.com");
        assertNotNull(host);
        assertEquals("host@host.com", host.getEmail());
    }

    @Test
    void shouldReturnNullForNoMatchingEmail() {
            assertNull(service.findByEmail("test@test.com"));
        }

    @Test
    void shouldFindById() {
        Host host = service.findById("test-test-test");
        assertNotNull(host);
        assertEquals("test-test-test", host.getId());
    }

    @Test
    void shouldReturnNullForNoMatchingId() {
            assertNull(service.findById("no-id-matches"));
        }

    @Test
    void shouldAdd() {
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddForNullLastName() {
        newHost.setLastName(null);
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("last"));
    }

    @Test
    void shouldNotAddForBlankLastName() {
        newHost.setLastName(" ");
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("last"));
    }

    @Test
    void shouldNotAddForNullEmail() {
        newHost.setEmail(null);
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("email"));
    }

    @Test
    void shouldNotAddForBlankEmail() {
        newHost.setEmail(" ");
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("email"));
    }

    @Test
    void shouldNotAddForNullAddress() {
        newHost.setAddress(null);
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("address"));
    }

    @Test
    void shouldNotAddForBlankAddress() {
        newHost.setAddress(" ");
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("address"));
    }

    @Test
    void shouldNotAddForNullCity() {
        newHost.setCity(null);
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("city"));
    }

    @Test
    void shouldNotAddForBlankCity() {
        newHost.setCity(" ");
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("city"));
    }

    @Test
    void shouldNotAddForNullState() {
        newHost.setState(null);
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("state"));
    }

    @Test
    void shouldNotAddForBlankState() {
        newHost.setState(" ");
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("state"));
    }

    @Test
    void shouldNotAddForNegativePostalCode() {
        newHost.setPostalCode(-5);
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("code"));
    }

    @Test
    void shouldNotAddForNegativeStandardRate() {
        newHost.setStandardRate(new BigDecimal(-5));
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("positive"));
    }

    @Test
    void shouldNotAddForZeroStandardRate() {
        newHost.setStandardRate(BigDecimal.ZERO);
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("positive"));
    }

    @Test
    void shouldNotAddForNegativeWeekendRate() {
        newHost.setWeekendRate(new BigDecimal(-5));
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("positive"));
    }

    @Test
    void shouldNotAddForZeroWeekendRate() {
        newHost.setWeekendRate(BigDecimal.ZERO);
        Result<Host> result = service.add(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("positive"));
    }

    @Test
    void shouldUpdate() {
        newHost.setId("test-test-test");
        Result<Host> result = service.update(newHost);
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateForNullId() {
        newHost.setId(null);
        Result<Host> result = service.update(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("ID"));
    }

    @Test
    void shouldNotUpdateForBlankId() {
        newHost.setId(" ");
        Result<Host> result = service.update(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("ID"));
    }

    @Test
    void shouldNotUpdateForNonMatchingId() {
        newHost.setId("no-match");
        Result<Host> result = service.update(newHost);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Host"));
    }

    @Test
    void shouldDelete() {
        Result<Host> result = service.delete("host@host.com");
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteForNullEmail() {
        Result<Host> result = service.delete(null);
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Email"));
    }

    @Test
    void shouldNotDeleteForBlankEmail() {
        Result<Host> result = service.delete("  ");
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Email"));
    }

    @Test
    void shouldNotDeleteForNoMatchingEmail() {
        Result<Host> result = service.delete("no@match.com");
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("email"));
    }
}