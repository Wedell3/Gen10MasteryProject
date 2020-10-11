package mastery_project.ui;

import mastery_project.domain.GuestService;
import mastery_project.domain.HostService;
import mastery_project.domain.ReservationService;
import mastery_project.domain.Result;
import mastery_project.models.Guest;
import mastery_project.models.Host;
import mastery_project.models.Reservation;
import mastery_project.repository.DataException;

import java.util.List;

public class Controller {
    private final View view;
    private final ReservationService reservationService;
    private final HostService hostService;
    private final GuestService guestService;

    public Controller(View view, ReservationService reservationService, HostService hostService, GuestService guestService) {
        this.view = view;
        this.reservationService = reservationService;
        this.hostService = hostService;
        this.guestService = guestService;
    }

    public void run() throws DataException{
        view.displayWelcome();
        MainMenuOption menu;
        do{
            menu = view.getMenuSelection();
            switch(menu) {
                case VIEW_RESERVATIONS_BY_HOST:
                    viewReservationsByHost();
                    break;
                case MAKE_RESERVATION:
                    makeReservation();
                    break;
                case EDIT_RESERVATION:
                    editReservation();
                    break;
                case DELETE_RESERVATION:
                    deleteReservation();
                    break;
                case MANAGE_ACCOUNTS:
                    manageAccounts();
            }
        } while(menu != MainMenuOption.EXIT);
        view.displayGoodbye();
    }

    private void viewReservationsByHost() throws DataException {
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_BY_HOST.getMessage());
        Host host = hostService.findByEmail(view.getEmail("Host"));
        List<Reservation> reservations = reservationService.findByHost(host);
        view.displayReservations(reservations, host);
    }

    private void makeReservation() throws DataException{
        view.displayHeader(MainMenuOption.MAKE_RESERVATION.getMessage());
        Guest guest = guestService.findByEmail(view.getEmail("Guest"));
        Host host = hostService.findByEmail(view.getEmail("Host"));
        view.displayReservations(reservationService.findByHost(host), host);
        if(host == null || guest == null) {
            view.displayAccountNotFound(host, guest);
            return;
        }
        Reservation reservation = view.makeReservation();
        reservation.setGuest(guest);
        reservation.setHost(host);
        Result<Reservation> costResult = reservationService.calculateCost(reservation);
        if(!costResult.isSuccess()) {
            view.displayResult(costResult, "");
            return;
        }
        if(view.confirmReservation(reservation, reservationService.calculateCost(reservation).getPayload().getCost())) {
            Result<Reservation> result = reservationService.addReservation(reservation);
            view.displayResult(result, "created");
        }
    }

    private void editReservation() throws DataException{
        view.displayHeader(MainMenuOption.EDIT_RESERVATION.getMessage());
        Guest guest = guestService.findByEmail(view.getEmail("Guest"));
        Host host = hostService.findByEmail(view.getEmail("Host"));
        if(host == null || guest == null) {
            view.displayAccountNotFound(host, guest);
            return;
        }
        List<Reservation> guestReservations = reservationService.findGuestForHost(host, guest);
        if(guestReservations.size() > 0) {
            view.displayReservations(guestReservations, host);
        }
        Reservation reservation = view.selectReservation(guestReservations);
        if(reservation == null) {
            return;
        }
        view.editReservation(reservation);
        reservation.setHost(host);
        Result<Reservation> costResult = reservationService.calculateCost(reservation);
        if(!costResult.isSuccess()) {
            view.displayResult(costResult, "");
            return;
        }
        if(view.confirmReservation(reservation, reservationService.calculateCost(reservation).getPayload().getCost())) {
            Result<Reservation> result = reservationService.updateReservation(reservation);
            view.displayResult(result, "updated");
        }

    }

    private void deleteReservation() throws DataException{
        view.displayHeader(MainMenuOption.DELETE_RESERVATION.getMessage());
        Guest guest = guestService.findByEmail(view.getEmail("Guest"));
        Host host = hostService.findByEmail(view.getEmail("Host"));
        if(host == null || guest == null) {
            view.displayAccountNotFound(host, guest);
            return;
        }
        List<Reservation> guestReservations = reservationService.findGuestForHost(host, guest);
        if(guestReservations.size() > 0) {
            view.displayReservations(guestReservations, host);
        }
        Reservation reservation = view.selectReservation(guestReservations);
        if(reservation == null) {
            return;
        }
        reservation.setHost(host);
        int id = reservation.getId();
        Result<Reservation> result = reservationService.deleteReservation(reservation.getHost(), id);
        view.displayResult(result, id, "deleted");
    }

    private void manageAccounts() {
        //view.displayHeader(MainMenuOption.MANAGE_ACCOUNTS.getMessage());
        ManageAccountsOption option;
        int type;
        do{
            option = view.getAccountsOption();
            switch (option) {
                case VIEW:
                    type = view.getAccountType();
                    getAndDisplayAccounts(type);
                    break;
                case CREATE:
                    type = view.getAccountType();
                    createAccount(type);
                    break;
                case UPDATE:
                    type = view.getAccountType();
                    updateAccount(type);
                    break;
                case DELETE:
                    type = view.getAccountType();
                    deleteAccount(type);
                    break;
            }
        } while (option != ManageAccountsOption.EXIT);
    }

    private void getAndDisplayAccounts(int type) {
        if(type == 1) {
            view.displayHeader("Viewing Hosts");
            List<Host> allHosts = hostService.findAll();
            view.displayHosts(allHosts);
        } else if(type == 2) {
            view.displayHeader("Viewing Guests");
            List<Guest> allGuests = guestService.findAll();
            view.displayGuests(allGuests);
        }
    }

    private void createAccount(int type) {
        if(type == 1) {
            view.displayHeader("Create Host Account");
            Host host = view.makeHost();
            Result<Host> result = hostService.add(host);
            view.displayHostResult(result, "added");
        } else if(type == 2) {
            view.displayHeader("Create Guest Account");
            Guest guest = view.makeGuest();
            Result<Guest> result = guestService.addGuest(guest);
            view.displayGuestResult(result, "added");
        }
    }

    private void updateAccount(int type) {
        if(type == 1) {
            view.displayHeader("Update Host Account");
            Host host = hostService.findByEmail(view.getEmail("Host"));
            if (host == null) {
                view.displayAccountNotFound(host);
                return;
            }
            view.updateHost(host);
            Result<Host> result = hostService.update(host);
            view.displayHostResult(result, "updated");
        } else if(type == 2) {
            view.displayHeader("Update Guest Account");
            Guest guest = guestService.findByEmail(view.getEmail("Guest"));
            if(guest == null) {
                view.displayAccountNotFound(guest);
                return;
            }
            view.updateGuest(guest);
            Result<Guest> result = guestService.updateGuest(guest);
            view.displayGuestResult(result, "updated");
        }
    }

    private void deleteAccount(int type) {
        if(type == 1) {
            view.displayHeader("Delete Host Account");
            String email = view.getEmail("Host");
            Host host = hostService.findByEmail(email);
            if(host == null) {
                view.displayAccountNotFound(host);
                return;
            }
            Result<Host> result = hostService.delete(email);
            view.displayHostResult(result, "deleted");
        } else if(type == 2) {
            view.displayHeader("Delete Guest Account");
            String email = view.getEmail("Guest");
            Guest guest = guestService.findByEmail(email);
            if(guest == null) {
                view.displayAccountNotFound(guest);
                return;
            }
            Result<Guest> result = guestService.deleteGuest(guest.getId());
            view.displayGuestResult(result, "deleted");
        }
    }
}
