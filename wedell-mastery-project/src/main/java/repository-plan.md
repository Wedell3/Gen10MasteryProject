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
    [x] List<Guest> findAll()
        [x] create new arraylist
        [x] try with resouces BufferedReader
        [x] catch IOException
        [x] loop through lines
        [x] deserialize
        [x] add to list
        [x] return list
    
    [x] Guest findByEmail(String email)
        [x] create array list equal to findAll()
        [x] enhanced for loop to loop through guests
        [x] if guest.getEmail equals email return guest
        [x] return null after loop
        
    [x] Guest deserialize(String line)
        [x] split line
        [x] create new Guest
        [x] set fields
        [x] return Guest    
        
        
###HostFileRepository 45 minutes
    Fields
    String filepath
    String Delimiter
    
    Methods
    [x] List<Host> findAll()
        [x] create new arraylist
        [x] try wtih resources BufferedReader     
        [x] catch IOException
        [x] for loop, loop through lines 
        [x] deserialize to Host
        [x] add Host to list
        [x] return list
        
    [x] Host findByEmail(String email)
        [x] create array list with all hosts using findAll()
        [x] enhanced for loop thru list
        [x] if host.getEmail = email return host
        [x] after loop return null if no match found   
           
    [x] Host findByID(String ID)
        [x] create array list with all hosts using findAll()
        [x] enhanced for loop thru list
        [x] if host.getId = id then return host
        [x] after loop return null if no match found
        
    [x] Host deserialize(String line)
        [x] split line
        [x] create new Host
        [x] set fields
        [x] return Host
        
###ReservationFileRepository 60 minutes
    Fields
    String filepath
    String Delimeter
    
    Methods
    [x] List<Reservations> findByHost(Host host)
        [x] make new arraylist
        [x] create file path from host id
        [x] try with Resources BufferedReader read from csv from hostId path
        [x] deserialize line
        [x] add reservation to list
        [x] return list
    
    [x] Reservation add(Reservation reservation)
        [x] make new arraylist from findByHost(reservation.getHost)
        [x] set reservation ID
        [x] add reservation to list
        [x] writeAll
        [x] return Reservation
    
    [x] Boolean update(Reservation reservation)
        [x] make arraylist from findByHost(reservation.getHost)
        [x] for loop thru list
        [x] if reservation.getId matches id in list
        [x] recalculate cost
        [x] arraylist.set(index, reservation) / update reservation
        [x] return true
        [x] after loop return false (no match found)
        
    [x] Boolean delete(Reservation reservation)
        [x] make arraylist from findByHost(reservation.getHost)
        [x] for loop thru list
        [x] if reservation.getId matches id in list
        [x] arraylist.remove(reservation) return true
        [x] after loop return false(No match found)
        
    [x] Reservation deserialize(String line)
        [x] split line
        [x] Create new Reservation
        [x] set fields
        [x] return Reservation
    
    [x] void writeAll(List<Reservations>)
        [x] try wtih resources printWriter to hostid file name
        [x] enhanced for loop thru list
        [x] serialize
        [x] print to file
    
    [x] String serialize(Reservation)
        [x] create stringbuiler object
        [x] append fields separated by commas
        [x] return string        
                        