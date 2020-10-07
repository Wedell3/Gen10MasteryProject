package mastery_project.domain;

import mastery_project.models.Guest;
import mastery_project.models.Host;
import mastery_project.models.Reservation;
import mastery_project.repository.DataException;
import mastery_project.repository.GuestRepository;
import mastery_project.repository.HostRepository;
import mastery_project.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {
    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(GuestRepository guestRepository, HostRepository hostRepository, ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findByHost(Host host) throws DataException {
        Result<Reservation> result = new Result<>();
        validateHost(host, result);
        return reservationRepository.findByHost(host);
    }

    public Result<Reservation> addReservation(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();
        validateReservation(reservation, result);
        if(!result.isSuccess()) {
            return result;
        }
        if(!result.isSuccess()) {
            return result;
        }
        result.setPayload(reservationRepository.add(reservation));
        return result;
    }

    public Result<Reservation> updateReservation(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();
        validateReservation(reservation, result);
        if(!result.isSuccess()) {
            return result;
        }

        if(!reservationRepository.update(reservation)){
            result.addErrorMessage("Update Failed");
            return result;
        }
        result.setPayload(reservation);
        return result;
    }

    public Result<Reservation> deleteReservation(Host host, int id) throws DataException {
        Result<Reservation> result = new Result<>();
        validateHost(host, result);
        if(!result.isSuccess()) {
            return result;
        }
        if(!reservationRepository.delete(host, id)) {
            result.addErrorMessage("Deletion Failed");
        }
        return result;
    }

    private void validateReservation(Reservation reservation, Result<Reservation> result) throws DataException{
        if(reservation == null) {
            result.addErrorMessage("Reservation cannot be null");
            return;
        }
        validateDates(reservation, result);
        validateHost(reservation.getHost(), result);
        validateGuest(reservation.getGuest(), result);
    }

    private void validateHost(Host host, Result<Reservation> result){
        if(host == null) {
            result.addErrorMessage("Host cannot be null");
            return;
        }
        if(host.getId() == null || host.getId().isBlank()) {
            result.addErrorMessage("Host must have a valid id");
        }
        if(host.getEmail() == null || host.getEmail().isBlank()) {
            result.addErrorMessage("Host must have a valid email");
        }
        if(!result.isSuccess()) {
            return;
        }
        if(hostRepository.findByEmail(host.getEmail()) == null) {
            result.addErrorMessage("Host must be registered in system");
        }
    }

    private void validateGuest(Guest guest, Result<Reservation> result) {
        if(guest == null) {
            result.addErrorMessage("Guest cannot be null");
            return;
        }
        if(guest.getEmail() == null || guest.getEmail().isBlank()) {
            result.addErrorMessage("Guest must have a valid email");
        }
        if(!result.isSuccess()) {
            return;
        }
        if(guestRepository.findByEmail(guest.getEmail()) == null) {
            result.addErrorMessage("Guest must be present in the system");
        }
    }

    private void validateDates(Reservation reservation, Result<Reservation> result) throws DataException{
        if(reservation.getStartDate() == null || reservation.getEndDate() == null) {
            result.addErrorMessage("Reservation must have a start and end date");
            return;
        }
        if(reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Start date cannot be in the past");
        }
        if(reservation.getEndDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("End date cannot be in the past");
        }
        if(reservation.getEndDate().isBefore(reservation.getStartDate())) {
            result.addErrorMessage("Start date must be before End date");
        }
        if(!result.isSuccess()) {
            return;
        }

        List<Reservation> previousReservations = findByHost(reservation.getHost());
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();
        for(Reservation r : previousReservations) {
            if(r.getId() != reservation.getId()){
                if(startDate.isAfter(r.getStartDate()) && startDate.isBefore(r.getEndDate())){
                    result.addErrorMessage("Start date cannot be during a previous reservation");
                    break;
                }
                if(endDate.isAfter(r.getStartDate()) && endDate.isBefore(r.getEndDate())){
                    result.addErrorMessage("End date cannot be during a previous reservation");
                    break;
                }
                if(startDate.isBefore(r.getStartDate()) && endDate.isAfter(r.getEndDate())) {
                    result.addErrorMessage("Reservation cannot overlap previous reservation");
                    break;
                }
                if(startDate.isAfter(r.getStartDate()) && endDate.isBefore(r.getEndDate())){
                    result.addErrorMessage("Reservation cannot overlap previous reservation");
                    break;
                }
            }
        }
    }



}
