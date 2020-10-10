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
}
