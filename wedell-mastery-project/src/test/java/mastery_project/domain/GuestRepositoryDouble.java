package mastery_project.domain;

import mastery_project.models.Guest;
import mastery_project.repository.GuestRepository;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {
    List<Guest> guests = makeGuests();

    @Override
    public List<Guest> findAll() {
        return guests;
    }

    @Override
    public Guest findByEmail(String email) {
        if(guests.get(0).getEmail().equals(email)) {
            return guests.get(0);
        }
        return null;
    }

    @Override
    public Guest findById(int id) {
        return guests.get(0);
    }

    @Override
    public Guest addGuest(Guest guest) {
        return guest;
    }

    @Override
    public boolean updateGuest(Guest guest) {
        return guest.getId() == 2;
    }

    @Override
    public boolean deleteGuest(int id) {
        return id == 2;
    }

    private ArrayList<Guest> makeGuests() {
        Guest guest1 = new Guest();
        guest1.setId(1);
        guest1.setFirstName("one");
        guest1.setLastName("uno");
        guest1.setState("MN");
        guest1.setPhoneNumber("(123) 456 7890");
        guest1.setEmail("one@uno.com");
        Guest guest2 = new Guest();
        guest2.setId(2);
        guest2.setFirstName("two");
        guest2.setLastName("dos");
        guest2.setState("WI");
        guest2.setPhoneNumber("(098) 765 4321");
        guest2.setEmail("two@dos.com");
        ArrayList<Guest> list = new ArrayList();
        list.add(guest1);
        list.add(guest2);
        return list;
    }
}
