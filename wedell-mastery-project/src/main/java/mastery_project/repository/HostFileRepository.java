package mastery_project.repository;

import mastery_project.models.Host;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostFileRepository implements HostRepository {
    private final String filepath;
    private static final String DELIMITER= ",";

    public HostFileRepository(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public List<Host> findAll() {
        ArrayList<Host> all = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            reader.readLine(); // Skip header
            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                Host host = deserialize(line);
                all.add(host);
            }
        } catch (IOException ex) {
            //Do nothing, return empty list if file not found
        }
        return all;
    }

    @Override
    public Host findByEmail(String email) {
        List<Host> all = findAll();
        for(Host host : all) {
            if(host.getEmail().equals(email)) {
                return host;
            }
        }
        return null;
    }

    @Override
    public Host findById(String id) {
        List<Host> all = findAll();
        for(Host host : all) {
            if(host.getId().equals(id)) {
                return host;
            }
        }
        return null;
    }

    private Host deserialize(String line) {
        String[] fields = line.split(DELIMITER);
        Host host = new Host();
        host.setId(fields[0]);
        host.setLastName(fields[1]);
        host.setEmail(fields[2]);
        host.setPhoneNumber(fields[3]);
        host.setAddress(fields[4]);
        host.setCity(fields[5]);
        host.setState(fields[6]);
        host.setPostalCode(Integer.parseInt(fields[7]));
        host.setStandardRate(new BigDecimal(fields[8]));
        host.setWeekendRate(new BigDecimal(fields[9]));
        return host;
    }
}
