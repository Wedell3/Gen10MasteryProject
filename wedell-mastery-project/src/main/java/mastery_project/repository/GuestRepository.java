package mastery_project.repository;

import mastery_project.models.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll();

    Guest findByEmail(String email);

    Guest findById(int id);

    Guest addGuest(Guest guest);

    boolean updateGuest(Guest guest);

    boolean deleteGuest(int id);

}
