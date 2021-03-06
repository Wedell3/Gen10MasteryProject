package mastery_project.repository;

import mastery_project.models.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();

    Host findByEmail(String email);

    Host findById(String id);

    Host addHost(Host host);

    boolean updateHost(Host host);

    boolean deleteHost(String email);
}
