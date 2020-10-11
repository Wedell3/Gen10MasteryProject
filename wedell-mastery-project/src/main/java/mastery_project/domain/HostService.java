package mastery_project.domain;

import mastery_project.models.Host;
import mastery_project.repository.HostRepository;

import java.math.BigDecimal;
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

    public Result<Host> add(Host host) {
        Result<Host> result = validateHost(host);
        if(!result.isSuccess()) {
            return result;
        }
        result.setPayload(repository.addHost(host));
        return result;
    }

    public Result<Host> update(Host host) {
        Result<Host> result = validateHost(host);
        if(host.getId() == null || host.getId().isBlank()) {
            result.addErrorMessage("Host must have ID");
        }
        if(!result.isSuccess()) {
            return result;
        }
        if(!repository.updateHost(host)) {
            result.addErrorMessage("Host not found in system");
            return result;
        }
        result.setPayload(host);
        return result;
    }

    public Result<Host> delete(String email) {
        Result<Host> result = new Result<>();
        if(email == null || email.isBlank()) {
            result.addErrorMessage("Email is required");
            return result;
        }
        if(!repository.deleteHost(email)) {
            result.addErrorMessage("Host email not found in system");
        }
        return result;
    }

    private Result<Host> validateHost(Host host) {
        Result<Host> result = new Result<>();
        if(host == null) {
            result.addErrorMessage("Host cannot be null");
            return result;
        }
        if(host.getLastName() == null || host.getLastName().isBlank()) {
            result.addErrorMessage("Host must have a last name");
        }
        if(host.getEmail() == null || host.getEmail().isBlank()) {
            result.addErrorMessage("Host must have a email");
        }
        if(host.getAddress() == null || host.getAddress().isBlank()) {
            result.addErrorMessage("Host must have an address registered");
        }
        if(host.getCity() == null || host.getCity().isBlank()) {
            result.addErrorMessage("Host must have a city registered");
        }
        if(host.getState() == null || host.getState().isBlank()) {
            result.addErrorMessage("Host must have a state registered");
        }
        if(host.getPostalCode() < 0) {
            result.addErrorMessage("Host must have a valid postal code");
        }
        if(host.getPhoneNumber() == null || host.getPhoneNumber().isBlank()) {
           result.addErrorMessage("Host must have a phone number");
        }
        if(host.getStandardRate().compareTo(BigDecimal.ZERO) <= 0) {
            result.addErrorMessage("Standard rate must have a positive value");
        }
        if(host.getWeekendRate().compareTo(BigDecimal.ZERO) <= 0) {
            result.addErrorMessage("Weekend rate must have a positive value");
        }
        return result;
    }

}
