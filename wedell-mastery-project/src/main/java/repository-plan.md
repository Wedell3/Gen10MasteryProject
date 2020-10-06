#Repository Plan 3.25 hours + 1.5 hour testing

##Interfaces 45 minutes total

###GuestRepository 15 minutes
    List<Guest> findAll()
    Guest findByEmail(String email)

###HostRepository 15 minutes
    List<Host> findAll() 
    Host findByEmail(String email)
    Host findByID(String ID)
    
###ReservationRepository 15 minutes
    List<Reservations> findByHost(Host host)
    Reservation add(Reservation reservation)
    Boolean update(Reservation reservation)
    Boolean delete(Reservation reservation)
    
##Classes

###GuestFileRepository 45 minutes
    Fields
    String filepath
    String Delimiter
    
    Methods
    [ ] List<Guest> findAll()
        [ ] create new arraylist
        [ ] try with resouces BufferedReader
        [ ] catch IOException
        [ ] loop through lines
        [ ] deserialize
        [ ] add to list
        [ ] return list
    
    [ ] Guest findByEmail(String email)
        [ ] create array list equal to findAll()
        [ ] enhanced for loop to loop through guests
        [ ] if guest.getEmail equals email return guest
        [ ] return null after loop
        
    [ ] Guest deserialize(String line)
        [ ] split line
        [ ] create new Guest
        [ ] set fields
        [ ] return Guest    
        
        
###HostFileRepository 45 minutes
    Fields
    String filepath
    String Delimiter
    
    Methods
    [ ] List<Host> findAll()
        [ ] create new arraylist
        [ ] try wtih resources BufferedReader     
        [ ] catch IOException
        [ ] for loop, loop through lines 
        [ ] deserialize to Host
        [ ] add Host to list
        [ ] return list
        
    [ ] Host findByEmail(String email)
        [ ] create array list with all hosts using findAll()
        [ ] enhanced for loop thru list
        [ ] if host.getEmail = email return host
        [ ] after loop return null if no match found   
           
    [ ] Host findByID(String ID)
        [ ] create array list with all hosts using findAll()
        [ ] enhanced for loop thru list
        [ ] if host.getId = id then return host
        [ ] after loop return null if no match found
        
    [ ] Host deserialize(String line)
        [ ] split line
        [ ] create new Host
        [ ] set fields
        [ ] return Host
        
###ReservationFileRepository 60 minutes
    Fields
    String filepath
    String Delimeter
    
    Methods
    [ ] List<Reservations> findByHost(Host host)
        [ ] make new arraylist
        [ ] create file path from host id
        [ ] try with Resources BufferedReader read from csv from hostId path
        [ ] deserialize line
        [ ] add reservation to list
        [ ] return list
    
    [ ] Reservation add(Reservation reservation)
        [ ] make new arraylist from findByHost(reservation.getHost)
        [ ] set reservation ID
        [ ] add reservation to list
        [ ] writeAll
        [ ] return Reservation
    
    [ ] Boolean update(Reservation reservation)
        [ ] make arraylist from findByHost(reservation.getHost)
        [ ] for loop thru list
        [ ] if reservation.getId matches id in list
        [ ] recalculate cost
        [ ] arraylist.set(index, reservation) / update reservation
        [ ] return true
        [ ] after loop return false (no match found)
        
    [ ] Boolean delete(Reservation reservation)
        [ ] make arraylist from findByHost(reservation.getHost)
        [ ] for loop thru list
        [ ] if reservation.getId matches id in list
        [ ] arraylist.remove(reservation) return true
        [ ] after loop return false(No match found)
        
    [ ] Reservation deserialize(String line)
        [ ] split line
        [ ] Create new Reservation
        [ ] set fields
        [ ] return Reservation
    
    [ ] void writeAll(List<Reservations>)
        [ ] try wtih resources printWriter to hostid file name
        [ ] enhanced for loop thru list
        [ ] serialize
        [ ] print to file
    
    [ ] String serialize(Reservation)
        [ ] create stringbuiler object
        [ ] append fields separated by commas
        [ ] return string        
                        