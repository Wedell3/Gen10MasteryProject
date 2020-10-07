package mastery_project.domain;

import mastery_project.models.Host;
import mastery_project.repository.HostRepository;

import java.util.List;

public class HostService {
    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public List<Host> findAll() {
        return repository.findAll();
    }

    public Host findByEmail(String email) {
        if(email == null) {
            return null;
        }
        return repository.findByEmail(email);
    }

    public Host findById(String id) {
        if (id == null) {
            return null;
        }
        return repository.findById(id);
    }

}
