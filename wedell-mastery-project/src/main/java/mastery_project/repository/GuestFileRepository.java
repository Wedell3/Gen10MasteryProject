package mastery_project.repository;

import mastery_project.models.Guest;

import java.io.*;
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

    public Guest findById(int id) {
        List<Guest> all = findAll();
        for(Guest guest : all) {
            if(guest.getId() == id){
                return guest;
            }
        }
        return null;
    }

    @Override
    public Guest addGuest(Guest guest) {
        List<Guest> all = findAll();
        guest.setId(getNextId(all));
        all.add(guest);
        writeAll(all);
        return guest;
    }

    @Override
    public boolean updateGuest(Guest guest) {
        List<Guest> all = findAll();
        for(int i = 0; i < all.size(); i++) {
            if(all.get(i).getId() == guest.getId()) {
                all.set(i, guest);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteGuest(int id) {
        List<Guest> all = findAll();
        for(int i = 0; i < all.size(); i++) {
            if(all.get(i).getId() == id) {
                all.remove(i);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    private void writeAll(List<Guest> all) {
        try(PrintWriter writer = new PrintWriter(filepath)) {
            writer.println(makeHeader());
            for(Guest g : all) {
                writer.println(serialize(g));
            }
        } catch (FileNotFoundException ex) {
        }
    }

    private int getNextId(List<Guest> guests) {
        int maxId = 0;
        for(Guest g : guests) {
            if(g.getId() > maxId) {
                maxId = g.getId();
            }
        }
        return maxId + 1;
    }

    private String makeHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("guest_id").append(DELIMITER);
        sb.append("first_name").append(DELIMITER);
        sb.append("last_name").append(DELIMITER);
        sb.append("email").append(DELIMITER);
        sb.append("phone").append(DELIMITER);
        sb.append("state");
        return sb.toString();
    }

    private String serialize(Guest guest) {
        StringBuilder sb = new StringBuilder();
        sb.append(guest.getId()).append(DELIMITER);
        sb.append(guest.getFirstName()).append(DELIMITER);
        sb.append(guest.getLastName()).append(DELIMITER);
        sb.append(guest.getEmail()).append(DELIMITER);
        sb.append(guest.getPhoneNumber()).append(DELIMITER);
        sb.append(guest.getState());
        return sb.toString();
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
