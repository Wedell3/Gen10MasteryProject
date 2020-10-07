package mastery_project.repository;

import mastery_project.models.Guest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepository implements GuestRepository{
    private final String filepath;
    private static final String DELIMITER = ",";

    public GuestFileRepository(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public List<Guest> findAll() {
        ArrayList<Guest> all = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))){
            reader.readLine(); // skip header
            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                Guest guest = deserialize(line);
                all.add(guest);
            }
        } catch (IOException ex) {
            //Do nothing, return empty list if file not found
        }
        return all;
    }

    @Override
    public Guest findByEmail(String email) {
        List<Guest> all = findAll();
        for(Guest guest : all) {
            if(guest.getEmail().equals(email)){
                return guest;
            }
        }
        return null;
    }

    private Guest deserialize(String line) {
        String[] fields = line.split(DELIMITER);
        if(fields.length == 6) {
            Guest guest = new Guest();
            guest.setId(Integer.parseInt(fields[0]));
            guest.setFirstName(fields[1]);
            guest.setLastName(fields[2]);
            guest.setEmail(fields[3]);
            guest.setPhoneNumber(fields[4]);
            guest.setState(fields[5]);
            return guest;
        }
        return null;
    }
}
