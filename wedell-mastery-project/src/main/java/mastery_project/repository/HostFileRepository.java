package mastery_project.repository;

import mastery_project.models.Host;

import java.io.*;
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

    @Override
    public Host addHost(Host host) {
        List<Host> all = findAll();
        host.setId(java.util.UUID.randomUUID().toString());
        all.add(host);
        writeAll(all);
        return host;
    }

    @Override
    public boolean updateHost(Host host) {
        List<Host> all = findAll();
        for(int i = 0; i < all.size(); i ++) {
            if(all.get(i).getId().equals(host.getId())) {
                all.set(i, host);
                writeAll(all);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean deleteHost(String email) {
        List<Host> all = findAll();
        for(int i = 0; i < all.size(); i ++) {
            if(all.get(i).getEmail().equals(email)) {
                all.remove(i);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    private void writeAll(List<Host> all) {
        try(PrintWriter writer = new PrintWriter(filepath)) {
            writer.println(makeHeader());
            for(Host h : all) {
                writer.println(serialize(h));
            }
        } catch(FileNotFoundException ex ) {

        }
    }

    private String serialize(Host host) {
        StringBuilder sb = new StringBuilder();
        sb.append(host.getId()).append(DELIMITER);
        sb.append(host.getLastName()).append(DELIMITER);
        sb.append(host.getEmail()).append(DELIMITER);
        sb.append(host.getPhoneNumber()).append(DELIMITER);
        sb.append(host.getAddress()).append(DELIMITER);
        sb.append(host.getCity()).append(DELIMITER);
        sb.append(host.getState()).append(DELIMITER);
        sb.append(host.getPostalCode()).append(DELIMITER);
        sb.append(host.getStandardRate()).append(DELIMITER);
        sb.append(host.getWeekendRate());
        return sb.toString();

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

    private String makeHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("id").append(DELIMITER);
        sb.append("last_name").append(DELIMITER);
        sb.append("email").append(DELIMITER);
        sb.append("phone").append(DELIMITER);
        sb.append("address").append(DELIMITER);
        sb.append("city").append(DELIMITER);
        sb.append("state").append(DELIMITER);
        sb.append("postal_code").append(DELIMITER);
        sb.append("standard_rate").append(DELIMITER);
        sb.append("weekend_rate");
        return sb.toString();
    }
}
