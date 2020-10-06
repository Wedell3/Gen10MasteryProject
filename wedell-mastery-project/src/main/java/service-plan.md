#Service plan service 2 hours + 1.5 hours Testing

##GuestService 30 minutes
    Fields
    GuestRepository repository
    
    Methods
    [ ] List<Guest> findAll()
        [ ] return repository.findall()
       
    [ ] Guest findByEmail(String email)
        [ ] return repository.findByEmail(email)
           
##HostService 30 minutes
    Fields
    HostRepository repository
        
    Methods
    [ ] List<Host> findAll()
        [ ] return repository.findAll()
         
    [ ] Host findByEmail(String email)
        return repository.findByEmail(email);
        
    [ ] Host findByID(String id)
        return repository.findById(id)
            
##Reservation Service 60 minutes
    Fields
    GuestRepository guestRepository
    HostRepository hostRepository
    ReservationRepository reservationRepository
    
    Methods
    [ ] Result<Reservation> addReservation(Reservation reservation)
        [ ] create new Result
        [ ] validate reservation info
        [ ] if result not successful return result
        [ ] reservationRepository.add(reservation)
        [ ] set result payload with reservation
        [ ] return result
    
    [ ] List<Reservation> findByHost(Host host)
        [ ] return hostRepository.findByHost(host)
        
    [ ] Result<Reservation> updateReservation(Reservation reservation)
        [ ] create result
        [ ] validate reservation
        [ ] if result is failure return result
        [ ] if(!reservationRepository.update(host) add error message return result
        [ ] add reservation as result payload
        [ ] return result
        
    [ ] Result<Reservation> deleteReservation(Reservation reservation)
        [ ] create result
        [ ] if reservationRepository.deleteReservation() is not success add error message
        [ ] return result
        
    [ ] validate reservation
        [ ] validate fields not null
        [ ] validate start data not in past
        [ ] end date after start date
        [ ] dates don't overlap other reservations    
        
           