package mastery_project.domain;

import mastery_project.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {
        HostService service = new HostService(new HostRepositoryDouble());

        @BeforeEach
        void setUp() {
            service = new HostService(new HostRepositoryDouble());
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
}