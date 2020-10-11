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

    public Result<Guest> addGuest(Guest guest) {
        Result<Guest> result = validateGuest(guest);
        if(!result.isSuccess()) {
            return result;
        }
        result.setPayload(repository.addGuest(guest));
        return result;
    }

    public Result<Guest> updateGuest(Guest guest) {
        Result<Guest> result = validateGuest(guest);
        if(guest.getId() <= 0) {
            result.addErrorMessage("Guest must have a positive id");
        }
        if(!result.isSuccess()) {
            return result;
        }
        if(!repository.updateGuest(guest)) {
            result.addErrorMessage("No Guest found with matching ID");
            return result;
        }
        result.setPayload(guest);
        return result;
    }

    public Result<Guest> deleteGuest(int id) {
        Result<Guest> result = new Result<>();
        if(id <= 0) {
            result.addErrorMessage("Guest must have a positive id");
        }
        if(!repository.deleteGuest(id)) {
            result.addErrorMessage("Guest not found");
        }
        return result;
    }


    private Result<Guest> validateGuest(Guest guest) {
        Result<Guest> result = new Result<>();
        if(guest == null) {
            result.addErrorMessage("Guest cannot be null");
            return result;
        }
        if(guest.getEmail() == null || guest.getEmail().isBlank()) {
            result.addErrorMessage("Guest must have an email");
        }
        if(guest.getFirstName() == null || guest.getFirstName().isBlank()) {
            result.addErrorMessage("Guest must have a first name");
        }
        if(guest.getLastName() == null || guest.getLastName().isBlank()) {
            result.addErrorMessage("Guest must have a last name");
        }
        if(guest.getPhoneNumber() == null || guest.getPhoneNumber().isBlank()) {
            result.addErrorMessage("Guest must have a phone number");
        }
        if(guest.getState() == null || guest.getState().isBlank()) {
            result.addErrorMessage("Guest must have a state of residence");
        }
        return result;
    }
}
