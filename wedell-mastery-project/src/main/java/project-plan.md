## Class structure
    App
###Models
    Guest
    Host
    Reservation
    
###Repository

######Interfaces
    GuestRepository
    HostRepository
    ReservationRepository

######Classes
    GuestFileRepository implements GuestRepository
    HostFileRepository implements HostRepository
    ReservationFileRepository implements ReservationRepository
    

###Service

######Classes
    GuestService
    HostService
    ReservationService
    Result<T> implements response
    
######Interfaces
    Response    
    
###ui

######Classes
    Controller
    View
    ConsoleIO implements io
    
######Interfaces
    io
    

##Dependency Injection

###Repository
    GuestRepostory <- filePath for guests data file
    HostRepository <- filePath for hosts data file
    ReservationRepository <- filePath for reservation data folder
    
###Service
    GuestService <- GuestRepository
    HostService <- HostRepository
    ReservationService <- ReservationRepository, GuestRepositort, HostRepository
    
###ui
    View <- ConsoleIO   
    Controller <- View, GuestService, HostService, ReservationService
    
###App
    App <- Controller    
    