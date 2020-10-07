package mastery_project.domain;

import mastery_project.models.Guest;
import mastery_project.repository.GuestRepository;

import java.util.List;

public class GuestService {
    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> findAll() {
        return repository.findAll();
    }

    public Guest findByEmail(String email) {
        if(email == null) {
            return null;
        }
        return repository.findByEmail(email);
    }
}
