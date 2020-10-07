#Service plan service 2 hours + 1.5 hours Testing

##GuestService 30 minutes
    Fields
    GuestRepository repository
    
    Methods
    [x] List<Guest> findAll()
        [x] return repository.findall()
       
    [x] Guest findByEmail(String email)
        [x] return repository.findByEmail(email)
           
##HostService 30 minutes
    Fields
    HostRepository repository
        
    Methods
    [x] List<Host> findAll()
        [x] return repository.findAll()
         
    [x] Host findByEmail(String email)
        [x]return repository.findByEmail(email);
        
    [x] Host findByID(String id)
        [x] return repository.findById(id)
            
##Reservation Service 60 minutes
    Fields
    GuestRepository guestRepository
    HostRepository hostRepository
    ReservationRepository reservationRepository
    
    Methods
    [x] Result<Reservation> addReservation(Reservation reservation)
        [x] create new Result
        [x] validate reservation info
        [x] if result not successful return result
        [x] reservationRepository.add(reservation)
        [x] set result payload with reservation
        [x] return result
    
    [x] List<Reservation> findByHost(Host host)
        [x] return reservationRepository.findByHost(host)
        
    [x] Result<Reservation> updateReservation(Reservation reservation)
        [x] create result
        [x] validate reservation
        [x] if result is failure return result
        [x] if(!reservationRepository.update(host) add error message return result
        [x] add reservation as result payload
        [x] return result
        
    [x] Result<Reservation> deleteReservation(Reservation reservation)
        [x] create result
        [x] if reservationRepository.deleteReservation() is not success add error message
        [x] return result
        
    [x] validate reservation
        [x] validate fields not null
        [x] validate start data not in past
        [x] end date after start date
        [x] dates don't overlap other reservations    
        
           